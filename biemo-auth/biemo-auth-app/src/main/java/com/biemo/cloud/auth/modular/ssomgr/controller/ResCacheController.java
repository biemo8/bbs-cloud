package com.biemo.cloud.auth.modular.ssomgr.controller;

import com.biemo.cloud.auth.modular.ssomgr.service.ResCacheService;
import com.biemo.cloud.core.base.controller.BaseController;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
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


/**
 * 资源缓存维护控制器
 *
 *
 * @Date 2019/12/4 21:12
 */
@RestController
@Api(tags = "资源缓存维护管理")
@ApiResource(name = "资源缓存维护管理", path = "/resCacheManager")
public class ResCacheController extends BaseController {

    @Autowired
    private ResCacheService resCacheService;

    /**
     * 获取资源缓存分页
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "获取资源缓存分页")
    @GetResource(name = "获取资源缓存分页", path = "/getResCacheList", requiredPermission = false)
    public ResponseData getResCacheList(@RequestParam String menuFlag) {
        return ResponseData.success(this.resCacheService.getResCacheList(menuFlag));
    }

    /**
     * 新增资源缓存
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "新增资源缓存")
    @PostResource(name = "新增资源缓存", path = "/addResCache", requiredPermission = false)
    public ResponseData addResCache(@RequestBody ResourceDefinition resourceDefinition) {
        this.resCacheService.addResCache(resourceDefinition);
        return ResponseData.success();
    }

    /**
     * 获取缓存资源的详情
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "获取缓存资源的详情")
    @GetResource(name = "获取缓存资源的详情", path = "/getDetail", requiredPermission = false)
    public ResponseData getDetail(@RequestParam String cacheKey) {
        return ResponseData.success(this.resCacheService.getDetail(cacheKey));
    }

    /**
     * 修改资源缓存
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "修改资源缓存")
    @PostResource(name = "修改资源缓存", path = "/editResCache", requiredPermission = false)
    public ResponseData editResCache(@RequestBody ResourceDefinition resourceDefinition) {
        this.resCacheService.editResCache(resourceDefinition);
        return ResponseData.success();
    }

    /**
     * 删除资源缓存
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "删除资源缓存")
    @GetResource(name = "删除资源缓存", path = "/removeResCache", requiredPermission = false)
    public ResponseData removeResCache(@RequestParam String cacheKey) {
        this.resCacheService.removeResCache(cacheKey);
        return ResponseData.success();
    }

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "获取应用下拉列表")
    @GetResource(name = "获取应用下拉列表", path = "/getAppSelect", requiredPermission = false)
    public ResponseData getAppSelect() {
        return ResponseData.success(this.resCacheService.getAppSelect());
    }

    /**
     * 获取资源所属模块下拉列表
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "获取资源所属模块下拉列表")
    @GetResource(name = "获取资源所属模块下拉列表", path = "/getResModuleSelect", requiredPermission = false)
    public ResponseData getResModuleSelect() {
        return ResponseData.success(this.resCacheService.getResModuleSelect());
    }

    /**
     * 获取http请求下拉列表
     *
     *
     * @Date 2019/12/4 21:13
     */
    @ApiOperation(value = "获取http请求下拉列表")
    @GetResource(name = "获取http请求下拉列表", path = "/getHttpRequestSelect", requiredPermission = false)
    public ResponseData getHttpRequestSelect() {
        return ResponseData.success(this.resCacheService.getHttpRequestSelect());
    }
}


