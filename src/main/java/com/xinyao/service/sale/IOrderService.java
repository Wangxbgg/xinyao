package com.xinyao.service.sale;

import com.xinyao.bean.sale.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.OrderVo;

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

    OrderVo confirmOrder(OrderVo orderVo);
}
