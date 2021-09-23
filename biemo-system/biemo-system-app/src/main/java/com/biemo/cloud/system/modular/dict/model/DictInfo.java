package com.biemo.cloud.system.modular.dict.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 字典详细信息
 *
 *
 * @date 2018-07-25-上午10:55
 */
@Data
public class DictInfo {

    /**
     * 字典id
     */
    @ApiModelProperty("字典id")
    private String dictId;

    /**
     * 字典类型编码
     */
    @ApiModelProperty("字典类型编码")
    private String dictTypeCode;

    /**
     * 字典类型名称
     */
    @ApiModelProperty("字典类型名称")
    private String dictTypeName;

    /**
     * 字典编码
     */
    @ApiModelProperty("字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @ApiModelProperty("字典名称")
    private String dictName;

    /**
     * 简称
     */
    @ApiModelProperty("简称")
    private String dictShortName;

    /**
     * 字典简拼
     */
    @ApiModelProperty("字典简拼")
    private String dictShortCode;

    /**
     * 上级代码id
     */
    @ApiModelProperty("上级代码id")
    private String parentId;

    /**
     * 上级字典名称
     */
    @ApiModelProperty("上级字典名称")
    private String parentName;

    /**
     * 状态(1:启用,2:禁用)
     */
    @ApiModelProperty("状态(1:启用,2:禁用)")
    private Integer status;

    /**
     * 应用编码
     */
    @ApiModelProperty("应用编码")
    private String appCode;

    /**
     * 字典排序
     */
    @ApiModelProperty("字典排序")
    private BigDecimal orderNo;
}
