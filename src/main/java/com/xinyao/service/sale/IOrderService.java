package com.xinyao.service.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.util.R;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
public interface IOrderService extends IService<Order> {

    OrderVo createOrder(OrderVo orderVo);

    boolean confirmOrder(OrderVo orderVo);

    IPage<OrderVo> getAllList(Page<Order> page, Integer status);

    OrderVo selectById(Long id);

    boolean cancelOrder(OrderVo orderVo);
}
