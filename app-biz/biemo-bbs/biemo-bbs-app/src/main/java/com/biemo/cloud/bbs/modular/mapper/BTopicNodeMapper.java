package com.biemo.cloud.bbs.modular.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.biemo.cloud.bbs.modular.domain.BTopic;
import com.biemo.cloud.bbs.modular.domain.BTopicNode;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.kafka.common.KafkaFuture;

/**
 * Mapper接口
 *
 * @author makesoft
 * @date 2021-08-11
 */
@CacheNamespace
public interface BTopicNodeMapper extends BaseMapper<BTopicNode>
{

}
