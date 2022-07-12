package com.xinyao.bean.Bo.fundBo;

import com.xinyao.bean.Bo.base.QueryParamBaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowingBo extends QueryParamBaseBo {
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "0,全部,1,支出,2收入")
    private Integer transaction;

}
