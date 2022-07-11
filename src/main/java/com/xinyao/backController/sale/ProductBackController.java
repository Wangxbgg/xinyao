package com.xinyao.backController.sale;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Product;
import com.xinyao.service.sale.IProductService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Api(tags = "后台管理-商品")
@RestController
@RequestMapping("/productBack")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = Product.class),
})
public class ProductBackController {

    @Autowired
    private IProductService productService;

    @PostMapping("/getAllList")
    @ApiOperation(value = "查询所有商品")
    public com.baomidou.mybatisplus.extension.api.R getAllList(Page<Product> page, @RequestBody Product product) {
        return com.baomidou.mybatisplus.extension.api.R.ok(productService.getAllList(page, product));
    }

    @GetMapping("/getInfoById")
    @ApiOperation(value = "根据id查询商品")
    public R getInfoById(Long id) {
        return R.ok(productService.getById(id));
    }

    @PostMapping("/createOrUpdate")
    @ApiOperation(value = "新增或者删除商品信息")
    public R create(@RequestBody Product product) {
        Integer index = productService.createOrUpdate(product);
        if (index == 0) {
            return R.failed("操作失败！！！");
        } else if (index == 2) {
            return R.failed("商品名称已存在！！！");
        } else if (index == 3) {
            return R.failed("商品编号已存在！！！");
        }
        return R.ok("操作成功！");
    }

    @GetMapping("/deleteById")
    @ApiOperation(value = "根据id删除商品")
    public R deleteById(Long id) {
        return R.isOk(productService.deleteById(id), "删除");
    }
}

