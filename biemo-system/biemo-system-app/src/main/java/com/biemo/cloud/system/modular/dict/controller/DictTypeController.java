package com.biemo.cloud.system.modular.dict.controller;

import com.biemo.cloud.system.modular.dict.entity.SysDictType;
import com.biemo.cloud.system.modular.dict.model.DictTypeInfo;
import com.biemo.cloud.system.modular.dict.service.DictTypeService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.model.response.SuccessResponseData;
import com.biemo.cloud.kernel.scanner.modular.annotation.PostResource;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 字典类型管理
 *
 *
 * @Date 2018/7/25 下午12:47
 */
@RestController
@ApiResource(name = "字典类型管理", path = "/dictType")
@Api(tags = "字典类型接口")
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 获取字典类型列表
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("获取字典类型列表")
    @PostResource(name = "获取字典类型列表", path = "/getDictTypeList")
    public ResponseData getDictTypeList(@RequestBody DictTypeInfo dictTypeInfo) {
        Page<DictTypeInfo> page = PageFactory.defaultPage();
        List<DictTypeInfo> dictTypeList = dictTypeService.getDictTypeList(page, dictTypeInfo);
        page.setRecords(dictTypeList);
        return new SuccessResponseData(page);
    }

    /**
     * 添加字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("添加字典类型")
    @PostResource(name = "添加字典类型", path = "/addDictType")
    public ResponseData addDictType(@RequestBody SysDictType dictType) {
        this.dictTypeService.addDictType(dictType);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("修改字典类型")
    @PostResource(name = "修改字典类型", path = "/updateDictType")
    public ResponseData updateDictType(@RequestBody SysDictType dictType) {
        this.dictTypeService.updateDictType(dictType);
        return new SuccessResponseData();
    }

    /**
     * 删除字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("删除字典类型")
    @PostResource(name = "删除字典类型", path = "/deleteDictType")
    public ResponseData deleteDictType(@RequestParam("dictTypeId") String dictTypeId) {
        this.dictTypeService.deleteDictType(dictTypeId);
        return new SuccessResponseData();
    }

    /**
     * 修改字典类型状态
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("修改字典类型状态")
    @PostResource(name = "修改字典类型状态", path = "/updateStatus")
    public ResponseData updateStatus(@RequestParam("dictTypeId") String dictTypeId, @RequestParam("dictStatus") Integer dictStatus) {
        this.dictTypeService.updateDictTypeStatus(dictTypeId, dictStatus);
        return new SuccessResponseData();
    }

    /**
     * code校验
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @ApiOperation("code校验")
    @PostResource(name = "code校验", path = "/checkCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictTypeCode", required = true, value = "不能为空"),
            @ApiImplicitParam(name = "dictTypeId", required = true, value = "可以传空")})
    public ResponseData checkCode(@RequestBody SysDictType dictType) {
        String dictTypeCode = dictType.getDictTypeCode();
        Long dictTypeId = dictType.getDictTypeId();
        boolean flag = this.dictTypeService.checkCode(dictTypeCode, dictTypeId);
        return new SuccessResponseData(flag);
    }
}
