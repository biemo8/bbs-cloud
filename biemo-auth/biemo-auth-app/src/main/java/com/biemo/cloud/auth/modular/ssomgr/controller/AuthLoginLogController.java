package com.biemo.cloud.auth.modular.ssomgr.controller;

import com.biemo.cloud.auth.modular.sso.model.result.AuthLoginLogResult;
import com.biemo.cloud.auth.modular.sso.service.LoginLogService;
import com.biemo.cloud.core.base.controller.BaseController;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录日志表控制器
 *
 *
 * @Date 2019/12/4 21:11
 */
@RestController
@Api(tags = "登录日志管理")
@ApiResource(name = "登录日志管理", path = "/authLoginLog")
public class AuthLoginLogController extends BaseController {

    @Autowired
    private LoginLogService authLoginLogService;

    /**
     * 查看日志列表（分页）
     *
     *
     * @Date 2019/12/4 21:11
     */
    @ApiOperation("查看日志列表（分页）")
    @GetResource(name = "查看日志列表（分页）", path = "/pageList")
    public ResponseData pageList(@RequestParam(value = "account", required = false) String account) {
        Page pageList = authLoginLogService.pageList(account);
        return ResponseData.success(pageList);
    }

    /**
     * 查看日志详情
     *
     *
     * @Date 2019/12/4 21:12
     */
    @ApiOperation(value = "日志详情", response = AuthLoginLogResult.class)
    @GetResource(name = "日志详情", path = "/detail")
    public ResponseData detail(@RequestParam("loginLogId") Long loginLogId) {
        AuthLoginLogResult detail = this.authLoginLogService.detail(loginLogId);
        return ResponseData.success(detail);
    }

}


