package com.xinyao.controller.sale;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Transfer;
import com.xinyao.service.sale.ITransferService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 转赠记录表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
@Api(tags = "APP端-转赠记录")
@RestController
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private ITransferService transferService;

    @ApiOperation(value = "转赠记录列表")
    @GetMapping("getPageList")
    public R getPageList(Page<Transfer> page, Integer status, Integer type) {
        return R.ok(transferService.getPageList(page, status, type));
    }

    @ApiOperation(value = "转赠详情")
    @GetMapping("getOneById")
    public R getOneById(Long id) {
        return R.ok(transferService.getOneById(id));
    }

    @ApiOperation(value = "取消转赠")
    @GetMapping("cancelTransfer")
    public R cancelTransfer(Long id) {
        return R.isOk(transferService.cancelTransfer(id), "取消");
    }

    @ApiOperation(value = "收下藏品")
    @GetMapping("takeItProduct")
    public R takeItProduct(Long id) {
        return R.isOk(transferService.takeItProduct(id), "接收");
    }

}

