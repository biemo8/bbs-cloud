package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysPermissionResource;
import com.biemo.cloud.system.modular.sys.model.params.SysPermissionResourceParam;
import com.biemo.cloud.system.modular.sys.model.result.SysPermissionResourceResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限资源表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysPermissionResourceService extends IService<SysPermissionResource> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysPermissionResourceParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(SysPermissionResourceParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysPermissionResourceParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    SysPermissionResourceResult findBySpec(SysPermissionResourceParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    List<SysPermissionResourceResult> findListBySpec(SysPermissionResourceParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    PageResult findPageBySpec(SysPermissionResourceParam param);

    /**
     * 权限绑定资源
     *
     *
     * @Date 2019/9/26 23:04
     */
    int permissionBindResource(Long permissionId, List<String> resourceList);

    /**
     * 解除绑定资源
     *
     *
     * @Date 2019/9/26 23:04
     */
    int cancelPermissionBindResource(Long permissionId, List<String> resourceList);

    /**
     * 获取权限绑定的资源
     *
     *
     * @Date 2019/9/26 23:04
     */
    List<Map<String, Object>> getPermissionBindResourceList(Long permissionId);

    /**
     * 获取权限的资源
     *
     *
     * @Date 2019/9/26 23:04
     */
    Map<String, Object> getPermissionResource(Long appId, Long permissionId);

    /**
     * 获取权限的资源列表
     *
     *
     * @Date 2019/9/26 23:04
     */
    Map<String, Object> getResourceList4Permission(Long appId, Long permissionId);

}
