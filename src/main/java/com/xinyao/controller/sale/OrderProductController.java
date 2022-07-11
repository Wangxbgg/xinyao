package com.xinyao.controller.sale;


import com.xinyao.bean.sale.OrderProduct;
import com.xinyao.bean.sale.vo.ProductVo;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单商品表 前端控制器
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
@RestController
@RequestMapping("/orderProduct")
@ApiResponses({
        @ApiResponse(code = 200,message = "OK",response = OrderProduct.class),
})
public class OrderProductController {

}

