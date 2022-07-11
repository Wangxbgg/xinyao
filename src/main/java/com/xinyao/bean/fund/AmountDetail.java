package com.xinyao.bean.fund;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 资金管理-用户账户明细
 * </p>
 *
 * @author WangXB
 * @since 2022-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("fund_amount_detail")
@ApiModel(value="AmountDetail对象", description="资金管理-用户账户明细")
public class AmountDetail extends Model<AmountDetail> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "(NORMAL_PAY:正常支付), (NORMAL_REVENUE:正常收入), (BANK_TRANSFER_IN:银行转入), (BANK_TRANSFER_OUT:银行转出)")
    private String type;

    @ApiModelProperty(value = "运算符号1:+  2:-")
    private Integer operationSymbol;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "创建人姓名")
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "状态(0：成功  1：失败)")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "交易流水号")
    private String transactionNum;

    @ApiModelProperty(value = "交易日")
    private String transactionDate;

    @ApiModelProperty(value = "银行账号")
    private String bankCard;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
