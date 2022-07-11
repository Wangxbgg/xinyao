package com.xinyao.util;

import org.springframework.stereotype.Component;

/**
 * 三级联动判断工具类
 */
@Component
public class AddressUtil {

    /**
     * 判断省市区是否为空
     * @param address 地址
     */
    public void checkAddress(String address){
        // 判断第一个地址的省市区不能为空
        String [] addressArray = address.split(",");
        if(addressArray.length < 3
                || StringUtils.isNullOrBlank(addressArray[0])
                || StringUtils.isNullOrBlank(addressArray[1])
                || StringUtils.isNullOrBlank(addressArray[2])){
            throw new RuntimeException("省市区信息不能为空");
        }
    }

}
