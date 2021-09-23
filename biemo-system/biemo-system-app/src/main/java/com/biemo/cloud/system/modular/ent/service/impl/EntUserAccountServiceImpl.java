package com.biemo.cloud.system.modular.ent.service.impl;

import com.biemo.cloud.system.modular.ent.entity.EntUserAccount;
import com.biemo.cloud.system.modular.ent.mapper.EntUserAccountMapper;
import com.biemo.cloud.system.modular.ent.model.params.EntUserAccountParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserAccountResult;
import com.biemo.cloud.system.modular.ent.service.EntUserAccountService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 登录账号 服务实现类
 * </p>
 *
 *
 * @since 2019-10-10
 */
@Service
public class EntUserAccountServiceImpl extends ServiceImpl<EntUserAccountMapper, EntUserAccount> implements EntUserAccountService {

    @Override
    public void add(EntUserAccountParam param) {
        EntUserAccount entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EntUserAccountParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void update(EntUserAccountParam param) {
        EntUserAccount oldEntity = getOldEntity(param);
        EntUserAccount newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public EntUserAccountResult findBySpec(EntUserAccountParam param) {
        return null;
    }

    @Override
    public List<EntUserAccountResult> findListBySpec(EntUserAccountParam param) {
        return null;
    }

    @Override
    public PageResult<EntUserAccountResult> findPageBySpec(EntUserAccountParam param) {
        Page pageContext = getPageContext();
        IPage<EntUserAccountResult> page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult<>(page);
    }

    @Override
    public EntUserAccount getAccountInfoByAccount(String account) {
        return this.getOne(
                new QueryWrapper<EntUserAccount>().eq("account", account));
    }

    private Serializable getKey(EntUserAccountParam param) {
        return param.getAccountId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private EntUserAccount getOldEntity(EntUserAccountParam param) {
        return this.getById(getKey(param));
    }

    private EntUserAccount getEntity(EntUserAccountParam param) {
        EntUserAccount entity = new EntUserAccount();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
