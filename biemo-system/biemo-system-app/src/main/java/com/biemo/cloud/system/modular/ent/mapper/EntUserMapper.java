package com.biemo.cloud.system.modular.ent.mapper;

import com.biemo.cloud.system.modular.ent.entity.EntUser;
import com.biemo.cloud.system.modular.ent.model.params.EntUserParam;
import com.biemo.cloud.system.modular.ent.model.params.EntUserQueryParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserQueryResult;
import com.biemo.cloud.system.modular.ent.model.result.EntUserResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 用户信息 Mapper 接口
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntUserMapper extends BaseMapper<EntUser> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-10-10
     */
    List<EntUserResult> customList(@Param("paramCondition") EntUserParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-10-10
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") EntUserParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-10-10
     */
    Page<EntUserQueryResult> customPageList(@Param("page") Page page, @Param("paramCondition") EntUserQueryParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-10-10
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") EntUserParam paramCondition);

    /**
     * 根据用户id获取应用id集合
     *
     *
     * @date 2019/10/28
     */
    Set<Long> getCompanyIdsByUserId(@Param("userId") Long userId);

    /**
     * 获取账号id
     *
     *
     * @date 2019/10/28
     */
    Long getAccountIdByUserId(@Param("userId") Long userId);

    /**
     * 获取用户的角色集合
     *
     *
     * @date 2019/10/29
     */
    List<Long> getUserRoles(@Param("userId") Long userId);
}
