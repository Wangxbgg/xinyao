package com.xinyao.service.fund;

import com.xinyao.bean.common.StatusEnum;
import com.xinyao.bean.fund.AmountDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.OrderVo;

import java.math.BigDecimal;

/**
 * <p>
 * 资金管理-用户账户明细 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-11
 */
public interface IAmountDetailService extends IService<AmountDetail> {

    boolean payAmount(OrderVo orderVo, Long userId, BigDecimal payAmount, StatusEnum.AmountStatus amountStatus);

}
