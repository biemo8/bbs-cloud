package com.biemo.cloud.auth.modular.ssomgr.controller;

import com.biemo.cloud.auth.modular.ssomgr.service.GatewayActuatorService;
import com.biemo.cloud.core.base.controller.BaseController;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 网关信息控制器
 *
 *
 * @Date 2019/12/4 21:12
 */
@RestController
@Api(tags = "网关信息控制器")
@ApiResource(name = "网关信息控制器", path = "/gateway")
public class GatewayActuatorController extends BaseController {

    @Autowired
    private GatewayActuatorService gatewayActuatorService;

    /**
     * 网关路由列表
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("网关路由列表")
    @GetResource(name = "网关路由列表", path = "/routes", menuFlag = true)
    public ResponseData routeList(@RequestParam(value = "routeId", required = false) String routeId) {
        return ResponseData.success(gatewayActuatorService.routeList(routeId));
    }

    /**
     * 过滤器列表
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("过滤器列表")
    @GetResource(name = "过滤器列表", path = "/globalfilters")
    public ResponseData globalfilters() {
        return ResponseData.success(this.gatewayActuatorService.globalfilters());
    }

    /**
     * 所有的过滤器工厂列表
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("所有的过滤器工厂")
    @GetResource(name = "所有的过滤器工厂列表", path = "/routefilters")
    public ResponseData routefilters() {
        return ResponseData.success(this.gatewayActuatorService.routefilters());
    }

    /**
     * 根据id查询路由信息
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("根据id查询路由信息")
    @GetResource(name = "根据id查询路由信息", path = "/routeInfoById")
    public ResponseData routeInfoById(@RequestParam("routeId") String routeId) {
        if (ToolUtil.isEmpty(routeId)) {
            throw new ServiceException(400, "routeId不能为空");
        }
        return ResponseData.success(this.gatewayActuatorService.routeInfoById(routeId));
    }

    /**
     * 清空路由缓存
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("清空路由缓存")
    @PostResource(name = "清空路由缓存", path = "/refreshRoute")
    public ResponseData refreshRoute() {
        return ResponseData.success(this.gatewayActuatorService.refreshRoute());
    }

    /**
     * 新增路由
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("新增路由")
    @PostResource(name = "新增路由", path = "/addRoute")
    public ResponseData addRoute(@RequestBody Map<String, String> map) {
        if (ToolUtil.isEmpty(map.get("routeId")) || ToolUtil.isEmpty(map.get("routeInfo"))) {
            throw new ServiceException(400, "routeId或routeInfo不能为空");
        }
        return ResponseData.success(this.gatewayActuatorService.addRoute(map.get("routeId"), map.get("routeInfo")));
    }

    /**
     * 删除指定路由
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation("删除指定路由")
    @GetResource(name = "删除指定路由", path = "/deleteRoute")
    public ResponseData deleteRoute(@RequestParam("routeId") String routeId) {
        if (ToolUtil.isEmpty(routeId)) {
            throw new ServiceException(400, "route不能为空");
        }
        return ResponseData.success(this.gatewayActuatorService.deleteRoute(routeId));
    }

}


