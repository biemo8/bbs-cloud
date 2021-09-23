package com.biemo.cloud.system.modular.sys.service.impl;

import com.biemo.cloud.system.modular.res.enums.AppExceptionEnum;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.entity.SysResource;
import com.biemo.cloud.system.modular.sys.factory.ResourceFactory;
import com.biemo.cloud.system.modular.sys.mapper.SysResourceMapper;
import com.biemo.cloud.system.modular.sys.model.params.ResourceQueryParam;
import com.biemo.cloud.system.modular.sys.model.params.SysResourceParam;
import com.biemo.cloud.system.modular.sys.model.result.SysResourceResult;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.system.modular.sys.service.SysResourceService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.biemo.cloud.kernel.model.resource.ResourceDefinition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements SysResourceService {

    @Autowired
    private SysAppService appService;

    @Override
    public void add(SysResourceParam param) {
        SysResource entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysResourceParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysResourceParam param) {
        SysResource oldEntity = getOldEntity(param);
        SysResource newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysResourceResult findBySpec(SysResourceParam param) {
        return null;
    }

    @Override
    public List<SysResourceResult> findListBySpec(SysResourceParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysResourceParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    @Override
    public void deleteResourceByAppCode(String appCode) {
        SysResource resource = new SysResource();
        resource.setAppCode(appCode);
        this.remove(new QueryWrapper<>(resource));
    }

    @Override
    public void deleteResourceByProjectCode(String projectCode) {
        SysResource resource = new SysResource();
        resource.setProjectCode(projectCode);
        this.remove(new QueryWrapper<>(resource));
    }

    @Override
    public void saveResource(ResourceDefinition resourceDefinition) {
        SysResource resource = ResourceFactory.createResource(resourceDefinition);
        this.save(resource);
    }

    @Override
    public void saveResourceList(List<SysResource> resourceList) {
        this.saveBatch(resourceList);
    }

    @Override
    public SysResource getResourceByCode(String code) {

        SysResource resource = new SysResource();
        resource.setCode(code);

        return this.getOne(new QueryWrapper<>(resource));
    }

    @Override
    public Page<Map<String, Object>> getResourceList(ResourceQueryParam param) {

        //通过appId获取APP名称
        String appName = null;
        if (ToolUtil.isNotEmpty(param.getAppId())) {
            SysApp app = appService.getById(param.getAppId());
            if (app == null) {
                throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
            }
            appName = app.getAppName();
        }
        Page page = PageFactory.defaultPage();

        //查询资源列表
        List<Map<String, Object>> resourceList = this.baseMapper.getResourceList(page, appName, param.getResourceName(), param.getMenuFlag());
        page.setRecords(resourceList);

        return page;
    }

    @Override
    public List<Map<String, Object>> getResourceSelectList(Long appId) {

        //通过appId查询到appCode
        SysApp app = appService.getById(appId);
        if (app == null) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
        }

        //通过appCode获取该应用的资源
        String appCode = app.getAppCode();
        QueryWrapper<SysResource> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("app_code", appCode).and(i -> i.eq("menu_flag", "Y"));
        entityWrapper.select("code as resCode, name as name");

        return this.listMaps(entityWrapper);
    }


    @Override
    public List<Map<String, Object>> getResourceList4Permission(Long appId) {

        //通过appId查询到appCode
        SysApp app = appService.getById(appId);
        if (app == null) {
            throw new ServiceException(AppExceptionEnum.APP_NOT_FOUND);
        }

        //通过appCode获取该应用的资源
        String appCode = app.getAppCode();
        QueryWrapper<SysResource> entityWrapper = new QueryWrapper<>();
        entityWrapper.eq("app_code", appCode);
        entityWrapper.select("resource_id as resourceId, name as name," +
                "app_code as appCode, class_name as className," +
                "method_name as methodName, modular_name as modularName");
        return this.listMaps(entityWrapper);
    }

    @Override
    public List<SysResource> getResourceByAppCode(String appCode) {

        SysResource resource = new SysResource();
        resource.setAppCode(appCode);
        QueryWrapper<SysResource> condition = new QueryWrapper<>(resource);

        return list(condition);
    }

    @Override
    public Set<String> getResourceUrls(Set<String> resCodes) {

        if (resCodes == null || resCodes.size() == 0) {
            return new HashSet<>();
        } else {
            List<SysResource> resources = this.list(new QueryWrapper<SysResource>()
                    .in("code", resCodes).select("resource_id as resourceId,code as code,url as url"));

            HashSet<String> resourceUrls = new HashSet<>();
            for (SysResource resource : resources) {
                resourceUrls.add(resource.getUrl());
            }

            return resourceUrls;
        }
    }

    private Serializable getKey(SysResourceParam param) {
        return param.getResourceId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysResource getOldEntity(SysResourceParam param) {
        return this.getById(getKey(param));
    }

    private SysResource getEntity(SysResourceParam param) {
        SysResource entity = new SysResource();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
