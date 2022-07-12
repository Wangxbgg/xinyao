package com.xinyao.bean.usc;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.math.BigDecimal;
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
 * 用户中心-用户信息表
 * </p>
 *
 * @author WangXB
 * @since 2022-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("usc_user")
@ApiModel(value="User对象", description="用户中心-用户信息表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "登录密码设置日期")
    private Date passwordDate;

    @ApiModelProperty(value = "交易密码")
    private String dealPassword;

    @ApiModelProperty(value = "交易密码设置时间")
    private Date dealPasswordDate;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户备注")
    private String comment;

    @ApiModelProperty(value = "头像")
    private String picture;

    @ApiModelProperty(value = "所属组织机构ID")
    private Long orgId;

    @ApiModelProperty(value = "是否系统内置用户（0：否，1：是）")
    private Boolean isSystem;

    @ApiModelProperty(value = "创建人id")
    @TableField(fill = FieldFill.INSERT)
    private Long createId;

    @ApiModelProperty(value = "修改人id")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateId;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "创建人姓名")
    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @ApiModelProperty(value = "最后修改人姓名")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;

    @ApiModelProperty(value = "是否删除（0：未删除，1：已删除）")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "1:后台   2:APP")
    private Integer type;

    @ApiModelProperty(value = "登录失败次数")
    private Integer failNum;

    @ApiModelProperty(value = "是否实名认证（0：未认证 1：已认证）")
    private Integer isAttestation;

    @ApiModelProperty(value = "实名认证时间")
    private Date attestationDate;

    @ApiModelProperty(value = "账户余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "真实姓名")
    private String trueName;

    @ApiModelProperty(value = "身份证号")
    private String idCar;

    @ApiModelProperty(value = "区块链地址")

    private String blockchainAddress;
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
