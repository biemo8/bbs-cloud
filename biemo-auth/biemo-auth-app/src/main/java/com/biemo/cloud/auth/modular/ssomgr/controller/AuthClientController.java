package com.biemo.cloud.auth.modular.ssomgr.controller;

import com.biemo.cloud.auth.modular.sso.model.params.AuthClientParam;
import com.biemo.cloud.auth.modular.sso.service.AuthClientService;
import com.biemo.cloud.core.base.controller.BaseController;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 单点客户端控制器
 *
 *
 * @Date 2019/12/4 21:10
 */
@RestController
@Api(tags = "单点客户端管理")
@ApiResource(name = "单点客户端管理", path = "/authClient")
public class AuthClientController extends BaseController {

    @Autowired
    private AuthClientService authClientService;

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 21:10
     */
    @ApiOperation("获取应用下拉列表")
    @GetResource(name = "获取应用下拉列表", path = "/getAppSelectList")
    public ResponseData appSelectList() {
        return ResponseData.success(authClientService.getAppSelectList());
    }

    /**
     * 查询列表
     *
     *
     * @Date 2019/12/4 21:10
     */
    @ApiOperation("获取单点客户端分页列表")
    @GetResource(name = "获取单点客户端分页列表", path = "/pageList")
    public ResponseData pageList(AuthClientParam authClientParam) {
        return ResponseData.success(this.authClientService.pageList(authClientParam));
    }

    /**
     * 客户端新增
     *
     *
     * @Date 2019/12/4 21:10
     */
    @ApiOperation("客户端新增")
    @PostResource(name = "客户端新增", path = "/add")
    public ResponseData add(@RequestBody AuthClientParam authClientParam) {
        this.authClientService.add(authClientParam);
        return ResponseData.success();
    }

    /**
     * 客户端编辑
     *
     *
     * @Date 2019/12/4 21:11
     */
    @ApiOperation("客户端编辑")
    @PostResource(name = "客户端编辑", path = "/edit")
    public ResponseData edit(@RequestBody AuthClientParam authClientParam) {
        this.authClientService.update(authClientParam);
        return ResponseData.success();
    }

    /**
     * 客户端删除
     *
     *
     * @Date 2019/12/4 21:11
     */
    @ApiOperation("客户端删除")
    @PostResource(name = "客户端删除", path = "/delete")
    public ResponseData delete(RequestData requestDate) {
        Long clientId = requestDate.getLong("clientId");
        this.authClientService.delete(clientId);
        return ResponseData.success();
    }

    /**
     * 客户端详情
     *
     *
     * @Date 2019/12/4 21:11
     */
    @ApiOperation("客户端详情")
    @GetResource(name = "客户端详情", path = "/detail")
    public ResponseData detail(AuthClientParam authClientParam) {
        return ResponseData.success(this.authClientService.detail(authClientParam.getClientId()));
    }


    /**
     * 生成随机密钥
     *
     *
     * @Date 2019/12/4 21:11
     */
    @ApiOperation("生成随机密钥")
    @GetResource(name = "生成随机密钥", path = "/generateKey")
    public ResponseData generateKey() {
        return ResponseData.success(this.authClientService.generateKey());
    }


}


