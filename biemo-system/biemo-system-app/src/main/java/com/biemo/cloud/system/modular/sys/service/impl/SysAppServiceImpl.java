package com.biemo.cloud.system.modular.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.biemo.cloud.auth.api.context.LoginContext;
import com.biemo.cloud.system.modular.dict.entity.SysDict;
import com.biemo.cloud.system.modular.dict.service.DictService;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.mapper.SysAppMapper;
import com.biemo.cloud.system.modular.sys.model.params.SysAppParam;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用管理 服务实现类
 * </p>
 *
 *
 * @since 2019-09-10
 */
@Slf4j
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {

    @Autowired
    private DictService dictService;

    @Override
    public void add(SysAppParam param) {
        SysApp entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(Long appId) {
        this.removeById(appId);
    }

    @Override
    public void update(SysAppParam param) {
        SysApp oldEntity = getOldEntity(param);
        SysApp newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(oldEntity);
    }

    @Override
    public Page<Map<String, Object>> findPageBySpec(String appName) {
        Page pageContext = PageFactory.defaultPage();
        return this.baseMapper.customPageMapList(pageContext, appName);
    }

    @Override
    public Map<String, Object> detail(Long appId) {
        SysApp sysApp = this.baseMapper.selectById(appId);
        if (ToolUtil.isEmpty(sysApp.getAppType())) {
            return BeanUtil.beanToMap(sysApp);
        }

        Map<String, Object> map = BeanUtil.beanToMap(sysApp);
        SysDict dict = dictService.getById(sysApp.getAppType());
        if (ToolUtil.isEmpty(dict)) {
            map.put("appTypeName", "");
        } else {
            map.put("appTypeName", dict.getDictName());
        }
        return map;
    }

    @Override
    public void changeStatus(Long appId, StatusEnum statusEnum) {
        if (ToolUtil.isEmpty(appId)) {
            throw new RequestEmptyException("appId为空");
        }
        if (statusEnum == null) {
            throw new RequestEmptyException("状态请求不合法");
        }
        SysApp sysApp = this.baseMapper.selectById(appId);
        sysApp.setStatus(statusEnum.getCode());
        this.baseMapper.updateById(sysApp);
    }

    @Override
    public List<Map<String, Object>> getAppSelect() {
        QueryWrapper<SysApp> entityWrapper = new QueryWrapper<>();
        entityWrapper = entityWrapper.eq("status", StatusEnum.ENABLE.getCode()).select("app_id as appId", "app_name " +
                "as appName");
        return this.listMaps(entityWrapper);
    }

    @Override
    public SysApp getAppByCode(String appCode) {
        SysApp app = new SysApp();
        app.setAppCode(appCode);
        return this.getOne(new QueryWrapper<>(app));
    }

    /**
     * 根据当前登录人获取应用下拉列表
     *
     *
     * @date 2019/11/26
     * @return
     */
    @Override
    public List<SysAppParam> getAppSelectByCurrentUser() {
        Long accountId = LoginContext.me().getAccountId();
        return this.baseMapper.getAppSelectByCurrentUser(accountId);
    }

    private Serializable getKey(SysAppParam param) {
        return param.getAppId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysApp getOldEntity(SysAppParam param) {
        return this.getById(getKey(param));
    }

    private SysApp getEntity(SysAppParam param) {
        SysApp entity = new SysApp();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
