package com.xinyao.service.sale.impl;

import com.xinyao.bean.sale.OrderProduct;
import com.xinyao.mapper.sale.OrderProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.sale.IOrderProductService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
@Service
public class OrderProductServiceImpl extends ServiceImpl<OrderProductMapper, OrderProduct> implements IOrderProductService {

}
