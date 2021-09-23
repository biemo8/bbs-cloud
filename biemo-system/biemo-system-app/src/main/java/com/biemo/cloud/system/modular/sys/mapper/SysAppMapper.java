package com.biemo.cloud.system.modular.sys.mapper;

import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.model.params.SysAppParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用管理 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysAppMapper extends BaseMapper<SysApp> {

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("appName") String appName);

    /**
     * 根据当前登录人获取应用下拉列表
     *
     *
     * @date 2019/11/26
     */
    List<SysAppParam> getAppSelectByCurrentUser(@Param("accountId") Long accountId);
}
