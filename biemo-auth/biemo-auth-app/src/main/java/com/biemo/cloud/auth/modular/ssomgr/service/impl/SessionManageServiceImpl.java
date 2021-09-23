package com.biemo.cloud.auth.modular.ssomgr.service.impl;

import com.biemo.cloud.auth.modular.sso.model.SessionInfo;
import com.biemo.cloud.auth.modular.sso.service.SessionService;
import com.biemo.cloud.auth.modular.ssomgr.service.SessionManageService;
import com.biemo.cloud.auth.modular.ssomgr.util.PageUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 会话管理实现
 *
 *
 * @Date 2019/12/4 21:15
 */
@Service
public class SessionManageServiceImpl implements SessionManageService {

    @Autowired
    private SessionService sessionService;

    @Override
    public Page pageList(String name) {
        List<SessionInfo> sessionInfoList = sessionService.getSessionInfoList(name);
        return PageUtil.createPageList(sessionInfoList);
    }

    @Override
    public void forcedOffline(String ticket) {
        sessionService.forcedOffline(ticket);
    }

}
