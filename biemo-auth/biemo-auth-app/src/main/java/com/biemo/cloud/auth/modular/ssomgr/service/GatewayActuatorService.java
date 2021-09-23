package com.biemo.cloud.auth.modular.ssomgr.service;

import java.util.List;
import java.util.Map;

/**
 * 网关信息 服务类
 *
 *
 * @Date 2019/12/4 21:15
 */
public interface GatewayActuatorService {

    /**
     * 网关路由列表
     *
     *
     * @Date 2019/12/4 21:15
     */
    List<Map<String, Object>> routeList(String routeId);

    /**
     * 过滤器列表
     *
     *
     * @Date 2019-11-09
     */
    List<Map.Entry<String, Object>> globalfilters();

    /**
     * 所有的过滤器工厂列表
     *
     *
     * @Date 2019-11-09
     */
    Map<String, Object> routefilters();

    /**
     * 根据id查询路由信息
     *
     *
     * @Date 2019-11-09
     */
    Map<String, Object> routeInfoById(String routeId);

    /**
     * 清空路由缓存
     *
     *
     * @Date 2019-11-09
     */
    Boolean refreshRoute();

    /**
     * 新增路由
     *
     *
     * @Date 2019-11-09
     */
    Boolean addRoute(String routeId, String routeInfo);

    /**
     * 删除指定路由
     *
     *
     * @Date 2019-11-09
     */
    Boolean deleteRoute(String routeId);
}
