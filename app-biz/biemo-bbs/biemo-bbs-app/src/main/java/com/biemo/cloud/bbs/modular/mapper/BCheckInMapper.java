package com.biemo.cloud.bbs.modular.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biemo.cloud.bbs.modular.domain.BCheckIn;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
public interface BCheckInMapper extends BaseMapper<BCheckIn>
{
    /**
     * 查询
     *
     * @param id ID
     * @return
     */
    BCheckIn selectBCheckInById(Long id);

    /**
     * 查询列表
     *
     * @param bCheckIn
     * @return 集合
     */
    List<BCheckIn> selectBCheckInList(BCheckIn bCheckIn);

    /**
     * 新增
     *
     * @param bCheckIn
     * @return 结果
     */
    int insertBCheckIn(BCheckIn bCheckIn);

    /**
     * 修改
     *
     * @param bCheckIn
     * @return 结果
     */
    int updateBCheckIn(BCheckIn bCheckIn);

    /**
     * 删除
     *
     * @param id ID
     * @return 结果
     */
    int deleteBCheckInById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBCheckInByIds(Long[] ids);
}
