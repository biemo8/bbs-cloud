package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysRolePermission;
import com.biemo.cloud.system.modular.sys.model.params.SysRolePermissionParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRolePermissionResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysRolePermissionParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(SysRolePermissionParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysRolePermissionParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    SysRolePermissionResult findBySpec(SysRolePermissionParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    List<SysRolePermissionResult> findListBySpec(SysRolePermissionParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    PageResult findPageBySpec(SysRolePermissionParam param);

}
