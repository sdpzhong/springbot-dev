package com.sdpzhong.dev.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "UserRegisterFormDto")
public class UserRegisterFormDto {

    @ApiModelProperty(value = "用户名")
    @NotBlank
    @Size(min = 6, max = 20)
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank
    // @Pattern(regexp = "")
    private String password;

    @ApiModelProperty(value = "邮箱")
    @NotBlank
    @Email
    private String email;
}
