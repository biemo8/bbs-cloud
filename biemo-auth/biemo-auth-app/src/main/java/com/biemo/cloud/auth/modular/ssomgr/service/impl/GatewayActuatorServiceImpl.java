package com.biemo.cloud.auth.modular.ssomgr.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import com.biemo.cloud.auth.modular.ssomgr.service.GatewayActuatorService;
import com.biemo.cloud.auth.modular.ssomgr.util.GatewayActuatorUtil;
import com.biemo.cloud.core.util.HttpContext;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * 网关信息 服务实现类
 *
 *
 * @Date 2019/12/4 21:14
 */
@Service
public class GatewayActuatorServiceImpl implements GatewayActuatorService {

    @Value("${gatewayInfo.path.routes}")
    private String routes;

    @Value("${gatewayInfo.path.globalfilters}")
    private String globalfilters;

    @Value("${gatewayInfo.path.routefilters}")
    private String routefilters;

    @Value("${gatewayInfo.path.routeInfoById}")
    private String routeInfoById;

    @Value("${gatewayInfo.path.refresh}")
    private String refresh;

    @Value("${gatewayInfo.path.addRoute}")
    private String addRoute;

    @Value("${gatewayInfo.path.deleteRoute}")
    private String deleteRoute;

    @Override
    public List<Map<String, Object>> routeList(String routeId) {

        //获取路由信息
        String result = getGatewayInfoResult(routes, "application/x-www-form-urlencoded");

        //解析路由信息
        List<Map<String, Object>> parse = (List<Map<String, Object>>) JSONArray.parse(result);

        //处理结果
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : parse) {
            Map<String, Object> objectMap = new HashMap<>();
            objectMap.put("routeId", map.get("route_id"));
            objectMap.put("order", map.get("order"));
            resultList.add(objectMap);
        }

        //返回指定路由信息
        if (ToolUtil.isNotEmpty(routeId) && ToolUtil.isNotEmpty(resultList)) {
            List<Map<String, Object>> resultList2 = new ArrayList<>();
            for (Map<String, Object> map : resultList) {
                if (map.get("routeId").equals(routeId)) {
                    resultList2.add(map);
                    return resultList2;
                }
            }
            return resultList2;
        }
        return resultList;
    }

    @Override
    public List<Map.Entry<String, Object>> globalfilters() {
        return sortByValue(JSON.parseObject(getGatewayInfoResult(globalfilters, "application/x-www-form-urlencoded")));
    }

    public List<Map.Entry<String, Object>> sortByValue(Map<String, Object> paramMap) {

        //转换
        ArrayList<Map.Entry<String, Object>> arrayList = new ArrayList<Map.Entry<String, Object>>(paramMap.entrySet());

        //用于排序
        ArrayList<Map.Entry<String, Object>> arrayList1 = new ArrayList<>();//先放负数(包括0)
        ArrayList<Map.Entry<String, Object>> arrayList2 = new ArrayList<>();//放正数数
        for (Map.Entry<String, Object> stringObjectEntry : arrayList) {
            if ((Integer) stringObjectEntry.getValue() <= 0) {
                arrayList1.add(stringObjectEntry);
            } else {
                arrayList2.add(stringObjectEntry);
            }
        }

        //排序
        Collections.sort(arrayList1, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> map1, Map.Entry<String, Object> map2) {
                return ((Integer) map1.getValue() - (Integer) map2.getValue());
            }
        });
        Collections.sort(arrayList2, new Comparator<Map.Entry<String, Object>>() {
            public int compare(Map.Entry<String, Object> map1, Map.Entry<String, Object> map2) {
                return ((Integer) map1.getValue() - (Integer) map2.getValue());
            }
        });
        arrayList1.addAll(arrayList2);
        return arrayList1;
    }

    @Override

    public Map<String, Object> routefilters() {
        return JSON.parseObject(getGatewayInfoResult(routefilters, "application/x-www-form-urlencoded"));
    }

    @Override
    public Map<String, Object> routeInfoById(String routeId) {
        return JSON.parseObject(getGatewayInfoResult(routeInfoById + routeId, "application/json"));
    }

    @Override
    public Boolean refreshRoute() {
        return getResult(HttpRequest.post(refresh)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", getToken())
                .execute().body());
    }

    @Override
    public Boolean addRoute(String routeId, String routeInfo) {

        //校验参数
        checkRouteInfo(routeInfo);

        return getResult(HttpRequest.post(addRoute + routeId)
                .header("Content-Type", "application/json")
                .header("Authorization", getToken())
                .body(routeInfo)
                .execute().body());
    }

    /**
     * 校验路由信息
     */
    private void checkRouteInfo(String routeInfo) {

        //类型转换
        JSONObject jsonObject = null;
        try {
            jsonObject = JSON.parseObject(routeInfo);
        } catch (Exception e) {
            throw new ServiceException(400, "json格式错误");
        }

        //检验必填的key值
        checkKeys(jsonObject);

        //校验predicates
        List<Map<String, Object>> predicates = (List<Map<String, Object>>) jsonObject.get("predicates");
        for (Map<String, Object> predicate : predicates) {
            if (!(predicate.containsKey("name") && GatewayActuatorUtil.getRoutePredicates().contains(predicate.get("name")))) {
                throw new ServiceException(400, "predicates对应的属性值中的name:" + predicate.get("name") + "不符合规范");
            }
        }

        //校验filters
        if (ToolUtil.isNotEmpty(jsonObject.get("filters"))) {
            List<Map<String, Object>> filters = (List<Map<String, Object>>) jsonObject.get("filters");
            for (Map<String, Object> filter : filters) {
                if (!(filter.containsKey("name") && GatewayActuatorUtil.getGatewayFilterFactories().contains(filter.get("name")))) {
                    throw new ServiceException(400, "filters对应的属性值中的name:" + filter.get("name") + "不符合规范");
                }
            }
        }

        //校验uri
        String uri = (String) jsonObject.get("uri");
        if (uri.startsWith("lb://") && !this.routeIdList().contains(uri.substring(5))) {
            throw new ServiceException(400, "uri格式不规范");
        }

        //校验order
        try {
            Number order = (Number) jsonObject.get("order");
        } catch (ClassCastException e) {
            throw new ServiceException(400, "order必须为数字类型");
        }
    }

    /**
     * 路由id集合
     */
    private List<String> routeIdList() {
        List<String> stringList = new ArrayList<>();
        List<Map<String, Object>> mapList = this.routeList(null);
        if (ToolUtil.isNotEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                stringList.add((String) map.get("routeId"));
            }
        }
        return stringList;
    }

    /**
     * 校验路由信息必填属性
     */
    private void checkKeys(JSONObject jsonObject) {

        if (!(jsonObject.containsKey("predicates") && ToolUtil.isNotEmpty(jsonObject.get("predicates")))) {
            throw new ServiceException(400, "predicates属性不能为空");
        }
        if (!jsonObject.containsKey("filters")) {
            throw new ServiceException(400, "filters属性不能为空");
        }
        if (!(jsonObject.containsKey("uri") && ToolUtil.isNotEmpty(jsonObject.get("uri")))) {
            throw new ServiceException(400, "uri属性不能为空");
        }
        if (!(jsonObject.containsKey("order") && ToolUtil.isNotEmpty(jsonObject.get("order")))) {
            throw new ServiceException(400, "order属性不能为空");
        }
    }

    @Override
    public Boolean deleteRoute(String routeId) {
        return getResult(HttpRequest.delete(deleteRoute + routeId)
                .header("Content-Type", "application/json")
                .method(Method.DELETE)
                .header("Authorization", getToken())
                .execute().body());
    }

    /**
     * 获取路由相关信息
     */
    private String getGatewayInfoResult(String routes, String contentType) {
        return HttpRequest.get(routes)
                .header("Content-Type", contentType)
                .header("Authorization", getToken())
                .execute().body();
    }

    /**
     * 获取token
     */
    private String getToken() {
        return HttpContext.getRequest().getHeader("Authorization");
    }

    /**
     * 处理结果
     */
    private Boolean getResult(String result) {
        JSONObject resultMap = JSON.parseObject(result);
        if (ToolUtil.isNotEmpty(resultMap)) {
            if ("true".equals(resultMap.getString("success"))) {
                return true;
            } else {
                throw new ServiceException(resultMap.getInteger("code"), resultMap.getString("message"));
            }
        }
        return true;
    }
}
