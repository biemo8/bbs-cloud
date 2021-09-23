package com.biemo.cloud.system.modular.ent.controller;

import com.biemo.cloud.system.modular.ent.entity.EntDept;
import com.biemo.cloud.system.modular.ent.model.params.EntDeptParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDeptResult;
import com.biemo.cloud.system.modular.ent.service.EntDeptService;
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

/**
 * 部门表控制器
 *
 *
 * @Date 2019-10-10 22:44:17
 */
@RestController
@ApiResource(name = "部门表管理", path = "/entDept")
@Api(tags = "部门表")
public class EntDeptController {


    @Autowired
    private EntDeptService entDeptService;

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "添加", path = "/add")
    @ApiOperation("新增")
    public ResponseData add(@RequestBody EntDeptParam param) {
        entDeptService.add(param);
        return ResponseData.success();
    }

    /**
     * 获取部门详情
     *
     *
     * @date 2019/10/12
     */
    @GetResource(name = "详情",path = "/detail")
    @ApiOperation("详情")
    public ResponseData detail(@RequestParam Long deptId){
        return ResponseData.success(this.entDeptService.detail(deptId));
    }

    /**
     * 修改
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "修改", path = "/update")
    @ApiOperation("修改")
    public ResponseData update(@RequestBody EntDeptParam param) {
        entDeptService.update(param);
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
    public ResponseData delete(@RequestBody EntDeptParam param) {
        entDeptService.delete(param);
        return ResponseData.success();
    }

    /**
     * 禁用启用
     *
     *
     * @date 2019/10/12
     */
    @GetResource(name = "禁用启用",path = "/changeStatus")
    @ApiOperation("禁用启用")
    public ResponseData changeStatus(@RequestParam Long deptId,@RequestParam Integer status){
        this.entDeptService.changeStatus(deptId, StatusEnum.toEnum(status));
        return ResponseData.success();
    }

    /**
     * 分页查询列表
     *
     *
     * @Date 2019-10-10
     */
    @GetResource(name = "分页查询列表", path = "/queryListPage")
    @ApiOperation(value = "分页查询列表", response = EntDept.class)
    public ResponseData queryListPage(EntDeptParam param) {
        PageResult<EntDeptResult> pageBySpec = entDeptService.findPageBySpec(param);
        return ResponseData.success(pageBySpec);
    }

    /**
     * 获取部门树列表
     *
     *
     * @date 2019/10/13
     */
    @GetResource(name = "获取部门树列表", path = "/queryDeptTree")
    @ApiOperation(value = "获取部门树列表")
    public ResponseData queryDeptTree(@RequestParam Long companyId){
        return ResponseData.success(this.entDeptService.queryDeptTree(companyId));
    }

}
