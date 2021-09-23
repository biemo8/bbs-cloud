package com.biemo.cloud.biz.dict.modular.mapper;

import com.biemo.cloud.biz.dict.api.entity.Dict;
import com.biemo.cloud.biz.dict.api.model.DictInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 *
 *
 * @since 2018-07-24
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictList(Page page, DictInfo dictInfo);

}
