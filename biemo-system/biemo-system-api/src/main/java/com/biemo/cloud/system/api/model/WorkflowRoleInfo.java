package com.biemo.cloud.system.api.model;

import lombok.Data;

/**
 * 角色详情
 *
 *
 * @Date 2019/12/1 13:11
 */
@Data
public class WorkflowRoleInfo {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

}
