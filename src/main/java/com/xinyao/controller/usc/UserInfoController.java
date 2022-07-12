package com.xinyao.controller.usc;

import com.xinyao.bean.Bo.IDBo;
import com.xinyao.bean.Bo.UserBo.UscUserCarBo;
import com.xinyao.bean.Bo.fundBo.AmountDetaliBo;
import com.xinyao.service.usc.Service.UserInfoService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/userInfo")
@Api(tags = "个人中心")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("getUserId")
    @ApiOperation(value = "我的钱包个人信息详情")
    public R getUserId(@RequestParam("Id") Long Id) {
        return userInfoService.users(Id);
    }
    @PostMapping("addUscUser")
    @ApiOperation(value = "绑定个人银行卡信息")
    public R addUscUser(@RequestBody(required = false) UscUserCarBo paramBo) {
        return userInfoService.addUscUser(paramBo);
    }

    @PostMapping("addRecharge")
    @ApiOperation(value = "充值")
    public R addRecharge(@RequestBody(required = false) AmountDetaliBo paramBo) {
        return userInfoService.addRecharge(paramBo);
    }
    @PostMapping("addWithdrawal")
    @ApiOperation(value = "提现")
    public R addWithdrawal(@RequestBody(required = false) AmountDetaliBo paramBo) {
        return userInfoService.addWithdrawal(paramBo);
    }


}
