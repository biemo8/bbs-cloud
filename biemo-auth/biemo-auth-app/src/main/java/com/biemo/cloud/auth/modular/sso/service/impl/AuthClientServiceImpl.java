package com.biemo.cloud.auth.modular.sso.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.biemo.cloud.auth.modular.sso.consumer.SystemAppConsumer;
import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import com.biemo.cloud.auth.modular.sso.enums.ClientTypeEnum;
import com.biemo.cloud.auth.modular.sso.enums.LoginTypeEnum;
import com.biemo.cloud.auth.modular.sso.mapper.AuthClientMapper;
import com.biemo.cloud.auth.modular.sso.model.params.AuthClientParam;
import com.biemo.cloud.auth.modular.sso.model.result.AuthClientResult;
import com.biemo.cloud.auth.modular.sso.service.AuthClientService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.DeleteFlagEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 应用表 服务实现类
 *
 *
 * @Date 2019/9/25 22:30
 */
@Service
public class AuthClientServiceImpl extends ServiceImpl<AuthClientMapper, AuthClient> implements AuthClientService {

    @Autowired(required = false)
    private SystemAppConsumer systemAppConsumer;

    @Override
    public List<Map<String, Object>> getAppSelectList() {
        return systemAppConsumer.getAppSelect();
    }

    @Override
    public Page<Map<String, Object>> pageList(AuthClientParam authClientParam) {
        Page<Map<String, Object>> page = PageFactory.defaultPage();
        List<Map<String, Object>> pageList = this.baseMapper.pageList(page, authClientParam);
        Map<Long, String> appMap = systemAppConsumer.getAppMap();
        for (Map<String, Object> app : pageList) {
            Long appId = (Long) app.get("appId");
            app.put("appName", appMap.get(appId));
            app.put("clientTypeName", ClientTypeEnum.getName((Integer) app.get("clientType")));
            app.put("loginTypeName", LoginTypeEnum.getName((Integer) app.get("loginType")));
        }
        return page.setRecords(pageList);
    }

    @Override
    public void add(AuthClientParam param) {
        AuthClient authClient = new AuthClient();
        ToolUtil.copyProperties(param, authClient);
        validateClient(authClient);
        this.save(authClient);
    }

    @Override
    public void update(AuthClientParam param) {
        AuthClient authClient = new AuthClient();
        ToolUtil.copyProperties(param, authClient);
        validateClient(authClient);
        this.updateById(authClient);
    }

    private void validateClient(AuthClient authClient) {
        //TODO：add some validate for AuthClient here
    }

    @Override
    public void delete(Long clientId) {
        AuthClient authClient = new AuthClient();
        authClient.setClientId(clientId);
        authClient.setDelFlag(DeleteFlagEnum.Y.name());
        this.baseMapper.updateById(authClient);
    }

    @Override
    public AuthClientResult detail(Long clientId) {
        AuthClient authClient = this.getById(clientId);
        AuthClientResult result = new AuthClientResult();
        ToolUtil.copyProperties(authClient, result);

        Map<Long, String> appMap = systemAppConsumer.getAppMap();
        result.setAppName(appMap.get(result.getAppId()));
        result.setClientTypeName(ClientTypeEnum.getName(result.getClientType()));
        result.setLoginTypeName(LoginTypeEnum.getName(result.getLoginType()));
        return result;
    }

    @Override
    public String generateKey() {
        return RandomUtil.randomString(16);
    }

}
