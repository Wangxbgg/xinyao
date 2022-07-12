package com.xinyao.bean.Bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IDBo {
    @ApiModelProperty(value = "姓名")
    private String cardNo;
    @ApiModelProperty(value = "身份证")
    private String realName;
    @ApiModelProperty(value = "用户id")
    private Long id;
}
