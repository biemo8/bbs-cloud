package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntDuty;
import com.biemo.cloud.system.modular.ent.model.params.EntDutyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDutyResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职务管理 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntDutyMapper extends BaseMapper<EntDuty> {

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntDutyResult> customPageList(@Param("page") Page page,
                                       @Param("dutyName")String dutyName,
                                       @Param("dutyCode") String dutyCode);

}
