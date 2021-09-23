package com.biemo.cloud.system.modular.dict.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.biemo.cloud.system.modular.dict.entity.SysDictType;
import com.biemo.cloud.system.modular.dict.enums.DictExceptionEnum;
import com.biemo.cloud.system.modular.dict.mapper.DictTypeMapper;
import com.biemo.cloud.system.modular.dict.model.DictTypeInfo;
import com.biemo.cloud.system.modular.dict.service.DictTypeService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 *
 * @since 2018-07-24
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, SysDictType> implements DictTypeService {

    @Override
    public List<DictTypeInfo> getDictTypeList(Page page, DictTypeInfo dictTypeInfo) {
        if (page == null) {
            page = PageFactory.defaultPage();
        }
        if (dictTypeInfo == null) {
            dictTypeInfo = new DictTypeInfo();
        }
        return this.baseMapper.getDictTypeList(page, dictTypeInfo);
    }

    @Override
    public void addDictType(SysDictType dictType) {
        if (ToolUtil.isOneEmpty(dictType, dictType.getDictTypeCode(), dictType.getDictTypeName())) {
            throw new RequestEmptyException();
        }

        //判断有没有相同编码的字典
        Wrapper<SysDictType> wrapper = new QueryWrapper<SysDictType>()
                .eq("DICT_TYPE_CODE", dictType.getDictTypeCode());
        int dictTypes = this.baseMapper.selectCount(wrapper);
        if (dictTypes > 0) {
            throw new ServiceException(DictExceptionEnum.REPEAT_DICT_TYPE);
        }
        dictType.setDelFlag("N");
        dictType.setStatus(StatusEnum.ENABLE.getCode());
        dictType.setDictTypeId(IdWorker.getId());
        this.baseMapper.insert(dictType);
    }

    @Override
    public void updateDictType(SysDictType dictType) {
        if (ToolUtil.isOneEmpty(dictType, dictType.getDictTypeCode(), dictType.getDictTypeName())) {
            throw new RequestEmptyException();
        }

        SysDictType oldDictType = this.baseMapper.selectById(dictType.getDictTypeId());
        if (oldDictType == null) {
            throw new ServiceException(DictExceptionEnum.NOT_EXISTED);
        }

        //查询有没有编码重复的字典
        Wrapper<SysDictType> wrapper = new QueryWrapper<SysDictType>()
                .eq("DICT_TYPE_CODE", dictType.getDictTypeCode())
                .ne("DICT_TYPE_ID", oldDictType.getDictTypeId());
        int dictTypes = this.baseMapper.selectCount(wrapper);
        if (dictTypes > 0) {
            throw new ServiceException(DictExceptionEnum.REPEAT_DICT_TYPE);
        }

        BeanUtil.copyProperties(dictType, oldDictType, CopyOptions.create().setIgnoreNullValue(true));
        this.updateById(oldDictType);
    }

    @Override
    public void deleteDictType(String dictTypeId) {
        if (ToolUtil.isEmpty(dictTypeId)) {
            throw new RequestEmptyException();
        }

        //判断字典是否存在
        SysDictType dictType = this.baseMapper.selectById(dictTypeId);
        if (dictType == null) {
            throw new ServiceException(DictExceptionEnum.NOT_EXISTED);
        }

        this.baseMapper.deleteById(dictTypeId);
    }

    @Override
    public void updateDictTypeStatus(String dictTypeId, Integer status) {
        if (ToolUtil.isOneEmpty(dictTypeId, status)) {
            throw new RequestEmptyException();
        }

        //判断字典是否存在
        SysDictType dictType = this.baseMapper.selectById(dictTypeId);
        if (dictType == null) {
            throw new ServiceException(DictExceptionEnum.NOT_EXISTED);
        }

        //判断状态是否正确
        StatusEnum statusEnum = StatusEnum.toEnum(status);
        if (statusEnum == null) {
            throw new ServiceException(DictExceptionEnum.WRONG_STATUS);
        }

        dictType.setStatus(status);

        this.updateById(dictType);
    }

    @Override
    public boolean checkCode(String dictTypeCode, Long dictTypeId) {
        QueryWrapper<SysDictType> wrapper = new QueryWrapper<>();
        wrapper.eq("DICT_TYPE_CODE", dictTypeCode);
        if (dictTypeId != null) {
            wrapper.ne("DICT_TYPE_ID", dictTypeId);
        }
        Integer selectCount = this.baseMapper.selectCount(wrapper);
        if (selectCount <= 0) {
            return true;
        }
        return false;
    }
}
