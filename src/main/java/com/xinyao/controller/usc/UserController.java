package com.xinyao.controller.usc;


import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.bean.usc.User;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 用户中心-用户信息表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-08
 */
@RestController
@RequestMapping("/user")
@Api(tags = "APP端-用户")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = User.class),
})
public class UserController {

    @Autowired
    private IUserService userService;

    @ApiOperation("设置交易密码")
    @PostMapping("setDealPassword")
    public R setDealPassword(@RequestBody Map<String, String> map){
        return R.isOk(userService.setDealPassword(map.get("dealPassword")), "设置");
    }

}

