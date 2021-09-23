package com.biemo.cloud.bbs.modular.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biemo.cloud.bbs.modular.domain.BTopicTag;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
@CacheNamespace
public interface BTopicTagMapper extends BaseMapper<BTopicTag>
{
}
