package com.biemo.cloud.auth.modular.sso.service;

import com.biemo.cloud.auth.modular.sso.entity.AuthLoginLog;
import com.biemo.cloud.auth.modular.sso.model.TokenInfo;
import com.biemo.cloud.auth.modular.sso.model.result.AuthLoginLogResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Async
public interface LoginLogService extends IService<AuthLoginLog> {

    /**
     * 登录日志
     *
     *
     * @Date 2019/9/25 22:32
     */
    void loginLog(HttpServletRequest request, TokenInfo tokenInfo);

    /**
     * 登出日志
     *
     *
     * @Date 2019/9/25 22:32
     */
    void logoutLog(HttpServletRequest request, TokenInfo tokenInfo);

    /**
     * 获取登录日志列表（分页）
     *
     * @param account 账号
     *
     * @Date 2019/12/3 22:25
     */
    Page pageList(String account);

    /**
     * 详情
     *
     *
     * @Date 2019/12/3 22:25
     */
    AuthLoginLogResult detail(Long loginLogId);
}
