package com.biemo.cloud.system.modular.sys.model.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询菜单列表的条件
 *
 *
 * @date 2018-02-27 15:24
 */
@Data
@ApiModel
public class MenuCondition {

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String menuName;

    /**
     * 父级菜单名称
     */
    @ApiModelProperty("父级菜单名称")
    private String pName;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private String appId;


    private Integer pageNo;


    private Integer pageSize;

}
