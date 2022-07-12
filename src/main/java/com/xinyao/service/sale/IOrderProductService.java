package com.xinyao.service.sale;

import com.xinyao.bean.sale.OrderProduct;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.OrderProductVo;

import java.util.List;

/**
 * <p>
 * 订单商品表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
public interface IOrderProductService extends IService<OrderProduct> {

    List<OrderProductVo> getByOrderId(Long orderId);
}
