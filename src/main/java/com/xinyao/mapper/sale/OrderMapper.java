package com.xinyao.mapper.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyao.bean.sale.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
public interface OrderMapper extends BaseMapper<Order> {

    IPage<OrderVo> getAllList(@Param("page") Page<Order> page, @Param("status") Integer status);
}
