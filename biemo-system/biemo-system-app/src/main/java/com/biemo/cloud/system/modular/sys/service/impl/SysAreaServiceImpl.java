package com.biemo.cloud.system.modular.sys.service.impl;

import com.biemo.cloud.system.modular.sys.entity.SysArea;
import com.biemo.cloud.system.modular.sys.mapper.SysAreaMapper;
import com.biemo.cloud.system.modular.sys.model.params.SysAreaParam;
import com.biemo.cloud.system.modular.sys.model.result.SysAreaResult;
import com.biemo.cloud.system.modular.sys.service.SysAreaService;
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
 * 基础字典 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Override
    public void add(SysAreaParam param) {
        SysArea entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysAreaParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysAreaParam param) {
        SysArea oldEntity = getOldEntity(param);
        SysArea newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysAreaResult findBySpec(SysAreaParam param) {
        return null;
    }

    @Override
    public List<SysAreaResult> findListBySpec(SysAreaParam param) {
        return null;
    }

    @Override
    public PageResult findPageBySpec(SysAreaParam param) {
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult(page);
    }

    private Serializable getKey(SysAreaParam param) {
        return param.getAreaId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysArea getOldEntity(SysAreaParam param) {
        return this.getById(getKey(param));
    }

    private SysArea getEntity(SysAreaParam param) {
        SysArea entity = new SysArea();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
