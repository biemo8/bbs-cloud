package com.biemo.cloud.system.modular.sys.service.impl;

import com.biemo.cloud.system.core.error.SysExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.SysPermissionResource;
import com.biemo.cloud.system.modular.sys.mapper.SysPermissionResourceMapper;
import com.biemo.cloud.system.modular.sys.model.params.SysPermissionResourceParam;
import com.biemo.cloud.system.modular.sys.model.result.SysPermissionResourceResult;
import com.biemo.cloud.system.modular.sys.service.SysButtonService;
import com.biemo.cloud.system.modular.sys.service.SysMenuService;
import com.biemo.cloud.system.modular.sys.service.SysPermissionResourceService;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 权限资源表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
@Slf4j
public class SysPermissionResourceServiceImpl extends ServiceImpl<SysPermissionResourceMapper, SysPermissionResource> implements SysPermissionResourceService {

    @Resource
    private SysPermissionResourceMapper permissionResourceMapper;

    @Resource
    private SysResourceService resourceService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysButtonService buttonService;

    @Override
    public void add(SysPermissionResourceParam param) {
        SysPermissionResource entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysPermissionResourceParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysPermissionResourceParam param) {
        SysPermissionResource oldEntity = getOldEntity(param);
        SysPermissionResource newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysPermissionResourceResult findBySpec(SysPermissionResourceParam param) {
        return null;
    }

    @Override
    public List<SysPermissionResourceResult> findListBySpec(SysPermissionResourceParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysPermissionResourceParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    private Serializable getKey(SysPermissionResourceParam param) {
        return param.getPermissionResourceId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysPermissionResource getOldEntity(SysPermissionResourceParam param) {
        return this.getById(getKey(param));
    }

    private SysPermissionResource getEntity(SysPermissionResourceParam param) {
        SysPermissionResource entity = new SysPermissionResource();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int permissionBindResource(Long permissionId, List<String> resourceList) {

        int count = 0;
        if (ToolUtil.isEmpty(permissionId)) {
            throw new ServiceException(SysExceptionEnum.DB_PERMISSION_NULL);
        }
        if (resourceList == null || resourceList.size() == 0)
            return count;

        // 删除权限的资源
        QueryWrapper<SysPermissionResource> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("permission_id", permissionId);
        remove(QueryWrapper);

        // 批量写入
        List<SysPermissionResource> permissionResourcesList = new ArrayList<>();

        for (int i = 0; i < resourceList.size(); i++) {

            // 新建实体
            SysPermissionResource permissionResource = new SysPermissionResource();
            permissionResource.setPermissionId(permissionId);
            permissionResource.setResourceId(resourceList.get(i));

            // 已经存在,跳过绑定该资源
            if (checkPermissionResource(permissionId, resourceList.get(i))) {
                continue;
            }
            permissionResourcesList.add(permissionResource);
            count++;
        }

        if (count > 0)
            saveBatch(permissionResourcesList, permissionResourcesList.size());
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancelPermissionBindResource(Long permissionId, List<String> resourceList) {

        int count = 0;
        if (ToolUtil.isEmpty(permissionId)) {
            throw new ServiceException(SysExceptionEnum.DB_PERMISSION_NULL);
        }
        if (resourceList == null || resourceList.size() == 0)
            return count;

        for (int i = 0; i < resourceList.size(); i++) {
            QueryWrapper<SysPermissionResource> QueryWrapper = new QueryWrapper<>();
            QueryWrapper.eq("permission_id", permissionId);
            QueryWrapper.eq("resource_id", resourceList.get(i));

            // 删除
            if (remove(QueryWrapper))
                count++;
        }
        return count;
    }

    @Override
    public List<Map<String, Object>> getPermissionBindResourceList(Long permissionId) {

        if (ToolUtil.isEmpty(permissionId)) {
            throw new ServiceException(SysExceptionEnum.DB_PERMISSION_NULL);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("permissionId", permissionId);

        return permissionResourceMapper.selectResourceList(param);
    }

    @Override
    public Map<String, Object> getPermissionResource(Long appId, Long permissionId) {

        Map<String, Object> result = new HashMap<>();

        // 获取权限已绑定的资源
        List<Map<String, Object>> bindResourceList = getPermissionBindResourceList(permissionId);

        // 应用的资源ID
        List<Map<String, Object>> resourceList = resourceService.getResourceList4Permission(appId);
        List<Map<String, Object>> notBindResourceList = new ArrayList<>();
        for (int i = 0; i < resourceList.size(); i++) {
            Map<String, Object> resource = resourceList.get(i);
            if (!bindResourceList.contains(resource))
                notBindResourceList.add(resource);
        }
        result.put("bindResourceList", bindResourceList);
        result.put("notBindResorceList", notBindResourceList);

        return result;
    }


    @Override
    public Map<String, Object> getResourceList4Permission(Long appId, Long permissionId) {

        // 获取权限已绑定的资源
        List<Map<String, Object>> bindResourceList = getPermissionBindResourceList(permissionId);

        // 应用的资源ID
        List<Map<String, Object>> resourceList = resourceService.getResourceList4Permission(appId);

        //菜单资源集合
        Set<String> menuResCodes = menuService.getMenuResCodesByAppId(appId);

        //按钮资源集合
        Set<String> buttonResCodes = buttonService.getButtonResCodesByAppId(appId);

        HashMap<String, Object> result = new HashMap<>();

        for (Map<String, Object> resource : resourceList) {
            String modularName = (String) resource.get("modularName");
            String resourceId = (String) resource.get("resourceId");
            resource.put("isBindResource", bindResourceList.contains(resource));
            resource.put("isMenu", menuResCodes.contains(resourceId));
            resource.put("isButton", buttonResCodes.contains(resourceId));

            //map中包含的key-value
            List<Map<String, Object>> keyValueList = new ArrayList<>();
            if (result.containsKey(modularName)) {
                keyValueList = (ArrayList<Map<String, Object>>) result.get(modularName);
                keyValueList.add(resource);
            } else {
                keyValueList.add(resource);
            }
            result.put(modularName, keyValueList);

        }
        //对权限管理菜单资源进行排序
        mapListSort(result);
        return result;
    }

    /**
     * 对菜单资源列表map进行排序
     *
     * @param map 菜单资源
     *
     * @Date 2019/9/26 23:02
     */
    private void mapListSort(Map<String, Object> map) {
        for (Map.Entry<String, Object> objectEntry : map.entrySet()) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) objectEntry.getValue();
            listSort(list);
        }
    }

    /**
     * 对菜单资源list根据资源名称进行排序
     *
     * @param list: 子菜单资源列表
     *
     * @Date 2019/9/26 23:02
     */
    private void listSort(List<Map<String, Object>> list) {
        try {
            list.sort((o1, o2) -> {
                String name = (String) o1.get("name");
                String name2 = (String) o2.get("name");
                if (name.contains("-") && name2.contains("-")) {
                    String[] temp1 = name.split("-");
                    String[] temp2 = name2.split("-");
                    if (NumberUtils.isNumber(temp1[0]) && NumberUtils.isNumber(temp2[0])) {
                        float var1 = Float.parseFloat(temp1[0]);
                        float var2 = Float.parseFloat(temp2[0]);
                        return Float.compare(var1, var2);
                    }
                }
                return name.compareTo(name2);
            });
        } catch (Exception e) {
            log.error("菜单排序错误", e);
        }
    }

    /**
     * 权限是否已经绑定该资源
     *
     * @param permissionId 权限ID
     * @param resourceId   资源ID
     *
     * @Date 2019/9/26 23:02
     */
    private boolean checkPermissionResource(Long permissionId, String resourceId) {
        QueryWrapper<SysPermissionResource> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("permission_id", permissionId);
        QueryWrapper.eq("resource_id", resourceId);
        int count = count(QueryWrapper);
        return count > 0;
    }


}
