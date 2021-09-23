package com.biemo.cloud.system.modular.dict.mapper;

import com.biemo.cloud.system.modular.dict.entity.SysDict;
import com.biemo.cloud.system.modular.dict.model.DictInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础字典 Mapper 接口
 * </p>
 *
 *
 * @since 2018-07-24
 */
public interface DictMapper extends BaseMapper<SysDict> {

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictList(Page page, @Param("dictInfo") DictInfo dictInfo);

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictList(@Param("dictInfo") DictInfo dictInfo);

    /**
     * 根据字典类型code和父id获取下级字典
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictListByTypeCodeAndPid(Map<String, Object> map);

    /**
     * 根据字典类型code和非父id获取下级字典
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<DictInfo> getDictListByTypeCodeAndNotPid(Map<String, Object> map);

    /**
     * 翻译Code对应的字典名称
     *
     *
     * @Date 2018/7/25 下午5:21
     */
    List<String> getDictName(String dictCode);

    /**
     * 根据字典类型code和字典名称获取字典码
     *
     * @param dictTypeCode 字典类型code
     * @param dictName     字典名称
     *
     * @Date 2018/7/25 下午5:21
     */
    List<String> getDictCode(@Param("dictTypeCode") String dictTypeCode, @Param("dictName") String dictName);
}
