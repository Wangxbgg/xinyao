package com.xinyao.service.sale.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.common.GlobalField;
import com.xinyao.bean.common.StatusEnum;
import com.xinyao.bean.sale.Order;
import com.xinyao.bean.sale.OrderProduct;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.vo.OrderProductVo;
import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.mapper.sale.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.fund.IAmountDetailService;
import com.xinyao.service.sale.IOrderProductService;
import com.xinyao.service.sale.IOrderService;
import com.xinyao.service.sale.IProductService;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private IProductService productService;

    @Autowired
    private IOrderProductService orderProductService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAmountDetailService amountDetailService;

    @Autowired
    private SnUtil snUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVo createOrder(OrderVo orderVo) {
        // 校验订单商品是否有效
        if (CollectionUtils.isEmpty(orderVo.getOrderProductVoList())) {
            throw new RuntimeException("订单商品为空！！！");
        }
        for (OrderProductVo orderProductVo : orderVo.getOrderProductVoList()) {
            Product product = productService.getById(orderProductVo.getProductId());
            if (product.getQuantity().compareTo(orderProductVo.getProductQuantity()) < 0) {
                throw new RuntimeException("商品剩余数量不足，订单提交失败！！！");
            }
        }
        // 生成订单信息
        Order order = new Order();
        order.setSn(snUtil.getOrderSn());
        order.setUserId(JWTUtil.getUserId());
        order.setStatus(StatusEnum.OrderStatus.PAYMENT.code);

        // 订单数量
        BigDecimal orderQuantity = BigDecimal.ZERO;
        // 订单金额
        BigDecimal orderPrice = BigDecimal.ZERO;
        // 获取订单商品信息
        for (OrderProductVo orderProductVo : orderVo.getOrderProductVoList()) {
            Product product = productService.getInfoById(orderProductVo.getProductId());
            orderQuantity = orderQuantity.add(orderProductVo.getProductQuantity());
            orderPrice = orderPrice.add(product.getAmount().multiply(orderProductVo.getProductQuantity()));
        }
        order.setTotalPrice(orderPrice);
        order.setTotalQuantity(orderQuantity);
        this.baseMapper.insert(order);

        // 生成订单商品信息
        for (OrderProductVo orderProductVo : orderVo.getOrderProductVoList()) {
            Product product = productService.getInfoById(orderProductVo.getProductId());
            orderProductVo.setBuyPrice(product.getAmount());
            orderProductVo.setProductNumber(product.getNumber());
            orderProductVo.setOrderId(order.getId());
            orderProductVo.setOrderSn(order.getSn());
            orderProductVo.setUserId(JWTUtil.getUserId());
            orderProductVo.setStatus(StatusEnum.OrderStatus.PAYMENT.code);
            orderProductService.save(orderProductVo);
            // 减少商品库存
            updateStock(orderProductVo.getProductId(), orderProductVo.getProductQuantity(), false);
        }

        OrderVo newOrderVo = new OrderVo();
        BeanUtils.copyProperties(order, newOrderVo);
        newOrderVo.setOrderProductVoList(orderVo.getOrderProductVoList());

        // 获取供应商余额信息
        newOrderVo.setUserAmount(userService.getAccountBalance());

        // 十五分钟的过期时间
        long lossTime = System.currentTimeMillis() - order.getCreateTime().getTime();
        long expirationDate = GlobalField.ORDER_TIMEOUT_TIME - lossTime / 1000;
        newOrderVo.setExpirationDate(expirationDate);

        // 获取供应商余额信息
        newOrderVo.setUserAmount(userService.getAccountBalance());

        // 将该用户的待支付订单信息保存到redis中
        redisUtil.set(GlobalField.ORDER_CONFIG + JWTUtil.getUserId(), order.getId(), expirationDate);
        // copy key
        redisUtil.set(GlobalField.COPY_KEY + GlobalField.ORDER_CONFIG + JWTUtil.getUserId(), order.getId());
        return newOrderVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmOrder(OrderVo orderVo) {
        // 获取订单信息
        Order order = this.baseMapper.selectById(orderVo.getId());
        // 获取订单商品信息
        List<OrderProductVo> orderProductVoList = orderProductService.getByOrderId(order.getId());

        // 校验订单商品是否有效
        for (OrderProductVo orderProductVo : orderProductVoList) {
            Product product = productService.getInfoById(orderProductVo.getProductId());
            if (product.getQuantity().compareTo(orderProductVo.getProductQuantity()) < 0) {
                throw new RuntimeException("商品剩余数量不足，订单提交失败！！！");
            }
        }

        // 校验用户支付密码
        String userDealPassword = userService.getDealPassword();
        if (StringUtils.isNullOrBlank(userDealPassword)) {
            throw new RuntimeException("您尚未设置支付密码\n请设置后支付");
        }
        if (!MD5Util.encrypt(orderVo.getUserDealPassword(), JWTUtil.getAccount()).equals(userDealPassword)) {
            throw new RuntimeException("您的交易密码错误");
        }

        // 校验用户余额
        BigDecimal userAmount = userService.getAccountBalance();
        if (order.getTotalPrice().compareTo(userAmount) > 0) {
            throw new RuntimeException("您的可用余额不足\n请充值后支付");
        }

        // 修改订单状态为已完成
        order.setStatus(StatusEnum.OrderStatus.FINISH.code);
        this.baseMapper.updateById(order);
        // 修改订单商品状态为已完成
        for (OrderProductVo orderProductVo : orderProductVoList) {
            orderProductVo.setStatus(StatusEnum.OrderStatus.FINISH.code);
            orderProductService.updateById(orderProductVo);
        }

        // 调用支付接口，进行余额扣款并记录支付信息
        amountDetailService.payAmount(order, JWTUtil.getUserId(), order.getTotalPrice(), StatusEnum.AmountStatus.NORMAL_PAY);

        // 将该用户的待支付订单信息保存到redis中
        redisUtil.remove(GlobalField.ORDER_CONFIG + JWTUtil.getUserId());
        // copy key
        redisUtil.remove(GlobalField.COPY_KEY + GlobalField.ORDER_CONFIG + JWTUtil.getUserId());

        return true;
    }

    @Override
    public IPage<OrderVo> getAllList(Page<Order> page, Integer status) {
        if (status == 0) {
            status = 4;
        }
        IPage<OrderVo> iPage = this.baseMapper.getAllList(page, status);
        for (OrderVo orderVo : iPage.getRecords()) {
            // 十五分钟的过期时间
            long lossTime = System.currentTimeMillis() - orderVo.getCreateTime().getTime();
            long expirationDate = GlobalField.ORDER_TIMEOUT_TIME - lossTime / 1000;
            orderVo.setExpirationDate(expirationDate);

            // 获取订单商品信息
            orderVo.setOrderProductVoList(orderProductService.getByOrderId(orderVo.getId()));
        }
        return iPage;
    }

    @Override
    public OrderVo selectById(Long id) {
        Order order = this.baseMapper.selectById(id);
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        // 十五分钟的过期时间
        long lossTime = System.currentTimeMillis() - orderVo.getCreateTime().getTime();
        long expirationDate = GlobalField.ORDER_TIMEOUT_TIME - lossTime / 1000;
        orderVo.setExpirationDate(expirationDate);

        // 获取订单商品信息
        orderVo.setOrderProductVoList(orderProductService.getByOrderId(orderVo.getId()));
        return orderVo;
    }

    @Override
    public boolean cancelOrder(Long orderId) {
        // 获取订单信息
        Order order = this.baseMapper.selectById(orderId);
        // 获取订单商品信息
        List<OrderProductVo> orderProductVoList = orderProductService.getByOrderId(order.getId());

        // 设置订单状态为已取消
        order.setStatus(StatusEnum.OrderStatus.CANCEL.code);
        this.baseMapper.updateById(order);

        // 设置订单商品状态为已取消
        for (OrderProductVo orderProductVo : orderProductVoList) {
            orderProductVo.setStatus(StatusEnum.OrderStatus.CANCEL.code);
            orderProductService.updateById(orderProductVo);
            // 退还商品库存
            updateStock(orderProductVo.getId(), orderProductVo.getProductQuantity(), true);
        }

        // 删除redis的记录
        redisUtil.remove(GlobalField.ORDER_CONFIG + JWTUtil.getUserId());
        // 删除copy key的记录
        redisUtil.remove(GlobalField.COPY_KEY + GlobalField.ORDER_CONFIG + JWTUtil.getUserId());
        return false;
    }

    /**
     * 处理商品库存
     * @Param productId 商品id，quantity 商品数量，flag true +；false -
     */
    private void updateStock(Long productId, BigDecimal quantity, boolean flag){
        Product product = productService.getInfoById(productId);
        if (flag) {
            product.setQuantity(product.getQuantity().add(quantity));
        } else {
            product.setQuantity(product.getQuantity().subtract(quantity));
        }
        productService.updateById(product);
    }
}
