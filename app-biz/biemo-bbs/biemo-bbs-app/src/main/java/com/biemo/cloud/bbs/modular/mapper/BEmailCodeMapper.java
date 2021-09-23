package com.biemo.cloud.bbs.modular.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biemo.cloud.bbs.modular.domain.BEmailCode;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
@CacheNamespace
public interface BEmailCodeMapper extends BaseMapper<BEmailCode>
{
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    BEmailCode selectBEmailCodeById(Long id);

    /**
     * 查询列表
     *
     * @param bEmailCode
     * @return 集合
     */
    List<BEmailCode> selectBEmailCodeList(BEmailCode bEmailCode);

    /**
     * 新增
     *
     * @param bEmailCode
     * @return 结果
     */
    int insertBEmailCode(BEmailCode bEmailCode);

    /**
     * 修改
     *
     * @param bEmailCode
     * @return 结果
     */
    int updateBEmailCode(BEmailCode bEmailCode);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    int deleteBEmailCodeById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBEmailCodeByIds(Long[] ids);
}
