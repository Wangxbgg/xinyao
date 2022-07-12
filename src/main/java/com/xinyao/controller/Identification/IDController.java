package com.xinyao.controller.Identification;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xinyao.ApiService.ApiID;
import com.xinyao.bean.Bo.IDBo;
import com.xinyao.mapper.usc.UserMapper;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "第三方对接通讯")
@RestController
@RequestMapping(value = "/api")
public class IDController {
@Autowired
private UserMapper userMapper;

    @ApiOperation(value = "身份证识别")
    @PostMapping("getID")
    public Object getID(@RequestBody(required = false) IDBo paramBo) throws Exception {
        if(paramBo.getId()==null){
            return R.ok("请先登入");
        }
        String UsernameID = ApiID.getID(paramBo);
        if (UsernameID!=null){
            JSONObject jsonObjectPageNo = JSON.parseObject(UsernameID);
            JSONObject isok = jsonObjectPageNo.getJSONObject("result");
            String isoks = isok.getString("isok");
            Integer isAttestation= null;
            if (isoks.equals("true")){
                isAttestation = 1;
                String blockchainAddress = "dhsjkfhs*****4898ds";
                userMapper.updateByPrimaryKey(paramBo.getId(),isAttestation,blockchainAddress);
                return R.ok("身份识别成功");
            }else {
                return R.ok("身份识别失败，请输入正确的身份证其对应姓名");
            }
        }
        return R.ok();
    }
}
