package com.sdpzhong.dev.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "UserLoginFormDto")
public class UserLoginFormDto {
    /*
     *  用户名
     * */
    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String username;

    /*
     * 密码（MD5 加密）
     * */
    @ApiModelProperty(value = "密码（需进行MD5加密）")
    @NotBlank
    private String password;

}
