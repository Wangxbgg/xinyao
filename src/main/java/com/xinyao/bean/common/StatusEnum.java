package com.xinyao.bean.common;

import lombok.AllArgsConstructor;

/**
 * 系统用到的状态枚举，添加&修改时注意修改对应的注释
 * @author ZhangFZ
 * @date 2020/10/31 9:31
 **/
public class StatusEnum {

    /**
     * 订单状态
     */
    @AllArgsConstructor
    public enum ContractStatus {
        /**
         * 经销商待购区确认信息时，生成临时合同，锁货15分钟
         */
        PAYMENT(0, "待支付"),

        /**
         * 合同支付成功，进入到正常状态
         */
        TRADE(1, "交易中"),

        /**
         * 管理人员进行合同作废操作
         */
        FINISH(2, "已完成"),

        /**
         * 管理人员进行合同失效操作
         */
        CANCEL(3, "已取消");

        /**
         * 状态
         */
        public final Integer code;
        /**
         * 描述
         */
        public final String desc;
    }

    /**
     * 支付类型
     */
    @AllArgsConstructor
    public enum AmountStatus {
        /**
         * 经销商待购区确认信息时，生成临时合同，锁货15分钟
         */
        NORMAL_PAY("NORMAL_PAY", "正常支付"),

        /**
         * 合同支付成功，进入到正常状态
         */
        NORMAL_REVENUE("NORMAL_REVENUE", "正常收入"),

        /**
         * 管理人员进行合同作废操作
         */
        BANK_TRANSFER_IN("BANK_TRANSFER_IN", "银行转入"),

        /**
         * 管理人员进行合同失效操作
         */
        BANK_TRANSFER_OUT("BANK_TRANSFER_OUT", "银行转出");

        /**
         * 状态
         */
        public final String code;
        /**
         * 描述
         */
        public final String desc;
    }

}
