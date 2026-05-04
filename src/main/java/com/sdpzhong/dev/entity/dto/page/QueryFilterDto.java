package com.sdpzhong.dev.entity.dto.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "QueryFilterDto")
public class QueryFilterDto {
    /**
     * 实体驼峰字段名
     */
    @ApiModelProperty(value = "过滤实体字段")
    private String field;
    /**
     * 操作符：eq/ne/like/ge/le/gt/lt
     */
    @ApiModelProperty(value = "操作符: eq/ne/like/likeleft/likeright/ge/le/gt/lt")
    private String op;
    /**
     * 条件值
     */
    @ApiModelProperty(value = "条件值")
    private String value;
}
