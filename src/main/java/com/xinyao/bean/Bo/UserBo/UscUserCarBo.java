package com.xinyao.bean.Bo.UserBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class UscUserCarBo {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String name;

    /**
     * 类型（0：信用卡 1：储蓄卡）
     */
    @ApiModelProperty(value = "类型（0：信用卡 1：储蓄卡）")
    private String type;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private Integer carNumber;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String tureName;

    /**
     * 预留手机号
     */
    @ApiModelProperty(value = "预留手机号")
    private String phone;

    /**
     * 开户行
     */
    @ApiModelProperty(value = "开户行")
    private String bankOfAccounts;


    private static final long serialVersionUID = 1L;
}
