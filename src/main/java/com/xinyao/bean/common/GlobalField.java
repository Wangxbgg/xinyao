package com.xinyao.bean.common;


/**
 * 全局字段
 *
 * @author Administrator
 */
public class GlobalField {

    /**
     * 短信服务区域id
     */
    public static final String REGINDID = "liu_nft";

    /**
     * 短信服务AccessKeyID
     */
    public static final String ACCESSKEYID = "226b2b67270832f50a57";

    /**
     * 运算符 +
     */
    public static final int OPERATION_ADD = 1;

    /**
     * 运算符 -
     */
    public static final int OPERATION_SUBTRACT = 2;

    /**
     * 储存redis的key
     */
    public static final String REDISKEY = "code:";

    /**
     * 一分钟的毫秒数
     */
    public static final int MINUTESMS = 60 * 1000;

    /**
     * 一小时的毫秒数
     */
    public static final int HOURSMS = 60 * 60 * 1000;

    /**
     * 一天的毫秒数
     */
    public static final int DAYMS = 1000 * 24 * 60 * 60;

    /**
     * 前台传递Token存放在Header里面对应的字段
     */
    public static final String TOKEN_AUTHORIZATION = "Authorization";

    /**
     * 响应成功状态码 200
     */
    public static final int SUCCESS = 200;

    /**
     * 删除状态-未删除
     */
    public static final int UNDELETE_STATUS = 0;

    /**
     * 所有的需要控制的权限，存放到Redis的key
     */
    public static final String ALL_PERMISSION = "ALL_PERMISSION";

    /**
     * 每个角色拥有的权限，存放到Redis的key
     */
    public static final String ROLE_PERMISSION = "ROLE_PERMISSION:";

    /**l
     * 订单支付有效期，时间为15分钟
     */
    public static final long ORDER_TIMEOUT_TIME = 900L;

    /**l
     * copy的key值
     */
    public static final String COPY_KEY = "COPY";

    /**l
     * 订单--待支付信息存放在redis的key值
     */
    public static final String ORDER_CONFIG = "ORDER_CONFIG:";
}
