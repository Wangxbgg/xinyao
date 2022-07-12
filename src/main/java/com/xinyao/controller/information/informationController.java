package com.xinyao.controller.information;

import com.xinyao.bean.Bo.fundBo.AmountDetaliBo;
import com.xinyao.bean.Bo.fundBo.FlowingBo;
import com.xinyao.service.usc.Service.UserInfoService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "个人信息交易流水")
@RestController
@RequestMapping(value = "/information")
public class informationController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("flowinGlist")
    @ApiOperation(value = "收支明细")
    public R flowinGlist(@RequestBody(required = false) FlowingBo paramBo) {
        return userInfoService.flowinGlist(paramBo);
    }
}
