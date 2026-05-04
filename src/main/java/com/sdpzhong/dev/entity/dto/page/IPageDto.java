package com.sdpzhong.dev.entity.dto.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "IPageDto")
public class IPageDto {

    /* 页码 */
    @ApiModelProperty(value = "页码", example = "1")
    private final Long current = 1L;

    /* 页容量 */
    @ApiModelProperty(value = "页容量", example = "20")
    private final Long size = 20L;

    /* 筛选 */
    @ApiModelProperty(value = "筛选, exp: [{field:'username', op: 'like', value: 'sdpzhong'}]")
    private List<QueryFilterDto> filters;


    /* 排序 */
    @ApiModelProperty(value = "排序, exp: [{field: 'createTime', order: 'desc'}]")
    private List<QuerySortDto> sorts;
}
