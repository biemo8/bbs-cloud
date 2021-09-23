package com.biemo.cloud.system.modular.dict.mapper;

import com.biemo.cloud.system.modular.dict.entity.SysDictType;
import com.biemo.cloud.system.modular.dict.model.DictTypeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 *
 * @since 2018-07-24
 */
public interface DictTypeMapper extends BaseMapper<SysDictType> {

    /**
     * 获取字典类型列表
     *
     *
     * @Date 2018/7/25 上午11:24
     */
    List<DictTypeInfo> getDictTypeList(Page page, @Param("dictTypeInfo") DictTypeInfo dictTypeInfo);

}
