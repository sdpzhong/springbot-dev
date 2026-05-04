package com.sdpzhong.dev.entity.dto.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "QuerySortDto")
public class QuerySortDto {
    /**
     * 实体驼峰字段名
     */
    @ApiModelProperty(value = "字段名称")
    private String field;
    /**
     * order: asc / desc
     */
    @ApiModelProperty(value = "排序规则: asc / desc (不区分大小写)")
    private String order;
}
