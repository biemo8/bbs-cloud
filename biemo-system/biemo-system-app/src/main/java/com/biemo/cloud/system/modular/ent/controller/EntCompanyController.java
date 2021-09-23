package com.biemo.cloud.system.modular.ent.controller;

import com.biemo.cloud.system.modular.ent.consumer.FileConsumer;
import com.biemo.cloud.system.modular.ent.entity.EntCompApp;
import com.biemo.cloud.system.modular.ent.model.params.EntCompanyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntCompanyResult;
import com.biemo.cloud.system.modular.ent.service.EntCompanyService;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
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

import java.util.List;
import java.util.Map;

/**
 * 企业信息控制器
 *
 *
 * @Date 2019-10-10 22:44:17
 */
@RestController
@ApiResource(name = "企业信息管理", path = "/entCompany")
@Api(tags = "企业信息")
public class EntCompanyController {

    @Autowired
    private EntCompanyService entCompanyService;

    @Autowired
    private FileConsumer fileConsumer;

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "添加", path = "/add")
    @ApiOperation("新增")
    public ResponseData add(@RequestBody EntCompanyParam param) {
        entCompanyService.add(param);
        return ResponseData.success();
    }

    /**
     * 获取公司详情
     *
     *
     * @date 2019/10/12
     */
    @GetResource(name = "详情", path = "/detail")
    @ApiOperation("详情")
    public ResponseData detail(@RequestParam Long companyId) {
        return ResponseData.success(this.entCompanyService.detail(companyId));
    }

    /**
     * 修改
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "修改", path = "/update")
    @ApiOperation("修改")
    public ResponseData update(@RequestBody EntCompanyParam param) {
        entCompanyService.update(param);
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
    public ResponseData changeStatus(@RequestParam Long companyId, @RequestParam Integer status) {
        this.entCompanyService.changeStatus(companyId, StatusEnum.toEnum(status));
        return ResponseData.success();
    }

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "删除", path = "/delete")
    @ApiOperation("删除")
    public ResponseData delete(@RequestBody EntCompanyParam param) {
        entCompanyService.delete(param);
        return ResponseData.success();
    }

    /**
     * 分页查询列表
     *
     *
     * @Date 2019-10-10
     */
    @GetResource(name = "分页查询列表", path = "/queryListPage")
    @ApiOperation(value = "分页查询列表", response = EntCompanyResult.class)
    public ResponseData queryListPage(EntCompanyParam param) {
        PageResult<EntCompanyResult> pageBySpec = entCompanyService.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    /**
     * 获取公司树列表
     *
     *
     * @date 2019/10/12
     */
    @GetResource(name = "查询公司树", path = "/queryCompTree")
    @ApiOperation(value = "查询公司树")
    public ResponseData queryCompTree() {
        return ResponseData.success(this.entCompanyService.queryCompTree());
    }

    /**
     * 获取应用列表
     *
     *
     * @date 2019/10/29
     */
    @GetResource(name = "获取应用列表", path = "/getAppList", requiredPermission = false)
    @ApiOperation(value = "获取应用列表")
    public ResponseData getAppList(@RequestParam Long companyId) {
        return ResponseData.success(this.entCompanyService.getAppList(companyId));
    }

    /**
     * 保存公司配置的应用
     *
     *
     * @date 2019/10/28
     */
    @PostResource(name = "保存公司配置的应用", path = "/saveCompApp", requiredPermission = false)
    @ApiOperation(value = "保存公司配置的应用")
    public ResponseData saveCompApp(@RequestBody Map<String, List<EntCompApp>> param) {
        List<EntCompApp> params = param.get("params");
        this.entCompanyService.saveCompApp(params);
        return ResponseData.success();
    }


}
