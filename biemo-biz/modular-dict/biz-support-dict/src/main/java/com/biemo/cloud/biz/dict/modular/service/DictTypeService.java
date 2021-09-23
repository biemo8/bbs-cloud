package com.biemo.cloud.biz.dict.modular.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.biemo.cloud.biz.dict.api.entity.DictType;
import com.biemo.cloud.biz.dict.api.model.DictTypeInfo;
import com.biemo.cloud.biz.dict.core.enums.DictTypeEnum;
import com.biemo.cloud.biz.dict.modular.mapper.DictTypeMapper;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.biemo.cloud.biz.dict.core.exception.DictExceptionEnum.*;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 *
 * @since 2018-07-24
 */
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

    /**
     * 获取字典类型列表
     *
     * @param page         分页参数
     * @param dictTypeInfo 查询条件封装
     *
     * @Date 2018/7/25 下午12:36
     */
    public List<DictTypeInfo> getDictTypeList(Page page, DictTypeInfo dictTypeInfo) {
        if (page == null) {
            page = PageFactory.defaultPage();
        }
        if (dictTypeInfo == null) {
            dictTypeInfo = new DictTypeInfo();
        }
        return this.baseMapper.getDictTypeList(page, dictTypeInfo);
    }

    /**
     * 添加字典类型
     *
     *
     * @Date 2018/7/25 下午1:43
     */
    public void addDictType(DictType dictType) {
        if (ToolUtil.isOneEmpty(dictType, dictType.getDictTypeCode(), dictType.getDictTypeName())) {
            throw new RequestEmptyException();
        }

        //判断有没有相同编码的字典
        QueryWrapper<DictType> wrapper = new QueryWrapper<DictType>()
                .eq("DICT_TYPE_CODE", dictType.getDictTypeCode());
        int dictTypes = this.count(wrapper);
        if (dictTypes > 0) {
            throw new ServiceException(REPEAT_DICT_TYPE);
        }

        dictType.setStatus(StatusEnum.ENABLE.getCode());
        dictType.setDictTypeClass(DictTypeEnum.BUSINESS.getCode());

        this.save(dictType);
    }

    /**
     * 修改字典类型
     *
     *
     * @Date 2018/7/25 下午2:28
     */
    public void updateDictType(DictType dictType) {
        if (ToolUtil.isOneEmpty(dictType, dictType.getDictTypeCode(), dictType.getDictTypeName())) {
            throw new RequestEmptyException();
        }

        DictType oldDictType = this.getById(dictType.getDictTypeId());
        if (oldDictType == null) {
            throw new ServiceException(NOT_EXISTED);
        }

        //查询有没有编码重复的字典
        QueryWrapper<DictType> wrapper = new QueryWrapper<DictType>()
                .eq("DICT_TYPE_CODE", dictType.getDictTypeCode())
                .and(i -> i.ne("DICT_TYPE_ID", oldDictType.getDictTypeId()));
        int dictTypes = this.count(wrapper);
        if (dictTypes > 0) {
            throw new ServiceException(REPEAT_DICT_TYPE);
        }

        BeanUtil.copyProperties(dictType, oldDictType, CopyOptions.create().setIgnoreNullValue(true));
        this.updateById(oldDictType);
    }

    /**
     * 删除字典类型
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    public void deleteDictType(Long dictTypeId) {
        if (ToolUtil.isEmpty(dictTypeId)) {
            throw new RequestEmptyException();
        }

        //判断字典是否存在
        DictType dictType = this.getById(dictTypeId);
        if (dictType == null) {
            throw new ServiceException(NOT_EXISTED);
        }

        this.removeById(dictTypeId);
    }

    /**
     * 修改字典状态
     *
     *
     * @Date 2018/7/25 下午2:43
     */
    public void updateDictTypeStatus(Long dictTypeId, Integer status) {
        if (ToolUtil.isOneEmpty(dictTypeId, status)) {
            throw new RequestEmptyException();
        }

        //判断字典是否存在
        DictType dictType = this.getById(dictTypeId);
        if (dictType == null) {
            throw new ServiceException(NOT_EXISTED);
        }

        //判断状态是否正确
        StatusEnum statusEnum = StatusEnum.toEnum(status);
        if (statusEnum == null) {
            throw new ServiceException(WRONG_STATUS);
        }

        dictType.setStatus(status);

        this.updateById(dictType);
    }
}
