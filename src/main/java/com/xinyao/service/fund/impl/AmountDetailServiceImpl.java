package com.xinyao.service.fund.impl;

import com.xinyao.bean.common.GlobalField;
import com.xinyao.bean.common.StatusEnum;
import com.xinyao.bean.fund.AmountDetail;
import com.xinyao.bean.sale.Order;
import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.bean.usc.User;
import com.xinyao.mapper.fund.AmountDetailMapper;
import com.xinyao.service.fund.IAmountDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.usc.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 资金管理-用户账户明细 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-11
 */
@Slf4j
@Service
public class AmountDetailServiceImpl extends ServiceImpl<AmountDetailMapper, AmountDetail> implements IAmountDetailService {

    @Autowired
    private IUserService userService;

    /**
     * 经销商余额扣款
     * @param userId  用户id
     * @param payAmount 支付金额
     * @param amountStatus 支付类型
     */
    @Override
    public boolean payAmount(Order order, Long userId, BigDecimal payAmount, StatusEnum.AmountStatus amountStatus) {
        // 再次校验用户余额
        User user = userService.getById(userId);
        if (user.getAmount().compareTo(payAmount) < 0) {
            log.error(userId + "-->账户余额不足，请先充值");
            throw new RuntimeException("账户余额不足，请先充值");
        }
        user.setAmount(user.getAmount().subtract(payAmount));
        userService.updateById(user);

        // 记录资金明细
        createAmountDetail(user.getId(), order.getId(), order.getSn(), amountStatus,
                payAmount, "用户购买商品支付" + payAmount + "元", user.getName());
        return true;
    }

    /**
     * 保存资金明细
     * @param userId 经销商id
     * @param orderId 合同id
     * @param amountStatus 资金支付类型
     * @param amount 支付金额
     */
    private void createAmountDetail(Long userId, Long orderId, String orderSn, StatusEnum.AmountStatus amountStatus,
                                    BigDecimal amount, String remark, String userName){
        AmountDetail amountDetail = new AmountDetail();
        amountDetail.setUserId(userId);
        amountDetail.setUserName(userName);
        amountDetail.setOrderId(orderId);
        amountDetail.setOrderSn(orderSn);
        amountDetail.setType(amountStatus.code);
        amountDetail.setOperationSymbol(GlobalField.OPERATION_SUBTRACT);
        amountDetail.setAmount(amount);
        amountDetail.setStatus(0);
        amountDetail.setComment(remark);
        this.baseMapper.insert(amountDetail);
    }

}
