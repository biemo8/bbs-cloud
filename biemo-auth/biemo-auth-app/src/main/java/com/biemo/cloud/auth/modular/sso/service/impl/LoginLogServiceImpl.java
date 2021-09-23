package com.biemo.cloud.auth.modular.sso.service.impl;

import com.biemo.cloud.auth.api.context.LoginUser;
import com.biemo.cloud.auth.modular.sso.entity.AuthLoginLog;
import com.biemo.cloud.auth.modular.sso.enums.OperationEnum;
import com.biemo.cloud.auth.modular.sso.mapper.AuthLoginLogMapper;
import com.biemo.cloud.auth.modular.sso.model.TokenInfo;
import com.biemo.cloud.auth.modular.sso.model.result.AuthLoginLogResult;
import com.biemo.cloud.auth.modular.sso.service.LoginLogService;
import com.biemo.cloud.auth.modular.sso.util.IpAddressService;
import com.biemo.cloud.auth.modular.sso.util.IpInfoUtils;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<AuthLoginLogMapper, AuthLoginLog> implements LoginLogService {

    @Autowired
    private IpAddressService ipAddressService;

    @Override
    public void loginLog(HttpServletRequest request, TokenInfo tokenInfo) {
        AuthLoginLog authLoginLog = createAuthLoginLog(request, tokenInfo, OperationEnum.LOGIN);
        if(authLoginLog!=null){
            baseMapper.insert(authLoginLog);
        }
    }

    @Override
    public void logoutLog(HttpServletRequest request, TokenInfo tokenInfo) {
        AuthLoginLog authLoginLog = createAuthLoginLog(request, tokenInfo, OperationEnum.LOGIN_OUT);
        if(authLoginLog!=null){
            baseMapper.insert(authLoginLog);
        }
    }

    private AuthLoginLog createAuthLoginLog(HttpServletRequest request, TokenInfo tokenInfo, OperationEnum operationEnum) {
        LoginUser loginUser = tokenInfo.getLoginUser();
        if(loginUser==null){
            return null;
        }
        String ipAddr = IpInfoUtils.getIpAddr(request);
        AuthLoginLog authLoginLog = new AuthLoginLog();
        authLoginLog.setIpAddress(ipAddr);
        String address = ipAddressService.getCityInfo(ipAddr);
        authLoginLog.setLocalAddress(address != null ? address : "未找到");
        authLoginLog.setAccount(loginUser!=null&&ToolUtil.isNotEmpty(loginUser.getAccount()) ? loginUser.getAccount() : "");
        authLoginLog.setName(loginUser.getName());
        authLoginLog.setCompanyId(loginUser.getCompanyId());
        authLoginLog.setClientId(tokenInfo.getAuthClient().getClientId());
        authLoginLog.setOperation(operationEnum.getMessage());
        authLoginLog.setLoginTime(new Date());
        return authLoginLog;
    }

    @Override
    public Page pageList(String account) {
        Page page = PageFactory.defaultPage();
        List<Map<String, Object>> pageList = this.baseMapper.pageList(page, account);
        return page.setRecords(pageList);
    }

    @Override
    public AuthLoginLogResult detail(Long loginLogId) {
        return this.baseMapper.detail(loginLogId);
    }

}
