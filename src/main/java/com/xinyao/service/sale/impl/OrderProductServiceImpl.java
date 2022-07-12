package com.xinyao.service.sale.impl;

import com.xinyao.bean.sale.OrderProduct;
import com.xinyao.bean.sale.vo.OrderProductVo;
import com.xinyao.mapper.sale.OrderProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.sale.IOrderProductService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<OrderProductVo> getByOrderId(Long orderId) {
        return this.baseMapper.getByOrderId(orderId);
    }
}
