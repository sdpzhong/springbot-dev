package com.sdpzhong.dev.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
@ApiModel("UserModel")
@Accessors(chain = true)
public class User implements Serializable {

    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    @ApiModelProperty("uid")
    @TableField(value = "uid", fill = FieldFill.INSERT)
    private String uid;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @TableField(value = "username")
    private String username;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    @TableField(value = "nickname")
    private String nickname = "";

    /**
     * 密码
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "密码", hidden = true)
    @TableField(value = "password", select = false)
    private String password;

    /**
     * 密码盐
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "密码盐", hidden = true)
    @TableField(value = "password_salt", select = false)
    private String passwordSalt;

    /**
     * 状态 0-正常 ｜ 1-禁用
     */
    @ApiModelProperty("状态")
    @TableField(value = "status")
    private Integer status;

    /**
     * 性别 0-男 ｜ 1-女 ｜ 2-未知
     */
    @ApiModelProperty("性别")
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    @TableField(value = "avatar")
    private String avatar = "";

    /**
     * 简介
     */
    @ApiModelProperty("简介")
    @TableField(value = "bio")
    private String bio = "";

    /**
     * 签名
     */
    @ApiModelProperty("签名")
    @TableField(value = "sign")
    private String sign = "";

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @TableField(value = "email")
    private String email = "";

    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    @TableField(value = "mobile")
    private String mobile = "";

    /**
     * 出生年月
     */
    @ApiModelProperty("出生年月")
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "deleted", select = false)
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
