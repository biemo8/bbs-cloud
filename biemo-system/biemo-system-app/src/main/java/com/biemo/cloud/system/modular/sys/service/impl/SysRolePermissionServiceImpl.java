package com.biemo.cloud.system.modular.sys.service.impl;

import com.biemo.cloud.system.modular.sys.entity.SysRolePermission;
import com.biemo.cloud.system.modular.sys.mapper.SysRolePermissionMapper;
import com.biemo.cloud.system.modular.sys.model.params.SysRolePermissionParam;
import com.biemo.cloud.system.modular.sys.model.result.SysRolePermissionResult;
import com.biemo.cloud.system.modular.sys.service.SysRolePermissionService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Override
    public void add(SysRolePermissionParam param) {
        SysRolePermission entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysRolePermissionParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysRolePermissionParam param) {
        SysRolePermission oldEntity = getOldEntity(param);
        SysRolePermission newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysRolePermissionResult findBySpec(SysRolePermissionParam param) {
        return null;
    }

    @Override
    public List<SysRolePermissionResult> findListBySpec(SysRolePermissionParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysRolePermissionParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    private Serializable getKey(SysRolePermissionParam param) {
        return param.getRolePermissionId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysRolePermission getOldEntity(SysRolePermissionParam param) {
        return this.getById(getKey(param));
    }

    private SysRolePermission getEntity(SysRolePermissionParam param) {
        SysRolePermission entity = new SysRolePermission();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
