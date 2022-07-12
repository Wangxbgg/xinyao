package com.xinyao.bean.Bo.fundBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WaterBo {
    @ApiModelProperty(value = "用户Id")
    private Long Id;
    @ApiModelProperty(value = "金额")
    private Double amount;
    @ApiModelProperty(value = "银行卡号")
    private Integer carnNumber;
}
