package com.biemo.cloud.system.modular.res.provider;

import com.biemo.cloud.system.modular.res.cache.ResourceCache;
import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.factory.ResourceFactory;
import com.biemo.cloud.system.modular.sys.mapper.SysResourceMapper;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.service.SysPermissionService;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.system.modular.sys.service.SysUserRoleService;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.api.ResourceService;
import com.biemo.cloud.kernel.model.api.model.ReportResourceReq;
import com.biemo.cloud.kernel.model.api.model.ResourceUrlReq;
import com.biemo.cloud.kernel.model.api.model.UserResourceReq;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 资源搜集提供服务
 *
 *
 * @date 2019-05-29-14:00
 */
@RestController
@Slf4j
public class ResourceProvider implements ResourceService {

    @Autowired
    private SysResourceService resourceService;

    @Autowired
    private SysResourceMapper resourceMapper;

    @Autowired
    private ResourceCache resourceCache;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reportResources(@RequestBody ReportResourceReq reportResourceReq) {

        String projectCode = reportResourceReq.getProjectCode();
        Map<String, Map<String, ResourceDefinition>> resourceDefinitions = reportResourceReq.getResourceDefinitions();

        if (ToolUtil.isEmpty(projectCode) || resourceDefinitions == null) {
            return;
        }

        //根据project删除该项目下的所有资源
        resourceService.deleteResourceByProjectCode(projectCode);

        //获取当前应用的所有资源
        ArrayList<SysResource> allResources = new ArrayList<>();
        for (Map.Entry<String, Map<String, ResourceDefinition>> appModularResources : resourceDefinitions.entrySet()) {
            Map<String, ResourceDefinition> value = appModularResources.getValue();
            for (Map.Entry<String, ResourceDefinition> modularResources : value.entrySet()) {
                SysResource resource = ResourceFactory.createResource(modularResources.getValue());
                allResources.add(resource);
            }
        }

        //将资源存入库中
        resourceService.saveBatch(allResources, allResources.size());

        //将资源存入缓存一份
        resourceCache.saveResourcesToCache(allResources);
    }

    @Override
    public Set<String> getUserResourceUrls(@RequestBody UserResourceReq userResourceReq) {

        // 1. 获取用户角色
        List<RoleInfo> userRoleInfo = sysUserRoleService.getUserRoleInfo(Long.valueOf(userResourceReq.getAccountId()));
        if (userRoleInfo == null || userRoleInfo.size() == 0) {
            log.error("查询到用户角色为空！accountId={}", userResourceReq.getAccountId());
        }

        // 2. 获取用户权限
        Set<String> resourcesUrls = sysPermissionService.getUserResources(userRoleInfo);
        if (resourcesUrls == null || resourcesUrls.size() == 0) {
            log.error("查询到用户资源为空！accountId={}", userResourceReq.getAccountId());
        }

        return resourcesUrls;
    }

    @Override
    public ResourceDefinition getResourceByUrl(@RequestBody ResourceUrlReq resourceUrlReq) {
        if (ToolUtil.isEmpty(resourceUrlReq.getUrl())) {
            return null;
        } else {

            List<SysResource> resources = resourceMapper.selectList(
                    new QueryWrapper<SysResource>().eq("url", resourceUrlReq.getUrl()));

            if (resources == null || resources.isEmpty()) {
                return null;
            } else {

                // 获取接口资源信息
                SysResource resource = resources.get(0);
                ResourceDefinition resourceDefinition = new ResourceDefinition();
                BeanUtils.copyProperties(resource, resourceDefinition);

                // 获取是否需要登录的标记, 判断是否需要登录，如果是则设置为true,否则为false
                String requiredLoginFlag = resource.getRequiredLoginFlag();
                if (YesOrNotEnum.Y.name().equals(requiredLoginFlag)) {
                    resourceDefinition.setRequiredLogin(true);
                } else {
                    resourceDefinition.setRequiredLogin(false);
                }

                // 获取请求权限的标记，判断是否有权限，如果有则设置为true,否则为false
                String requiredPermissionFlag = resource.getRequiredPermissionFlag();
                if (YesOrNotEnum.Y.name().equals(requiredPermissionFlag)) {
                    resourceDefinition.setRequiredPermission(true);
                } else {
                    resourceDefinition.setRequiredPermission(false);
                }

                // 获取是否是菜单的flag，如果是则设置为true,否则为false
                String menuFlag = resource.getMenuFlag();
                if (YesOrNotEnum.Y.name().equals(menuFlag)) {
                    resourceDefinition.setMenuFlag(true);
                } else {
                    resourceDefinition.setMenuFlag(false);
                }

                return resourceDefinition;
            }
        }
    }

    /**
     * 新老资源分拣出来,将需要新增的新增,需要修改的修改
     *
     *
     * @Date 2019-05-29 14:06
     */
    private Map<String, List<SysResource>> separateResource(List<SysResource> historyResource, List<SysResource> newResource) {

        Map<String, List<SysResource>> result = new HashMap<>();

        //需要新增到数据库中的资源
        List<SysResource> addResource = new ArrayList<>();

        //需要修改的资源
        List<SysResource> updateResource = new ArrayList<>();

        if (historyResource == null) {
            historyResource = new ArrayList<>();
        }
        if (newResource == null) {
            newResource = new ArrayList<>();
        }

        for (SysResource newRes : newResource) {
            boolean falg = false;
            for (SysResource hisRes : historyResource) {

                //如果 resourceId 相同则是需要更新的 如果不存在 就是需要新增的
                if (newRes.getCode().equals(hisRes.getCode())) {
                    falg = true;
                    break;
                }
            }
            if (falg) {
                updateResource.add(newRes);
            } else {
                addResource.add(newRes);
            }
        }

        result.put("updateResource", updateResource);
        result.put("addResource", addResource);

        return result;
    }
}
