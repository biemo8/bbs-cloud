package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysUserRole;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.model.params.SysUserRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysUserRoleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysUserRoleResult> customList(@Param("paramCondition") SysUserRoleParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysUserRoleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysUserRoleResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysUserRoleParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysUserRoleParam paramCondition);

    /**
     * 获取用户角色信息
     *
     *
     * @Date 2019-09-12 09:05
     */
    List<RoleInfo> getUserRoleInfo(Long accountId);

    /**
     * 获取公司用户角色信息
     *
     *
     * @Date 2019/9/26 22:58
     */
    List<Map<String, Object>> findCompanyUserRoleInfo(Page page, Map<String, Object> param);

    /**
     * 获取用户列表通过角色id
     *
     *
     * @Date 2019/9/26 22:58
     */
    List<Map<String, Object>> selectUserListByRoleId(@Param("companyId") Long companyId, @Param("roleId") Long roleId);

    /**
     * 获取账户的角色列表
     *
     *
     * @Date 2019/9/26 22:58
     */
    List<Long> findRoleIdsByAccountId(@Param("accountId") Long accountId);

    /**
     * 获取用户角色详情
     *
     *
     * @Date 2019/9/26 22:59
     */
    List<SysUserRole> userRoles(@Param("accountId") Long accountId);

}
