package com.biemo.cloud.auth.modular.sso.mapper;

import com.biemo.cloud.auth.modular.sso.entity.AuthLoginLog;
import com.biemo.cloud.auth.modular.sso.model.params.AuthLoginLogParam;
import com.biemo.cloud.auth.modular.sso.model.result.AuthLoginLogResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-26
 */
public interface AuthLoginLogMapper extends BaseMapper<AuthLoginLog> {

    /**
     * 获取列表
     *
     *
     * @Date 2019-09-26
     */
    List<AuthLoginLogResult> customList(@Param("paramCondition") AuthLoginLogParam paramCondition);

    /**
     * 获取map列表
     *
     *
     * @Date 2019-09-26
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") AuthLoginLogParam paramCondition);

    /**
     * 获取分页实体列表
     *
     *
     * @Date 2019-09-26
     */
    Page<AuthLoginLogResult> customPageList(@Param("page") Page page, @Param("paramCondition") AuthLoginLogParam paramCondition);

    /**
     * 获取分页map列表
     *
     *
     * @Date 2019-09-26
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") AuthLoginLogParam paramCondition);

    /**
     * 获取日志列表（分页）
     *
     * @param page    分页参数
     * @param account 账号
     *
     * @Date 2019/10/28 14:56
     */
    List<Map<String, Object>> pageList(@Param("page") Page page, @Param("account") String account);

    /**
     * 详情
     *
     * @param loginLogId 登录日志id
     *
     * @Date 2019/10/28 15:17
     */
    AuthLoginLogResult detail(@Param("loginLogId") Long loginLogId);

}
