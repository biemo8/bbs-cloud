package com.biemo.cloud.system.modular.sys.provider;

import com.biemo.cloud.system.api.ResourceServiceApi;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.factory.ResourceFactory;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 资源服务
 *
 *
 * @Date 2019/12/4 21:52
 */
@RestController
public class ResourceServiceProvider implements ResourceServiceApi {

    @Autowired
    private SysResourceService resourceService;

    @Autowired
    private SysAppService appService;

    /**
     * 保存新增的资源
     *
     *
     * @Date 2019/12/4 21:52
     */
    @Override
    public void saveResource(@RequestBody ResourceDefinition resourceDefinition) {
        resourceService.saveResource(resourceDefinition);
    }

    /**
     * 修改资源
     *
     *
     * @Date 2019/12/4 21:52
     */
    @Override
    public void editResource(@RequestBody ResourceDefinition resourceDefinition) {
        SysResource resource = ResourceFactory.createResource(resourceDefinition);
        this.resourceService.updateById(resource);
    }

    /**
     * 删除资源
     *
     *
     * @Date 2019/12/4 21:52
     */
    @Override
    public void removeResource(@RequestParam String resourceId) {
        this.resourceService.removeById(resourceId);
    }

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/4 21:52
     */
    @Override
    public List<Map<String, Object>> getAppSelect() {
        QueryWrapper<SysApp> wrapper = new QueryWrapper<>();
        wrapper = wrapper.select("app_name AS appName,app_code AS appCode")
                .eq("status", StatusEnum.ENABLE.getCode());
        return this.appService.listMaps(wrapper);
    }

    @Override
    public List<Map<String, Object>> getResModuleSelect() {
        return this.resourceService.listMaps(new QueryWrapper<SysResource>()
                .select("modular_name as modularName,modular_code as modularCode")
                .groupBy("modular_code"));

    }
}
