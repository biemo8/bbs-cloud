package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysPermission;
import com.biemo.cloud.system.modular.sys.model.params.SysPermissionParam;
import com.biemo.cloud.system.modular.sys.model.result.SysPermissionResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysPermissionResult> customList(@Param("paramCondition") SysPermissionParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysPermissionParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysPermissionResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysPermissionParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysPermissionParam paramCondition);

    /**
     * 设置权限状态
     *
     *
     * @Date 2019/9/26 22:55
     */
    int setPermissionStatus(Map<String, Object> param);

    /**
     * 获取分页权限列表
     *
     *
     * @Date 2019/9/26 22:55
     */
    List<Map<String, Object>> selectPermissionPageList(Page page, @Param("param") HashMap<String, Object> param);

    /**
     * 获取权限列表
     *
     *
     * @Date 2019/9/26 22:55
     */
    List<Map<String, Object>> selectPermissionList(Map<String, Object> param);

    /**
     * 获取权限tree
     *
     *
     * @Date 2019/9/26 22:55
     */
    List<Map<String, Object>> selectPermissionTree(Map<String, Object> param);

    /**
     * 查找所有权限（checked 为 1 时，代表 该角色已分配该权限）
     *
     *
     * @Date 2019/9/26 22:56
     */
    List<Map<String, Object>> findPermissionByRole(Map<String, Object> param);

    /**
     * 查询一个角色所有的权限 Id
     *
     *
     * @Date 2019/9/26 22:56
     */
    List<Long> findRoleAllPermissions(Map<String, Object> param);

    /**
     * 获取应用按钮的集合
     *
     *
     * @date 2019/11/3
     * @return
     */
    List<Map<String, Object>> getButtonByAppCode(@Param("appCode") String appCode);
}
