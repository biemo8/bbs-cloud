package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysUserRole;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.model.params.SysUserRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysUserRoleResult;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysUserRoleParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(SysUserRoleParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysUserRoleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    SysUserRoleResult findBySpec(SysUserRoleParam param);

    /**
     * 查询列表，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    List<SysUserRoleResult> findListBySpec(SysUserRoleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    PageResult findPageBySpec(SysUserRoleParam param);

    /**
     * 获取用户角色信息
     *
     *
     * @Date 2019-09-12 09:03
     */
    List<RoleInfo> getUserRoleInfo(Long accountId);

}
