package com.xinyao.controller.sale;

import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.util.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Product;
import com.xinyao.service.sale.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Api(tags = "APP端-商品")
@RestController
@RequestMapping("/product")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = ProductVo.class),
})
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping("/getAllList")
    @ApiOperation(value = "查询所有商品")
    public R getAllList(Page<Product> page, @RequestBody Product product) {
        return R.ok(productService.getAllList(page, product));
    }

    @GetMapping("/getInfoById")
    @ApiOperation(value = "根据id查询商品")
    public R getInfoById(Long id) {
        Product product = productService.getInfoById(id);
        if (product == null) {
            return R.failed("查询失败，请确认该商品是否下架！！！");
        }
        return R.ok(product);
    }
}

