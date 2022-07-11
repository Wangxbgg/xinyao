package com.xinyao.controller.sale;


import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.service.sale.IOrderService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
@RestController
@RequestMapping("/order")
@Api("APP端-订单")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = OrderVo.class),
})
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("createOrder")
    public R createOrder(@RequestBody OrderVo orderVo){
        return R.ok(orderService.createOrder(orderVo));
    }

}

