package com.biemo.cloud.system.modular.sys.service.impl;

import com.biemo.cloud.system.modular.sys.entity.SysUserRole;
import com.biemo.cloud.system.modular.sys.mapper.SysUserRoleMapper;
import com.biemo.cloud.system.modular.sys.model.params.RoleInfo;
import com.biemo.cloud.system.modular.sys.model.params.SysUserRoleParam;
import com.biemo.cloud.system.modular.sys.model.result.SysUserRoleResult;
import com.biemo.cloud.system.modular.sys.service.SysUserRoleService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public void add(SysUserRoleParam param) {
        SysUserRole entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysUserRoleParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysUserRoleParam param) {
        SysUserRole oldEntity = getOldEntity(param);
        SysUserRole newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysUserRoleResult findBySpec(SysUserRoleParam param) {
        return null;
    }

    @Override
    public List<SysUserRoleResult> findListBySpec(SysUserRoleParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysUserRoleParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    @Override
    public List<RoleInfo> getUserRoleInfo(Long accountId) {
        return this.baseMapper.getUserRoleInfo(accountId);
    }

    private Serializable getKey(SysUserRoleParam param) {
        return param.getUserRoleId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysUserRole getOldEntity(SysUserRoleParam param) {
        return this.getById(getKey(param));
    }

    private SysUserRole getEntity(SysUserRoleParam param) {
        SysUserRole entity = new SysUserRole();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
