package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysRolePermission;
import com.biemo.cloud.system.modular.sys.model.params.SysRolePermissionParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRolePermissionResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysRolePermissionResult> customList(@Param("paramCondition") SysRolePermissionParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysRolePermissionParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysRolePermissionResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysRolePermissionParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysRolePermissionParam paramCondition);

    /**
     * 批量添加角色权限 关联关系
     *
     *
     * @Date 2019/9/26 22:58
     */
    int addBatch(Map<String, Object> param);

    /**
     * 清空为角色分配的权限
     *
     *
     * @Date 2019/9/26 22:58
     */
    int deleteByRoleID(Map<String, Object> param);

    /**
     * 根据角色信息查询角色包含的权限信息
     *
     * @param appId   应用id
     * @param roleIds 角色ID集合
     *
     * @Date 2019/9/26 22:58
     */
    List<Map<String, Object>> findPermissionByRoleInfo(@Param("appId") Long appId, @Param("roleIds") Set<Long> roleIds);

}
