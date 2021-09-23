package com.biemo.cloud.system.modular.dict.service;

import com.biemo.cloud.system.modular.dict.entity.SysDictType;
import com.biemo.cloud.system.modular.dict.model.DictTypeInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 *
 * @since 2018-07-24
 */
public interface DictTypeService extends IService<SysDictType> {

    /**
     * 获取字典类型列表
     *
     * @param page         分页参数
     * @param dictTypeInfo 查询条件封装
     *
     * @Date 2018/7/25 下午12:36
     */
    List<DictTypeInfo> getDictTypeList(Page page, DictTypeInfo dictTypeInfo);

    /**
     * 添加字典类型
     *
     *
     * @Date 2018/7/25 下午1:43
     */
    void addDictType(SysDictType dictType);

    /**
     * 修改字典类型
     *
     *
     * @Date 2018/7/25 下午2:28
     */
    void updateDictType(SysDictType dictType);

    /**
     * 删除字典类型
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    void deleteDictType(String dictTypeId);

    /**
     * 修改字典状态
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    void updateDictTypeStatus(String dictTypeId, Integer status);

    /**
     * code校验
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    boolean checkCode(String dictTypeCode, Long dictTypeId);
}
