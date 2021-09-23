package com.biemo.cloud.bbs.modular.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biemo.cloud.bbs.modular.domain.BSysConfig;
import org.apache.ibatis.annotations.CacheNamespace;
/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
@CacheNamespace
public interface BSysConfigMapper extends BaseMapper<BSysConfig>
{
}
