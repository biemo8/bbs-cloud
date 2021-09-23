package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysPermissionResource;
import com.biemo.cloud.system.modular.sys.model.params.SysPermissionResourceParam;
import com.biemo.cloud.system.modular.sys.model.result.SysPermissionResourceResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限资源表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysPermissionResourceMapper extends BaseMapper<SysPermissionResource> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysPermissionResourceResult> customList(@Param("paramCondition") SysPermissionResourceParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysPermissionResourceParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysPermissionResourceResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysPermissionResourceParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysPermissionResourceParam paramCondition);

    /**
     * 获取资源列表
     *
     *
     * @Date 2019/9/26 22:56
     */
    List<Map<String, Object>> selectResourceList(Map<String, Object> param);
}
