package com.xinyao.bean.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class AmountDetailVo {
    @ApiModelProperty(value = "金额")
    private BigDecimal amount;
    @ApiModelProperty(value = "备注")
    private String comment;
    @ApiModelProperty(value = "日期")
    private String createTime;
    @ApiModelProperty(value = "1,支出2收入")
    private Integer transaction;
}
