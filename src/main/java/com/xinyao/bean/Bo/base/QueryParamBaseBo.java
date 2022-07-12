package com.xinyao.bean.Bo.base;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * @author ：陈鹏
 * @date ：2022/7/12 11:49
 * @description：
 */
@Getter
@Setter
public class QueryParamBaseBo {

    @ApiModelProperty(value = "当前页")
    private Integer pageNo=0;

    @ApiModelProperty(value = "每页行数")
    private Integer pageSize=10;

    @ApiModelProperty(hidden = true)
    private Integer page;

    public Integer getPage() {
        return (this.pageNo-1)*this.pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
