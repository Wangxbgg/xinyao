package com.xinyao.mapper.sale;

import com.xinyao.bean.sale.OrderProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyao.bean.sale.vo.OrderProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单商品表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
public interface OrderProductMapper extends BaseMapper<OrderProduct> {

    List<OrderProductVo> getByOrderId(@Param("orderId") Long orderId);
}
