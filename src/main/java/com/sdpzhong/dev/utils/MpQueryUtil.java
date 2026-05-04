package com.sdpzhong.dev.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.sdpzhong.dev.entity.dto.page.QueryFilterDto;
import com.sdpzhong.dev.entity.dto.page.QuerySortDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MyBatis-Plus 查询工具类
 * 提供动态筛选、多列排序等通用查询构建能力，简化基于 MP 的条件查询开发
 */
public class MpQueryUtil {

    /**
     * 缓存实体类的属性名与数据库列名的映射关系，避免重复解析表结构
     * Key: 实体类Class | Value: 键-属性名，值-数据库列名
     */
    private static final Map<Class<?>, Map<String, String>> CACHE = new HashMap<>();

    /**
     * 获取实体类属性名与数据库列名的映射关系
     * 优先从缓存获取，缓存未命中时解析表结构并缓存结果
     *
     * @param clazz 实体类Class
     * @param <T>   实体类泛型
     * @return 键：实体类属性名，值：对应的数据库列名
     */
    public static <T> Map<String, String> getFieldMap(Class<T> clazz) {
        if (CACHE.containsKey(clazz)) {
            return CACHE.get(clazz);
        }
        TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
        Map<String, String> map = new HashMap<>();
        for (TableFieldInfo f : tableInfo.getFieldList()) {
            map.put(f.getProperty(), f.getColumn());
        }
        CACHE.put(clazz, map);
        return map;
    }

    // ====================== 多条件筛选 ======================

    /**
     * 构建动态筛选条件（基于QueryWrapper）
     * 支持多字段、多操作符的动态筛选，仅允许筛选指定的字段，避免非法字段查询
     *
     * @param wrapper     MyBatis-Plus的查询条件构造器
     * @param clazz       实体类Class，用于解析属性与列名映射
     * @param filterList  筛选参数列表，包含字段名、操作符、筛选值
     * @param allowFields 允许筛选的字段列表（实体类属性名），非白名单字段会被过滤
     * @param <T>         实体类泛型
     */
    public static <T> void buildFilter(
            QueryWrapper<T> wrapper,
            Class<T> clazz,
            List<QueryFilterDto> filterList,
            List<String> allowFields
    ) {
        if (filterList == null || filterList.isEmpty()) return;
        Map<String, String> fieldMap = getFieldMap(clazz);

        for (QueryFilterDto f : filterList) {
            String field = f.getField();
            String op = f.getOp();
            Object val = f.getValue();
            String column = fieldMap.get(field);

            // 只有这 1 个 continue，满足规范
            if (!allowFields.contains(field) || val == null || StringUtils.isBlank(op) || column == null) {
                continue;
            }

            switch (op.toLowerCase()) {
                case "eq":
                    wrapper.eq(column, val);
                    break;
                case "ne":
                    wrapper.ne(column, val);
                    break;
                case "like":
                    wrapper.like(column, val);
                    break;
                case "likeleft":
                    wrapper.likeLeft(column, val);
                    break;
                case "likeright":
                    wrapper.likeRight(column, val);
                    break;
                case "gt":
                    wrapper.gt(column, val);
                    break;
                case "lt":
                    wrapper.lt(column, val);
                    break;
                case "ge":
                    wrapper.ge(column, val);
                    break;
                case "le":
                    wrapper.le(column, val);
                    break;
                default:
                    break;

            }
        }
    }

    // ====================== 多列排序 ======================

    /**
     * 构建多列动态排序条件（基于QueryWrapper）
     * 支持不同字段的升降序排序，若未传入有效排序条件则使用默认排序规则
     *
     * @param wrapper      MyBatis-Plus查询条件构造器
     * @param clazz        实体类Class，用于解析属性与列名映射
     * @param sortList     排序参数列表，包含字段名、排序方向（asc/desc）
     * @param allowFields  允许排序的字段列表（实体类属性名），非白名单字段会被过滤
     * @param defaultField 默认排序字段（实体类属性名），当无有效排序条件时启用
     * @param defaultDesc  默认排序方向，true=降序（desc），false=升序（asc）
     * @param <T>          实体类泛型
     */
    public static <T> void buildSort(
            QueryWrapper<T> wrapper,
            Class<T> clazz,
            List<QuerySortDto> sortList,
            List<String> allowFields,
            String defaultField,
            boolean defaultDesc
    ) {
        Map<String, String> fieldMap = getFieldMap(clazz);
        boolean sorted = false;

        // 解析传入的排序条件
        if (sortList != null && !sortList.isEmpty()) {
            for (QuerySortDto s : sortList) {
                String field = s.getField();
                String order = s.getOrder();
                // 过滤非法排序条件：非白名单字段、排序方向为空
                String column = fieldMap.get(field);
                if (!allowFields.contains(field) || order == null || column == null) continue;

                // 根据排序方向构建排序条件
                boolean isAsc = "asc".equalsIgnoreCase(order);
                if (isAsc) {
                    wrapper.orderByAsc(column);
                } else {
                    wrapper.orderByDesc(column);
                }
                sorted = true;
            }
        }

        // 无有效排序条件时，使用默认排序规则
        if (!sorted && defaultField != null) {
            String column = fieldMap.get(defaultField);
            if (column != null) { // 增加列名非空判断，避免NPE
                if (defaultDesc) {
                    wrapper.orderByDesc(column);
                } else {
                    wrapper.orderByAsc(column);
                }
            }
        }
    }
}