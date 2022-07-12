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
 * 转赠记录表
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sale_transfer")
@ApiModel(value="Transfer对象", description="转赠记录表")
public class Transfer extends Model<Transfer> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "转赠方id")
    private Long transferId;

    @ApiModelProperty(value = "转赠方手机号")
    private String transferPhone;

    @ApiModelProperty(value = "受赠方id")
    private Long giftedId;

    @ApiModelProperty(value = "受赠方手机号")
    private String giftedPhone;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "订单商品id")
    private Long orderProductId;

    @ApiModelProperty(value = "转赠数量")
    private Integer transferQuantity;

    @ApiModelProperty(value = "状态（1：已提交 2：转赠中/待接收 3：已完成 4：已取消）")
    private Integer status;

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

    @ApiModelProperty(value = "是否删除（0：未删除 1：已删除）")
    @TableLogic
    private Integer isDeleted;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
