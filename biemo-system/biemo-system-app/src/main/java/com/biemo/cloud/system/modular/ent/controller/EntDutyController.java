package com.biemo.cloud.system.modular.ent.controller;

import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.scanner.modular.annotation.GetResource;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.system.modular.ent.model.params.EntDutyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDutyResult;
import com.biemo.cloud.system.modular.ent.service.EntDutyService;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 职务管理控制器
 *
 *
 * @Date 2019-10-10 22:44:17
 */
@RestController
@ApiResource(name = "职务管理管理", path = "/entDuty")
@Api(tags = "职务管理")
public class EntDutyController {


    @Autowired
    private EntDutyService entDutyService;

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "添加", path = "/add")
    @ApiOperation("新增")
    public ResponseData add(@RequestBody EntDutyParam param) {
        entDutyService.add(param);
        return ResponseData.success();
    }

    /**
     * 获取职务详情
     *
     *
     * @date 2019/10/13
     */
    @GetResource(name = "详情", path = "/detail")
    @ApiOperation("详情")
    public ResponseData detail(@RequestParam Long dutyId) {
        return ResponseData.success(entDutyService.getById(dutyId));
    }

    /**
     * 修改
     *
     *
     * @Date 2019-10-10
     */
    @PostResource(name = "修改", path = "/update")
    @ApiOperation("修改")
    public ResponseData update(@RequestBody EntDutyParam param) {
        entDutyService.update(param);
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
    public ResponseData delete(@RequestBody EntDutyParam param) {
        entDutyService.delete(param);
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
    public ResponseData changeStatus(@RequestParam Long dutyId,@RequestParam Integer status){
        this.entDutyService.changeStatus(dutyId, StatusEnum.toEnum(status));
        return ResponseData.success();
    }

    /**
     * 分页查询列表
     *
     *
     * @Date 2019-10-10
     */
    @GetResource(name = "分页查询列表", path = "/queryListPage")
    @ApiOperation(value = "分页查询列表", response = EntDutyResult.class)
    public ResponseData queryListPage(@RequestParam String dutyName,
                                      @RequestParam String dutyCode) {
        PageResult<EntDutyResult> pageBySpec = entDutyService.findPageBySpec(dutyName,dutyCode);
        return ResponseData.success(pageBySpec);
    }

    /**
     * 获取职务列表
     *
     *
     * @date 2019/10/14
     */
    @GetResource(name = "职务不分页列表", path = "/queryList")
    @ApiOperation(value = "职务不分页列表")
    public ResponseData queryList(){
        return ResponseData.success(this.entDutyService.queryList());
    }

}
