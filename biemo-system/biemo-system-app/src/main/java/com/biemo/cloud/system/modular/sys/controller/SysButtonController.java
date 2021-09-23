package com.biemo.cloud.system.modular.sys.controller;

import com.biemo.cloud.system.modular.sys.model.params.SysButtonParam;
import com.biemo.cloud.system.modular.sys.model.result.SysButtonResult;
import com.biemo.cloud.system.modular.sys.service.SysButtonService;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.biemo.cloud.kernel.validator.stereotype.ParamValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 按钮表控制器
 *
 *
 * @Date 2019-11-02 20:59:14
 */
@RestController
@ApiResource(name = "按钮接口", path = "/sysButton")
@Api(tags = "按钮接口")
public class SysButtonController {

    @Autowired
    private SysButtonService sysButtonService;

    /**
     * 新增
     *
     *
     * @Date 2019-11-02
     */
    @PostResource(name = "添加", path = "/add")
    @ApiOperation("新增")
    @ParamValidator
    public ResponseData add(@RequestBody SysButtonParam param) {
        sysButtonService.add(param);
        return ResponseData.success();
    }

    /**
     * 获取按钮详情
     *
     *
     * @date 2019/11/2
     */
    @GetResource(name = "获取按钮详情", path = "/getDetail")
    @ApiOperation("获取按钮详情")
    public ResponseData getDetail(@RequestParam Long buttonId) {
        return ResponseData.success(sysButtonService.getDetail(buttonId));
    }

    /**
     * 修改
     *
     *
     * @Date 2019-11-02
     */
    @PostResource(name = "修改", path = "/update")
    @ApiOperation("修改")
    public ResponseData update(@RequestBody SysButtonParam param) {
        sysButtonService.update(param);
        return ResponseData.success();
    }

    /**
     * 禁用启用
     *
     *
     * @date 2019/10/12
     */
    @GetResource(name = "禁用启用", path = "/changeStatus")
    @ApiOperation("禁用启用")
    public ResponseData changeStatus(@RequestParam Long buttonId, @RequestParam Integer status) {
        this.sysButtonService.changeStatus(buttonId, StatusEnum.toEnum(status));
        return ResponseData.success();
    }

    /**
     * 删除
     *
     *
     * @Date 2019-11-02
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation("删除")
    public ResponseData delete(@RequestBody Map<String,Long[]> param) {
        Long[] buttonIds = param.get("buttonIds");
        sysButtonService.delete(buttonIds);
        return ResponseData.success();
    }

    /**
     * 分页查询列表
     *
     *
     * @Date 2019-11-02
     */
    @GetResource(name = "分页查询列表", path = "/queryListPage")
    @ApiOperation(value = "分页查询列表", response = SysButtonResult.class)
    public ResponseData queryListPage(@RequestParam Long menuId,
                                      @RequestParam(required = false) String buttonName,
                                      @RequestParam(required = false) String buttonCode) {
        return ResponseData.success(sysButtonService.findPageBySpec(menuId,buttonName,buttonCode));
    }

    /**
     * 获取资源级联列表
     *
     *
     * @date 2019/11/3
     */
    @GetResource(name = "获取资源级联列表", path = "/getResources",requiredPermission = false)
    @ApiOperation(value = "获取资源级联列表")
    public ResponseData getResources(@RequestParam Long appId) {
        return ResponseData.success(sysButtonService.getResources(appId));
    }



}
