package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.api.model.UserInfo;
import com.biemo.cloud.system.modular.sys.entity.SysRole;
import com.biemo.cloud.system.modular.sys.model.RoleParam;
import com.biemo.cloud.system.modular.sys.model.params.SysRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRoleResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysRoleResult> customList(@Param("paramCondition") SysRoleParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysRoleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysRoleResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysRoleParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysRoleParam paramCondition);

    /**
     * 获取角色列表
     *
     *
     * @Date 2019/9/26 22:56
     */
    List<RoleParam> findRoleList(SysRole role);

    /**
     * 获取角色分页列表
     *
     *
     * @Date 2019/9/26 22:56
     */
    List<RoleParam> findRolePage(Page<RoleParam> page, @Param("param") SysRole role);

    /**
     * 获取角色详情
     *
     *
     * @Date 2019/9/26 22:57
     */
    RoleParam findRoleDetail(SysRole role);

    /**
     * 获取角色详情
     *
     *
     * @Date 2019/9/26 22:57
     */
    List<Map<String, Object>> findRoleInfoList(Page page, @Param("roleIds") List<Long> roleIds);

    /**
     * 获取角色列表 根据应用id
     *
     *
     * @Date 2019/9/26 22:57
     */
    List<Map<String, Object>> findRoleListByAppId(@Param("roleIds") List<Long> roleIds, @Param("appId") Long appId);

    /**
     * 获取角色列表，根据类型
     *
     *
     * @Date 2019/9/26 22:57
     */
    List<RoleParam> findRoleListByType(@Param("appId") Long appId, @Param("type") String type);

    /**
     * 获取账户角色
     *
     *
     * @Date 2019/9/26 22:57
     */
    List<Map<String, Object>> getRoleById(@Param("accountId") Long accountId);

    /**
     * 获取用户列表通过角色id
     *
     *
     * @Date 2019/12/1 16:14
     */
    List<UserInfo> getUsersByRoleId(@Param("roleId") Long roleId);

    /**
     * 获取用户列表通过角色id
     *
     *
     * @Date 2019/12/1 16:14
     */
    Page<UserInfo> getUsersByRoleIdByPage(@Param("page") Page page, @Param("roleId") Long roleId);

    /**
     * 获取用户列表，通过用户id和角色id集合
     *
     *
     * @Date 2019/12/1 16:14
     */
    List<UserInfo> getUsersByUserIdsOrRoleIds(@Param("accountIds") Set<Long> accountIds, @Param("roleIds") Set<Long> roleIds);
}
