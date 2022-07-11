package com.xinyao.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * create by liwutong on 2020/11/09
 * 编号工具类
 */
@Component
public class SnUtil {

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 获取合同编号
     * @return 合同编号
     */
    public String getSn() {
        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long sn=a*100000000L+b;
        return String.valueOf(sn);
    }

    /**
     * 获取合同编号
     * @return 合同编号
     */
    public String getOrderSn() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String sn = "XY" + dateStr;
        String num = redisUtil.getIncr("CONTRACT_SN:" + dateStr, 5);
        return sn + "-" + num;
    }

    /**
     * 获取提货单的编号
     * @param orgEncrypt 经销商编号
     * @return 提货单的编号
     */
    public String getLoadBillSn(String orgEncrypt){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        String sn = "SO" + orgEncrypt + dateStr;
        String num = redisUtil.getIncr("LOAD_BILL_SN:" + dateStr, 4);
        return sn + "-" + num;
    }

    /**
     * 提货单 直发拆多车的时候，返回确认收货单的编号
     * @param loadBillSn 提货单编号
     * @return 收货单的编号
     */
    public String getLoadBillCarSn(String loadBillSn){
        return redisUtil.getIncr("LOAD_BILL_CAR_SN:" + loadBillSn, 3);
    }

    /**
     * 获取锁货编号
     * @return 锁货编号
     */
    public String getLockSn(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(new Date());
        return dateStr + redisUtil.getIncr("LOCK_SN:" + dateStr, 4);
    }

}
