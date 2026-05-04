package com.sdpzhong.dev.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName t_article
 */
@ApiModel("Article")
@TableName(value = "t_article")
@Data
public class Article implements Serializable {
    /**
     * 文章编号
     */
    @ApiModelProperty("文章编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文章作者ID
     */
    @ApiModelProperty("作者uid")
    @TableField(value = "uid")
    private String uid;

    /**
     * 文章频道ID
     */
    @ApiModelProperty("专栏（频道）id")
    @TableField(value = "channel_id")
    private Long channelId;

    /**
     * 标题
     */
    @ApiModelProperty("标题")
    @TableField(value = "title")
    private String title = "";

    /**
     * 副标题
     */
    @ApiModelProperty("副标题")
    @TableField(value = "subtitle")
    private String subtitle = "";

    /**
     * 内容
     */
    @ApiModelProperty("原文内容")
    @TableField(value = "content")
    private String content = "";

    /**
     * 封面链接
     */
    @ApiModelProperty("文章封面")
    @TableField(value = "cover")
    private String cover = "";

    /**
     * 阅读数
     */
    @ApiModelProperty("阅读数")
    @TableField(value = "read_count")
    private Integer readCount;

    /**
     * 关键字
     */
    @ApiModelProperty("关键字，标签")
    @TableField(value = "keywords")
    private String keywords = "";

    /**
     * 文章状态 草稿 0｜ 待审核1 ｜审核失败2 ｜人工审核3 ｜ 人工审核 4 ｜审核通过5 ｜待发布6 ｜己发布7 ｜ 已封禁 8
     */
    @ApiModelProperty("状态")
    @TableField(value = "status")
    private Integer status;

    /**
     * 文章提交时间
     */
    @ApiModelProperty("提交时间")
    @TableField(value = "submit_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date submitTime;

    /**
     * 发布时间
     */
    @ApiModelProperty("发布时间")
    @TableField(value = "publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

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

    /**
     * 逻辑删除标识
     */
    @ApiModelProperty("deleted")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "deleted", select = false)
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    /**
     * 额外的作者信息
     */
    @ApiModelProperty("作者")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(value = "author", exist = false, select = false)
    private UserSimplePo author;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}