package com.biemo.cloud.bbs.modular.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.biemo.cloud.bbs.modular.domain.BTopicTag;
import com.biemo.cloud.bbs.modular.domain.response.BTopicResponse;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
@CacheNamespace
public interface BTopicTagMapper extends BaseMapper<BTopicTag>
{
    Page<BTopicResponse> customPageList(@Param("page")Page page, @Param("tagId")Long tagId);
}
