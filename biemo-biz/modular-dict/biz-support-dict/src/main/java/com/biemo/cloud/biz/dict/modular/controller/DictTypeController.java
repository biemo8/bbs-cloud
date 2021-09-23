package com.biemo.cloud.biz.dict.modular.controller;

import com.biemo.cloud.biz.dict.api.entity.DictType;
import com.biemo.cloud.biz.dict.api.model.DictTypeInfo;
import com.biemo.cloud.biz.dict.modular.service.DictTypeService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.request.RequestData;
import com.biemo.cloud.kernel.model.response.ResponseData;
import com.biemo.cloud.kernel.scanner.modular.stereotype.ApiResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
public class DictTypeController {

    @Autowired
    private DictTypeService dictTypeService;

    /**
     * 获取字典类型列表
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @PostMapping(name = "获取字典类型列表", path = "/getDictTypeList")
    public ResponseData getDictTypeList(RequestData requestData) {
        DictTypeInfo dictTypeInfo = requestData.parse(DictTypeInfo.class);
        Page<DictTypeInfo> page = PageFactory.defaultPage();
        List<DictTypeInfo> dictTypeList = dictTypeService.getDictTypeList(page, dictTypeInfo);
        page.setRecords(dictTypeList);
        return ResponseData.success(page);
    }

    /**
     * 添加字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @PostMapping(name = "添加字典类型", path = "/addDictType")
    public ResponseData addDictType(RequestData requestData) {
        DictType dictType = requestData.parse(DictType.class);
        this.dictTypeService.addDictType(dictType);
        return ResponseData.success();
    }

    /**
     * 修改字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @PostMapping(name = "修改字典类型", path = "/updateDictType")
    public ResponseData updateDictType(RequestData requestData) {
        DictType dictType = requestData.parse(DictType.class);
        this.dictTypeService.updateDictType(dictType);
        return ResponseData.success();
    }

    /**
     * 删除字典类型
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @PostMapping(name = "删除字典类型", path = "/deleteDictType")
    public ResponseData deleteDictType(RequestData requestData) {
        Long dictTypeId = requestData.getLong("dictTypeId");
        this.dictTypeService.deleteDictType(dictTypeId);
        return ResponseData.success();
    }

    /**
     * 修改字典类型状态
     *
     *
     * @Date 2018/7/25 下午12:36
     */
    @PostMapping(name = "修改字典类型状态", path = "/updateStatus")
    public ResponseData updateStatus(RequestData requestData) {
        Long dictTypeId = requestData.getLong("dictTypeId");
        this.dictTypeService.deleteDictType(dictTypeId);
        return ResponseData.success();
    }
}
