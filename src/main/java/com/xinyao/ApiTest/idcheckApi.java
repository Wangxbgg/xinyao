package com.xinyao.ApiTest;

import com.xinyao.util.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class idcheckApi {
    public static void main(String[] args) {
        String host = "https://zid.market.alicloudapi.com";
        String path = "/idcheck/Post";
        String method = "POST";
        String appcode = "28ed04abc3cc4f1db1ddb0248b209695";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("cardNo", "430602199608257418");
        bodys.put("realName", "陈鹏");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            int httpCode= response.getStatusLine().getStatusCode();
            if (httpCode == 200) {

                System.out.println("正常请求计费(其他均不计费)");
                System.out.println("获取返回的json：");
                System.out.println(EntityUtils.toString(response.getEntity()));
            } else {

                // Map<String, List<String>> map = response.getAllHeaders().map();
                //String error = map.get("X-Ca-Error-Message").get(0);
                String error = response.getFirstHeader("X-Ca-Error-Message").getValue();
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
