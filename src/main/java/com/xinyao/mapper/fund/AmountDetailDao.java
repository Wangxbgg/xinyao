package com.xinyao.mapper.fund;

import com.xinyao.bean.Bo.fundBo.AmountDetaliBo;
import com.xinyao.bean.Bo.fundBo.FlowingBo;
import com.xinyao.bean.Vo.AmountDetailVo;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Resource
public interface AmountDetailDao {
    /**
     * 查询当前账户金额
     */
    Double GetMoneny(Long id);
    /**
     * 充值
     */
    int recharge(Double amount,Long userId);
    /**
     * 提现
     */
    int withdrawal(Double amount,Long userId);

    /**
     *
     * @param record 记录充值转账流水
     * @return
     */
    int insertSelective(AmountDetaliBo record);
    /**
     * 查询充值转账记录流水
     */
    List<AmountDetailVo> flowinGlist(FlowingBo paramBo);
    /**
     * 流水总条数
     */
    int ConutflowinGlist(FlowingBo paramBo);
}
