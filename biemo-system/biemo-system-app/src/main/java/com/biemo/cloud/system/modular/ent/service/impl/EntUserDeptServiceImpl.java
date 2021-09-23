package com.biemo.cloud.system.modular.ent.service.impl;

import com.biemo.cloud.system.modular.ent.entity.EntUserDept;
import com.biemo.cloud.system.modular.ent.mapper.EntUserDeptMapper;
import com.biemo.cloud.system.modular.ent.model.params.EntUserDeptParam;
import com.biemo.cloud.system.modular.ent.model.result.EntUserDeptResult;
import com.biemo.cloud.system.modular.ent.service.EntUserDeptService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.YesOrNotEnum;
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
 * 用户部门关联表 服务实现类
 * </p>
 *
 *
 * @since 2019-10-10
 */
@Service
public class EntUserDeptServiceImpl extends ServiceImpl<EntUserDeptMapper, EntUserDept> implements EntUserDeptService {

    @Override
    public void add(EntUserDeptParam param) {
        EntUserDept entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(EntUserDeptParam param) {

    }

    @Override
    public void update(EntUserDeptParam param) {

    }

    @Override
    public EntUserDeptResult findBySpec(EntUserDeptParam param) {
        return null;
    }

    @Override
    public List<EntUserDeptResult> findListBySpec(EntUserDeptParam param) {
        return null;
    }

    @Override
    public PageResult<EntUserDeptResult> findPageBySpec(EntUserDeptParam param) {
        Page pageContext = getPageContext();
        IPage<EntUserDeptResult> page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult<>(page);
    }

    @Override
    public EntUserDept getUserDefaultDept(Long userId) {
        return this.getOne(
                new QueryWrapper<EntUserDept>()
                        .eq("default_flag", YesOrNotEnum.Y.name())
                        .and(i -> i.eq("user_id", userId)));
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }


    private EntUserDept getEntity(EntUserDeptParam param) {
        EntUserDept entity = new EntUserDept();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
