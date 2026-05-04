package com.sdpzhong.dev.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "UserLoginResponseVo")
public class UserLoginResponseVo {

    @ApiModelProperty(value = "schema")
    private String schema = "Bearer";

    @ApiModelProperty(value = "登录凭证")
    private String token;

    @ApiModelProperty(value = " 剩余有效期（秒）")
    private long timeout;

    @ApiModelProperty(value = "过期时间")
    private long expires;
}
