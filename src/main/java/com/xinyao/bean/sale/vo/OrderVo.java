package com.xinyao.bean.sale.vo;

import com.xinyao.bean.sale.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderVo extends Order {

    @ApiModelProperty("订单商品信息")
    private List<OrderProductVo> orderProductVoList;

    @ApiModelProperty("订单支付有效期")
    private Long expirationDate;

    @ApiModelProperty("用户剩余余额")
    private BigDecimal userAmount;

    @ApiModelProperty("用户支付密码")
    private String userDealPassword;

}
