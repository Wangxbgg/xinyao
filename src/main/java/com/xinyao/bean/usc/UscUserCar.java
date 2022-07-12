package com.xinyao.bean.usc;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * usc_user_car
 * @author 
 */
@Data
public class UscUserCar implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 银行名称
     */
    private String name;

    /**
     * 类型（0：信用卡 1：储蓄卡）
     */
    private String type;

    /**
     * 银行卡号
     */
    private Integer carNumber;

    /**
     * 真实姓名
     */
    private String tureName;

    /**
     * 预留手机号
     */
    private String phone;

    /**
     * 开户行
     */
    private String bankOfAccounts;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 创建人时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 修改人姓名
     */
    private String updateName;

    /**
     * 修改人时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}