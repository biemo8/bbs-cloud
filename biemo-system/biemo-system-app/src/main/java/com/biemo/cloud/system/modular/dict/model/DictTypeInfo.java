package com.biemo.cloud.system.modular.dict.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 字典类型详情
 *
 *
 * @date 2018-07-25-上午11:26
 */
@Data
public class DictTypeInfo {

    /**
     * 字典类型id
     */
    @ApiModelProperty("字典类型id")
    private String dictTypeId;

    /**
     * 类型1：业务类型2：系统类型
     */
    @ApiModelProperty("类型1：业务类型2：系统类型")
    private Integer dictTypeClass;

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
     * 字典描述
     */
    @ApiModelProperty("字典描述")
    private String dictTypeDesc;

    /**
     * 状态1：启用2：禁用
     */
    @ApiModelProperty("状态1：启用2：禁用")
    private Integer status;

    /**
     * 应用编码
     */
    @ApiModelProperty("应用编码")
    private String appCode;
    /**
     * 字典类型排序
     */
    @ApiModelProperty("字典类型排序")
    private BigDecimal orderNo;

}
