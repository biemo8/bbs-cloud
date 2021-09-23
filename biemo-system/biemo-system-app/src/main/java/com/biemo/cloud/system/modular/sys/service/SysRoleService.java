package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysRole;
import com.biemo.cloud.system.modular.sys.model.RoleParam;
import com.biemo.cloud.system.modular.sys.model.params.SysRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRoleResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysRoleParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(SysRoleParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysRoleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    SysRoleResult findBySpec(SysRoleParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    List<SysRoleResult> findListBySpec(SysRoleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    PageResult findPageBySpec(SysRoleParam param);

    /**
     * 通过账号id获取资源
     *
     *
     * @Date 2019-09-16 16:31
     */
    Set<String> getUserResByAccountId(Long accountId);


    /**
     * 获取用户角色ids
     *
     *
     * @Date 2018/2/28 19:10
     */
    Set<Long> getUserRoles(Long accountId);

    /**
     * 获取用户的权限ids
     *
     *
     * @Date 2018/2/28 19:10
     */
    Set<Long> getUserPermission(Long accountId);

    /**
     * 获取用户的资源ids
     *
     *
     * @Date 2018/2/28 19:10
     */
    Set<String> getUserResources(Long accountId);

    /**
     * 获取用户的资源ids
     *
     *
     * @Date 2018/2/28 19:10
     */
    Set<String> getUserResourcesByApp(Long accountId, Long appId);

    /**
     * 添加角色
     *
     *
     * @Date 2019-09-12 09:31
     */
    void addRole(SysRole role);

    /**
     * 删除角色
     *
     *
     * @Date 2019-09-12 09:31
     */
    void deleteRole(Long roleId);

    /**
     * 修改角色
     *
     *
     * @Date 2019-09-12 09:31
     */
    int updateRole(SysRole role);

    /**
     * 禁用/启用
     *
     *
     * @Date 2019-09-12 09:31
     */
    int updateStatus(Long roleId, int status);

    /**
     * 分页查询角色
     *
     *
     * @Date 2019-09-12 09:31
     */
    Page<RoleParam> findRolePage(SysRole role);

    /**
     * 查询所有角色
     *
     *
     * @Date 2019-09-12 09:31
     */
    List<RoleParam> findRoleList(SysRole role);

    /**
     * 查找角色详情
     *
     *
     * @Date 2019-09-12 09:31
     */
    RoleParam findRoleDetail(SysRole role);

    /**
     * 校验角色名称
     *
     * @param appId    应用ID
     * @param roleId   角色ID
     * @param roleName 角色名称
     *
     * @Date 2019-09-12 09:31
     */
    boolean validateName(Long appId, Long roleId, String roleName);

    /**
     * 验重角色编码
     *
     *
     * @Date 2019-09-12 09:31
     */
    boolean validateCode(Long appId, Long roleId, String roleCode);

    /**
     * 分配权限
     *
     *
     * @Date 2019-09-12 09:31
     */
    void assignPermission(Long roleId, List<Long> permissionIds);

    /**
     * 查看该角色已分配的权限
     *
     * @param roleId 角色ID
     * @param appId  应用ID
     *
     * @Date 2019-09-12 09:31
     */
    List<Map<String, Object>> findPermissions(Long roleId, Long appId);


    /**
     * 查询一个角色所有的权限 Id
     *
     *
     * @Date 2019-09-12 09:31
     */
    List<Long> findRoleAllPermissions(Long roleId);

    /**
     * 获取角色通过账号id
     *
     *
     * @Date 2019/9/26 23:05
     */
    Page selectRoleByAccountId(Page page, Long accountId);

}
