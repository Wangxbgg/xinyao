package com.xinyao.controller.sale;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.Bo.TransferBo;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.ProductUser;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.service.sale.IProductUserService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户商品表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
@Api(tags = "APP端---藏馆")
@RestController
@RequestMapping("/productUser")

@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = TransferBo.class),
})
public class ProductUserController {

    @Autowired
    private IProductUserService productUserService;

    @GetMapping("/getInfoByUserId")
    @ApiOperation(value = "藏馆列表")
    public R getInfoByUserId(Page<ProductUser> page, Integer collectionsId) {
        return R.ok(productUserService.getInfoByUserId(page, collectionsId));
    }

    @GetMapping("/getListByProductId")
    @ApiOperation(value = "藏馆列表---详情")
    public R getListByProductId(Page<ProductUser> page, Long productId) {
        return R.ok(productUserService.getPageByProductId(page, productId));
    }

    @PostMapping("/transfer")
    @ApiOperation(value = "藏馆列表---转赠")
    public R transfer(@RequestBody TransferBo transferBo) {
        return R.ok(productUserService.transfer(transferBo));
    }

}

