package com.sdpzhong.dev.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@TableName(value = "t_user")
@ApiModel("UserSimplePo")
@Accessors(chain = true)
public class UserSimplePo implements Serializable {
    /**
     * 用户编号
     */
    @ApiModelProperty("用户编号")
    @TableId(value = "userId", type = IdType.ASSIGN_UUID)
    private String uid;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    @TableField(value = "username")
    private String username = "";

    /**
     * 性别 0-男 ｜ 1-女 ｜ 2-未知
     */
    @ApiModelProperty("性别")
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 简介
     */
    @ApiModelProperty("简介")
    @TableField(value = "sign")
    private String sign = "";

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    @TableField(value = "email")
    private String email = "";
}
