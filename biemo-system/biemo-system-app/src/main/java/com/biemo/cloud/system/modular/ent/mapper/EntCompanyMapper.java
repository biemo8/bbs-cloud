package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntCompany;
import com.biemo.cloud.system.modular.ent.model.params.EntCompanyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntCompanyResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业信息 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntCompanyMapper extends BaseMapper<EntCompany> {

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntCompanyResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntCompanyParam paramCondition);

    /**
     * 获取公司配置的应用ids
     *
     *
     * @date 2019/10/29
     */
    List<Long> getCompAppIds(@Param("companyId") Long companyId);
}
