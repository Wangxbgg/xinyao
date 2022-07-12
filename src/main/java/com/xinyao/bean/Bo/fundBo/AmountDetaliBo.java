package com.xinyao.bean.Bo.fundBo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AmountDetaliBo {
    private static final long serialVersionUID = 1L;
    private Long id;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "(NORMAL_PAY:正常支付), (NORMAL_REVENUE:正常收入), (BANK_TRANSFER_IN:银行转入), (BANK_TRANSFER_OUT:银行转出)")
    private String type;
    @ApiModelProperty(value = "金额")
    private Double amount;
    @ApiModelProperty(value = "银行账号")
    private String bankCard;
}
