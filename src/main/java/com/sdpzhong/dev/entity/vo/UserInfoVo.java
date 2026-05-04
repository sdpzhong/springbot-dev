package com.sdpzhong.dev.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "用户基本信息")
public class UserInfoVo {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "UID")
    private String uid;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "手机号")
    private String mobile;
}
