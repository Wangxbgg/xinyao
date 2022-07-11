package com.xinyao.service.sale.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xinyao.bean.sale.Order;
import com.xinyao.bean.sale.OrderProduct;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.vo.OrderProductVo;
import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.mapper.sale.OrderMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.sale.IOrderService;
import com.xinyao.service.sale.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private OrderMapper orderMapper;

    @Autowired
    private IProductService productService;

    @Override
    public OrderVo createOrder(OrderVo orderVo) {
        // 校验订单商品是否有效
        if (CollectionUtils.isEmpty(orderVo.getOrderVoList())) {
            throw new RuntimeException("订单商品为空！！！");
        }
        for (OrderProductVo orderProductVo : orderVo.getOrderVoList()) {
            Product product = productService.getInfoById(orderProductVo.getProductId());
            if (product.getQuantity().compareTo(orderProductVo.getProductQuantity()) < 0) {
                throw new RuntimeException("商品剩余数量不足，订单提交失败！！！");
            }
        }
        // 生成订单信息

        // 获取订单商品信息
        List<OrderProduct> orderProductList = new ArrayList<>();


        return null;
    }
}
