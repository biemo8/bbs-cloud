package com.biemo.cloud.system.modular.sys.model.params;

import lombok.Data;
import com.biemo.cloud.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 菜单
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysMenuParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 菜单id
     */
    @ApiModelProperty("菜单id")
    private Long menuId;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 菜单编码
     */
    @ApiModelProperty("菜单编码")
    private String code;

    /**
     * 菜单父id
     */
    @ApiModelProperty("菜单父id")
    private Long pid;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 应用id
     */
    @ApiModelProperty("应用id")
    private Long appId;

    /**
     * 资源code
     */
    @ApiModelProperty("资源code")
    private String resCode;

    /**
     * 菜单提示
     */
    @ApiModelProperty("菜单提示")
    private String menuTips;

    /**
     * 是否隐藏
     */
    @ApiModelProperty("是否隐藏")
    private String hiddenFlag;

    /**
     * 排序编号
     */
    @ApiModelProperty("排序编号")
    private BigDecimal orderNo;

    /**
     * 请求地址
     */
    @ApiModelProperty("请求地址")
    private String url;


    @ApiModelProperty("组件地址")
    private String component;

    /**
     * 菜单状态(1:启用,2:禁用)
     */
    @ApiModelProperty("菜单状态(1:启用,2:禁用)")
    private Integer status;

    /**
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private Long createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 更新人
     */
    @ApiModelProperty(hidden = true)
    private Long updateUser;

    /**
     * 更新时间
     */
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @Override
    public String checkParam() {
        return null;
    }

}
