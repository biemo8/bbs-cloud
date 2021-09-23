package com.biemo.cloud.system.modular.dict.service;

import cn.hutool.core.lang.Dict;
import com.biemo.cloud.system.modular.dict.entity.SysDict;
import com.biemo.cloud.system.modular.dict.model.DictInfo;
import com.biemo.cloud.system.modular.dict.model.TreeDictInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基础字典 服务实现类
 * </p>
 *
 *
 * @since 2018-07-24
 */
public interface DictService extends IService<SysDict> {

    /**
     * 新增字典
     *
     *
     * @Date 2018/7/25 下午3:17
     */
    void addDict(SysDict dict);

    /**
     * 修改字典
     *
     *
     * @Date 2018/7/25 下午3:35
     */
    void updateDict(SysDict dict);

    /**
     * 删除字典
     *
     *
     * @Date 2018/7/25 下午4:53
     */
    void deleteDict(String dictId);

    /**
     * 更新字典状态
     *
     *
     * @Date 2018/7/25 下午4:53
     */
    void updateDictStatus(String dictId, Integer status);

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:18
     */
    List<DictInfo> getDictList(DictInfo dictInfo);

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:18
     */
    Page<DictInfo> getDictPage(DictInfo dictInfo);

    /**
     * 获取字典列表
     *
     *
     * @Date 2018/7/25 下午5:18
     */
    List<SysDict> getDictListByTypeCode(String dictTypeCode);

    /**
     * 获取树形字典列表
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    List<TreeDictInfo> getTreeDictList(String dictTypeCode);

    /**
     * 根据字典类型code和父id获取下级字典
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    List<DictInfo> getDictListByTypeCodeAndPid(String dictTypeCode, String parentCode);

    /**
     * code校验
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    boolean checkCode(String dictId, String dictCode);

    /**
     * 根据字典类型code和非父id获取下级字典
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    List<DictInfo> getDictListByTypeCodeAndNotPids(String dictTypeCode, List<String> parentIds);

    /**
     * 翻译Code对应的字典名称
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    String translateCode(String dictCode);

    /**
     * 根据字典类型code和字典名称获取字典码
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    String getDictCode(String dictTypeCode, String dictName);

    /**
     * 转id为名称
     *
     *
     * @Date 2018/7/25 下午5:53
     */
    String idsToNames(String ids);
}
