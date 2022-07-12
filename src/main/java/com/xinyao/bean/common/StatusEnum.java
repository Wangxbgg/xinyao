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
    public enum OrderStatus {
        /**
         * 用户创建订单尚未支付
         */
        PAYMENT(0, "待支付"),

        /**
         * 用户将商品进行二次出售
         */
        TRADE(1, "交易中"),

        /**
         * 订单支付成功
         */
        FINISH(2, "已完成"),

        /**
         * 取消订单
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
         * 用户创建订单支付金额
         */
        NORMAL_PAY("NORMAL_PAY", "正常支付"),

        /**
         * 用户转售商品收入金额
         */
        NORMAL_REVENUE("NORMAL_REVENUE", "正常收入"),

        /**
         * 银行卡充值余额
         */
        BANK_TRANSFER_IN("BANK_TRANSFER_IN", "银行转入"),

        /**
         * 余额提现银行卡
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

    /**
     * 转赠状态
     */
    @AllArgsConstructor
    public enum TransferStatus {
        /**
         * 创建转赠记录未确认受赠人
         */
        COMMIT(1, "已提交"),

        /**
         * 合同支付成功，进入到正常状态
         */
        PROGRESS(2, "转赠中/待接收"),

        /**
         * 管理人员进行合同作废操作
         */
        FINISH(3, "已完成"),

        /**
         * 管理人员进行合同失效操作
         */
        CANCEL(4, "已取消");

        /**
         * 状态
         */
        public final Integer code;
        /**
         * 描述
         */
        public final String desc;
    }

}
