package com.biemo.cloud.system.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资源表
 * </p>
 *
 *
 * @since 2019-10-15
 */
@TableName("biemo_sys_resource")
@Data
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源id
     */
    @TableId(value = "resource_id", type = IdType.ASSIGN_ID)
    private String resourceId;

    /**
     * 应用编码
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 项目编码
     */
    @TableField("project_code")
    private String projectCode;

    /**
     * 类名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 方法名称
     */
    @TableField("method_name")
    private String methodName;

    /**
     * 资源模块编码
     */
    @TableField("modular_code")
    private String modularCode;

    /**
     * 资源模块名称
     */
    @TableField("modular_name")
    private String modularName;

    /**
     * 资源编码
     */
    @TableField("code")
    private String code;

    /**
     * 资源名称
     */
    @TableField("name")
    private String name;

    /**
     * 资源初始化的服务器ip地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 资源url
     */
    @TableField("url")
    private String url;

    /**
     * http请求方法
     */
    @TableField("http_method")
    private String httpMethod;

    /**
     * 是否是方法(Y:页面 N:api)
     */
    @TableField("menu_flag")
    private String menuFlag;

    /**
     * 是否需要登录(Y:是 N:否)
     */
    @TableField("required_login_flag")
    private String requiredLoginFlag;

    /**
     * 是否需要鉴权标识(Y:是 N:否)
     */
    @TableField("required_permission_flag")
    private String requiredPermissionFlag;

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private Long updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

}
