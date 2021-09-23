package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysButton;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 按钮表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-11-02
 */
public interface SysButtonMapper extends BaseMapper<SysButton> {

    /**
     * 获取分页Map列表
     *
     *
     * @Date 2019-11-02
     */
    Page<Map<String, Object>> customPageList(@Param("page") Page page,
                                             @Param("menuId") Long menuId,
                                             @Param("buttonName") String buttonName,
                                             @Param("buttonCode") String buttonCode);

    /**
     * 根据应用id获取按钮资源集合
     *
     *
     * @date 2019/11/3
     */
    Set<String> getButtonResCodesByAppId(@Param("appId") Long appId);

    /**
     * 根据appCode获取所有的控制器信息
     *
     *
     * @date 2019/11/3
     */
    Set<Map<String, String>> getAllControllers(@Param("appCode") String appCode);

    /**
     * 根据模块code获取资源集合
     *
     *
     * @date 2019/11/3
     */
    List<Map<String, Object>> getResMapsByModularCode(@Param("modularCode") String modularCode);
}
