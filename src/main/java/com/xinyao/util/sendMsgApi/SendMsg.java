package com.xinyao.util.sendMsgApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinyao.bean.common.GlobalField;
import com.xinyao.util.RandomNumberUtil;
import com.xinyao.util.RedisUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

public class SendMsg {

    private static RedisUtil redisUtil;

    public static String sendMsg(String phone){
        String number = RandomNumberUtil.getPhoneMessage();
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("https://utf8api.smschinese.cn/");
        //在头文件中设置转码
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        NameValuePair[] data ={ new NameValuePair("Uid", GlobalField.REGINDID),new NameValuePair("Key", GlobalField.ACCESSKEYID),new NameValuePair("smsMob",phone),new NameValuePair("smsText", number)};
        post.setRequestBody(data);
        try {
            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            //HTTP状态码
            System.out.println("statusCode:" + statusCode);
            for (Header h : headers) {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            //打印返回消息状态
            redisUtil.set(GlobalField.REDISKEY + phone, number, 60 * 5L);
            post.releaseConnection();
            return number;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
