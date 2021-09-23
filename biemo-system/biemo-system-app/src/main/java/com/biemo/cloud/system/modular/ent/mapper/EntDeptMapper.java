package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntDept;
import com.biemo.cloud.system.modular.ent.model.params.EntDeptParam;
import com.biemo.cloud.system.modular.ent.model.result.EntDeptResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntDeptMapper extends BaseMapper<EntDept> {

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntDeptResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntDeptParam paramCondition);

    /**
     * 获取部门树列表
     *
     *
     * @date 2019/10/13
     */
    List<Map<String, Object>> queryDeptTree(@Param("companyId") Long companyId,
                                            @Param("nodeType") Integer nodeType);
}
