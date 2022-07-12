package com.xinyao.bean.sale.vo;

import com.xinyao.bean.sale.Transfer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class TransferVo extends Transfer {

    @ApiModelProperty("转赠商品信息")
    private ProductVo productVo;

}
