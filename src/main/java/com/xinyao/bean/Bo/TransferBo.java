package com.xinyao.bean.Bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TransferBo {

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品数量")
    private Integer productQuantity;

    @ApiModelProperty("受赠人手机号")
    private String phone;

    @ApiModelProperty("交易密码")
    private String dealPassword;

}
