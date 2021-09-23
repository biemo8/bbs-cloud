package com.biemo.cloud.system.modular.sys.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 资源表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@Data
@ApiModel
public class SysResourceResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 资源id
     */
    @ApiModelProperty("资源id")
    private String resourceId;

    /**
     * 应用编码
     */
    @ApiModelProperty("应用编码")
    private String appCode;

    /**
     * 类名称
     */
    @ApiModelProperty("类名称")
    private String className;

    /**
     * 方法名称
     */
    @ApiModelProperty("方法名称")
    private String methodName;

    /**
     * 资源模块编码
     */
    @ApiModelProperty("资源模块编码")
    private String modularCode;

    /**
     * 资源模块名称
     */
    @ApiModelProperty("资源模块名称")
    private String modularName;

    /**
     * 资源编码
     */
    @ApiModelProperty("资源编码")
    private String code;

    /**
     * 资源名称
     */
    @ApiModelProperty("资源名称")
    private String name;

    /**
     * 资源初始化的服务器ip地址
     */
    @ApiModelProperty("资源初始化的服务器ip地址")
    private String ipAddress;

    /**
     * 资源url
     */
    @ApiModelProperty("资源url")
    private String url;

    /**
     * http请求方法
     */
    @ApiModelProperty("http请求方法")
    private String httpMethod;

    /**
     * 是否是方法(Y:页面 N:api)
     */
    @ApiModelProperty("是否是方法(Y:页面 N:api)")
    private String menuFlag;

    /**
     * 是否需要登录(Y:是 N:否)
     */
    @ApiModelProperty("是否需要登录(Y:是 N:否)")
    private String requiredLoginFlag;

    /**
     * 是否需要鉴权标识(Y:是 N:否)
     */
    @ApiModelProperty("是否需要鉴权标识(Y:是 N:否)")
    private String requiredPermissionFlag;

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

}
