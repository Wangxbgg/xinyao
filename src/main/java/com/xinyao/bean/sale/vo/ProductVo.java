package com.xinyao.bean.sale.vo;

import com.xinyao.bean.sale.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVo extends Product {

    @ApiModelProperty("商品数量")
    private Integer productQuantity;

    @ApiModelProperty("购买时金额")
    private BigDecimal buyPrice;

    @ApiModelProperty("发售时间戳")
    private Long bookingDateTime;

    @ApiModelProperty("商品状态(0：预售中 1：即将开售 2：抢购中 3：已售馨)")
    private Integer status;

    @ApiModelProperty("用户商品id")
    private Long userProductId;

}
