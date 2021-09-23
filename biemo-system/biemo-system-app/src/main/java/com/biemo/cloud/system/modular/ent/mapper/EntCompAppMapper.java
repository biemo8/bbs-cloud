package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntCompApp;
import com.biemo.cloud.system.modular.ent.model.params.EntCompAppParam;
import com.biemo.cloud.system.modular.ent.model.result.EntCompAppResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业应用配置 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntCompAppMapper extends BaseMapper<EntCompApp> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-10-10
     */
    List<EntCompAppResult> customList(@Param("paramCondition") EntCompAppParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-10-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntCompAppParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntCompAppResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntCompAppParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-10-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntCompAppParam paramCondition);

}
