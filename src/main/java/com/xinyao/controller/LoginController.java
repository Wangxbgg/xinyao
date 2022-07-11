package com.xinyao.controller;


import com.alibaba.fastjson.JSONObject;
import com.xinyao.bean.common.GlobalConfig;
import com.xinyao.bean.common.GlobalField;
import com.xinyao.bean.common.TokenBean;
import com.xinyao.bean.usc.User;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Maple
 */
@Api(tags = "登录")
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IUserService userService;

    @GetMapping("sendMsg")
    @ApiOperation("发送验证码")
    public R sendMsg(String phone){
        if (StringUtils.isNullOrBlank(phone)) {
            return R.failed("手机号为空！！！");
        }
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
            System.out.println("statusCode:"+statusCode);
            for(Header h : headers){
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            //打印返回消息状态
            System.out.println(result);
            redisUtil.set(GlobalField.REDISKEY+phone, number, 60*5L);

            post.releaseConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.ok();
    }

    @ApiOperation("登录实现---APP端")
    @PostMapping("/auth")
    public R dealersLogin(@RequestBody JSONObject jsonObject){
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        if (StringUtils.isNullOrBlank(phone)) {
            return R.failed("手机号为空！！！");
        }
        if (StringUtils.isNullOrBlank(code)) {
            return R.failed("验证码为空！！！");
        }
        try {
            User user = userService.login(phone, code);
            Long userId = user.getId();
            TokenBean tokenBean = new TokenBean();
            tokenBean.setUserId(userId);
            tokenBean.setAccount(user.getMobile());
            tokenBean.setUserName(user.getName());
            tokenBean.setOrgId(user.getOrgId());
            String token = JWTUtil.createToken(tokenBean);
            redisUtil.remove(GlobalConfig.getRedisUserKey(user.getMobile()));
            redisUtil.set(GlobalConfig.getRedisUserKey(user.getMobile()), token, GlobalConfig.EXPIRE_TIME);

            Map<String, Object> resultMap = new HashMap<>(16);
            resultMap.put("token", token);
            resultMap.put("id", user.getId());
            resultMap.put("account", user.getAccount());
            resultMap.put("mobile", user.getMobile());
            resultMap.put("name", user.getName());
            resultMap.put("picture", user.getPicture());
            return R.ok(resultMap, "登录成功");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return R.failed(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return R.failed("登录失败，请重试");
        }
    }

}
