package com.xinyao.bean.sale.vo;

import com.xinyao.bean.sale.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderVo extends Order {

    @ApiModelProperty
    private List<OrderProductVo> orderVoList;

}
