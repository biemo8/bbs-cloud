package com.biemo.cloud.auth.modular.ssomgr.service;

import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 资源缓存服务类
 *
 *
 * @Date 2019/12/4 21:15
 */
public interface ResCacheService {

    /**
     * 获取资源缓存分页列表
     *
     *
     * @Date 2019/12/4 21:15
     */
    Page<Object> getResCacheList(String menuFlag);

    /**
     * 新增资源缓存
     *
     *
     * @Date 2019/12/4 21:15
     */
    void addResCache(ResourceDefinition resourceDefinition);

    /**
     * 获取资源详情
     *
     *
     * @Date 2019/12/4 21:15
     */
    Object getDetail(String cacheKey);

    /**
     * 修改资源缓存
     *
     *
     * @Date 2019/12/4 21:15
     */
    void editResCache(ResourceDefinition resourceDefinition);

    /**
     * 删除资源缓存
     *
     *
     * @Date 2019/12/4 21:15
     */
    void removeResCache(String cacheKey);

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 21:15
     */
    List<Map<String, Object>> getAppSelect();

    /**
     * 获取资源所属模块下拉列表
     *
     *
     * @Date 2019/12/4 21:15
     */
    List<Map<String, Object>> getResModuleSelect();

    /**
     * 获取http请求下拉列表
     *
     *
     * @Date 2019/12/4 21:16
     */
    List<String> getHttpRequestSelect();
}
