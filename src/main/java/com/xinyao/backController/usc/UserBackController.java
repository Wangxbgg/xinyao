package com.xinyao.backController.usc;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.usc.User;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Api(tags = "后台管理-用户")
@RestController
@RequestMapping("/userBack")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = User.class),
})
public class UserBackController {

    @Autowired
    private IUserService userService;

    @PostMapping("/getAllList")
    @ApiOperation(value = "查询所有用户")
    public R getAllList(Page<User> page, @RequestBody User user) {
        return R.ok(userService.getAllList(page, user));
    }

    @GetMapping("/getInfoById")
    @ApiOperation(value = "根据id查询用户")
    public R getInfoById(Long id) {
        return R.ok(userService.getById(id));
    }

    @PostMapping("/createOrUpdate")
    @ApiOperation(value = "新增或者删除用户信息")
    public R create(@RequestBody User user) {
        Integer index = userService.createOrUpdate(user);
        if (index == 0) {
            return R.failed("操作失败！！！");
        } else if (index == 2) {
            return R.failed("用户账号已存在！！！");
        } else if (index == 3) {
            return R.failed("用户名称已存在！！！");
        } else if (index == 4) {
            return R.failed("手机号已存在！！！");
        }
        return R.ok("操作成功！");
    }

    @GetMapping("/deleteById")
    @ApiOperation(value = "根据id删除用户")
    public R deleteById(Long id) {
        return R.isOk(userService.deleteById(id), "删除");
    }
}

