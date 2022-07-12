package com.xinyao.bean.sale;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sale_product")
@ApiModel(value="Product对象", description="商品表")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品编码")
    private String number;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity;

    @ApiModelProperty(value = "商品权益")
    private String interests;

    @ApiModelProperty(value = "商品图片地址")
    private String iconUrl;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal amount;

    @ApiModelProperty(value = "发行版本")
    private String issueVersion;

    @ApiModelProperty(value = "发行数量")
    private Integer issueQuantity;

    @ApiModelProperty(value = "发行方")
    @TableField("Issued")
    private String Issued;

    @ApiModelProperty(value = "发行时间")
    private Date issueTime;

    @ApiModelProperty(value = "预售时间")
    private Date bookingTime;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "藏馆id")
    private Integer collectionsId;

    @ApiModelProperty(value = "藏馆名称")
    private String collectionsName;

    @ApiModelProperty(value = "系列id")
    private Integer seriesId;

    @ApiModelProperty(value = "系列名称")
    private String seriesName;

    @ApiModelProperty(value = "预购须知")
    private String remark;

    @ApiModelProperty(value = "是否上架（0：没上架 1：上架）")
    private Integer isShelves;

    @ApiModelProperty(value = "商品类型（0：典藏 1：限量）")
    private Integer type;

    @ApiModelProperty(value = "排序字段 ")
    private Integer sort;

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
