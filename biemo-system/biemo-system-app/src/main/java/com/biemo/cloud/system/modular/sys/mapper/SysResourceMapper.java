package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.model.params.SysResourceParam;
import com.biemo.cloud.system.modular.sys.model.result.SysResourceResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-10
     */
    List<SysResourceResult> customList(@Param("paramCondition") SysResourceParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysResourceParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-10
     */
    Page<SysResourceResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysResourceParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysResourceParam paramCondition);

    /**
     * 获取资源列表
     *
     * @param appName      应用名称
     * @param resourceName 资源名称
     * @param menuFlag     是否是菜单(true-是菜单,false-不是菜单)
     *
     * @Date 2019-05-29 13:54
     */
    List<Map<String, Object>> getResourceList(@Param("page") Page page,
                                              @Param("appName") String appName,
                                              @Param("resourceName") String resourceName,
                                              @Param("menuFlag") String menuFlag);

    /**
     * 获取资源code
     *
     *
     * @Date 2019-09-16 16:41
     */
    List<Map<String, Object>> getResourceLeftList(@Param("set") Set<String> userResourceUrls, @Param("appCode") String appCode);
}
