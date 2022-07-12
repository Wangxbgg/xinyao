package com.xinyao.controller.sale;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Order;
import com.xinyao.bean.sale.vo.OrderVo;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.service.sale.IOrderService;
import com.xinyao.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "APP端-订单")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = OrderVo.class),
})
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @ApiOperation("订单---订单列表")
    @GetMapping("getAllList")
    public R getAllList(Page<Order> page, Integer status) {
        return R.ok(orderService.getAllList(page, status));
    }

    @ApiOperation("订单---订单详情")
    @GetMapping("selectById")
    public R selectById(Long id) {
        return R.ok(orderService.selectById(id));
    }

    @ApiOperation("订单---创建订单")
    @PostMapping("createOrder")
    public R createOrder(@RequestBody OrderVo orderVo){
        return R.ok(orderService.createOrder(orderVo));
    }

    @ApiOperation("订单---确认支付")
    @PostMapping("confirmOrder")
    public R confirmOrder(@RequestBody OrderVo orderVo){
        return R.isOk(orderService.confirmOrder(orderVo), "支付");
    }

    @ApiOperation("订单---取消订单")
    @PostMapping("cancelOrder")
    public R cancelOrder(@RequestBody OrderVo orderVo){
        return R.isOk(orderService.cancelOrder(orderVo), "取消");
    }

}

