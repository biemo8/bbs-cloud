package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysPermission;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.model.params.SysPermissionParam;
import com.biemo.cloud.system.modular.sys.model.result.SysPermissionResult;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysPermissionParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(SysPermissionParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysPermissionParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    SysPermissionResult findBySpec(SysPermissionParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    List<SysPermissionResult> findListBySpec(SysPermissionParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    PageResult findPageBySpec(SysPermissionParam param);

    /**
     * 获取用户资源url
     *
     *
     * @Date 2019-09-12 09:31
     */
    Set<String> getUserResources(List<RoleInfo> roleInfos);

    /**
     * 新增权限
     *
     * @param permission 权限实体
     *
     * @Date 2019-09-12 09:31
     */
    Long addPermission(SysPermission permission);

    /**
     * 设置权限状态
     *
     * @param permissionIds 权限ID组
     * @param status        设置的权限状态 1：启用 2：禁用
     *
     * @Date 2019-09-12 09:31
     */
    int setPermissionStatus(List<Long> permissionIds, StatusEnum status);

    /**
     * 分页获取权限列表
     *
     * @param page           分页
     * @param appId          应用ID，可以为空
     * @param permissionName 权限名称，模糊查询
     * @param status         权限状态，1：启用，2：禁用 "":所有
     *
     * @Date 2019-09-12 09:31
     */
    Page getPermissionPageList(Page<Map<String, Object>> page, Long appId, String permissionName, Integer status);

    /**
     * 获取所有权限列表
     *
     * @param appId  应用ID，可以为空
     * @param status 权限状态，1：启用，2：禁用 "":所有
     *
     * @Date 2019-09-12 09:31
     */
    List<Map<String, Object>> getPermissionList(Long appId, Integer status);

    /**
     * 分级加载权限-角色分配权限使用
     *
     * @param appId    应用ID，可以为空
     * @param parentId 权限上级ID，默认为0
     * @param status   权限状态，1：启用，2：禁用 "":所有
     *
     * @Date 2019-09-12 09:31
     */
    List<Map<String, Object>> getPermissionList4Level(Long appId, Long parentId, Integer status);

    /**
     * 获取权限树
     *
     * @param appId 应用ID
     *
     * @Date 2019-09-12 09:31
     */
    List<Map<String, Object>> getPermissionTree(Long appId);

    /**
     * 删除权限
     *
     *
     * @date 2019/10/27
     */
    void removePermission(Long permissionId);

    /**
     * 获取按钮状态
     *
     *
     * @date 2019/11/3
     */
    List<Map<String,Object>> getAppButtonStatus(String appCode);
}
