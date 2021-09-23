package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.model.params.EntUserAccountParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserAccountResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录账号 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntUserAccountMapper extends BaseMapper<EntUserAccount> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-10-10
     */
    List<EntUserAccountResult> customList(@Param("paramCondition") EntUserAccountParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-10-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntUserAccountParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntUserAccountResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntUserAccountParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-10-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntUserAccountParam paramCondition);

}
