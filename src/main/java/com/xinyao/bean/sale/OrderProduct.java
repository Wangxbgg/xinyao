package com.xinyao.bean.sale;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单商品表
 * </p>
 *
 * @author WangXB
 * @since 2022-07-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sale_order_product")
@ApiModel(value="OrderProduct对象", description="订单商品表")
public class OrderProduct extends Model<OrderProduct> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderSn;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "商品编号")
    private String productNumber;

    @ApiModelProperty(value = "商品数量")
    private BigDecimal productQuantity;

    @ApiModelProperty(value = "购买时价格")
    private BigDecimal buyPrice;

    @ApiModelProperty(value = "是否删除（0：未删除 1：已删除）")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "创建人姓名")
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "创建人时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "修改人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty(value = "修改人姓名")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;

    @ApiModelProperty(value = "修改人时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
