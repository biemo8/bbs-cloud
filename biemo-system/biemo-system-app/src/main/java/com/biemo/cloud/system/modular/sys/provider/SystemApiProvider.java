package com.biemo.cloud.system.modular.sys.provider;

import com.biemo.cloud.system.api.SystemAppApi;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统基础服务提供者
 *
 *
 * @Date 2019/12/4 21:53
 */
@RestController
@Slf4j
public class SystemApiProvider implements SystemAppApi {

    @Autowired
    private SysAppService sysAppService;

    @Override
    public Map<Long, String> getAppMap() {
        List<Map<String, Object>> appSelect = sysAppService.getAppSelect();
        Map<Long, String> appMap = new HashMap(appSelect.size());
        for (Map<String, Object> map : appSelect) {
            appMap.put((Long) map.get("appId"), (String) map.get("appName"));
        }
        return appMap;
    }

    @Override
    public List<Map<String, Object>> getAppSelect() {
        return sysAppService.getAppSelect();
    }
}
