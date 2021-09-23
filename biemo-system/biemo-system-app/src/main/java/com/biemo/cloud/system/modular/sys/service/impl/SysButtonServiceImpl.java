package com.biemo.cloud.system.modular.sys.service.impl;

import cn.hutool.core.map.MapUtil;
import com.biemo.cloud.system.modular.ent.utils.EntUtil;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.entity.SysButton;
import com.biemo.cloud.system.modular.sys.mapper.SysButtonMapper;
import com.biemo.cloud.system.modular.sys.model.params.SysButtonParam;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.system.modular.sys.service.SysButtonService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 按钮表 服务实现类
 * </p>
 *
 *
 * @since 2019-11-02
 */
@Service
public class SysButtonServiceImpl extends ServiceImpl<SysButtonMapper, SysButton> implements SysButtonService {

    @Autowired
    private SysAppService sysAppService;

    /**
     * 保存按钮信息
     *
     *
     * @date 2019/11/2
     */
    @Override
    public void add(SysButtonParam param) {
        SysButton entity = getEntity(param);
        entity.setOrderNo(EntUtil.getMaxSort(SysButton.class));
        entity.setStatus(StatusEnum.ENABLE.getCode());
        try {
            this.save(entity);
        } catch (DuplicateKeyException e) {
            throw new RequestEmptyException("按钮编码已存在");
        }
    }

    /**
     * 批量删除按钮
     *
     *
     * @date 2019/11/2
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] buttonIds) {
        if (ToolUtil.isEmpty(buttonIds)) {
            throw new RequestEmptyException("要删除的按钮数组为空");
        }
        this.removeByIds(Arrays.asList(buttonIds));
    }

    /**
     * 按钮更新
     *
     *
     * @date 2019/11/2
     */
    @Override
    public void update(SysButtonParam param) {
        if (ToolUtil.isEmpty(param.getOrderNo())) {
            throw new RequestEmptyException("按钮排序为空");
        }
        SysButton oldEntity = getOldEntity(param);
        SysButton newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        try {
            this.updateById(oldEntity);
        } catch (DuplicateKeyException e) {
            throw new RequestEmptyException("按钮编码已存在");
        }
    }

    @Override
    public PageResult<Map<String, Object>> findPageBySpec(Long menuId, String buttonName, String buttonCode) {
        Page pageContext = getPageContext();
        Page<Map<String, Object>> result = this.baseMapper.customPageList(pageContext, menuId, buttonName, buttonCode);
        return new PageResult<>(result);
    }

    /**
     * 获取按钮详情
     *
     *
     * @date 2019/11/2
     */
    @Override
    public SysButton getDetail(Long buttonId) {
        return this.getById(buttonId);
    }

    /**
     * 改变按钮状态
     *
     *
     * @date 2019/11/2
     */
    @Override
    public void changeStatus(Long buttonId, StatusEnum statusEnum) {
        if (ToolUtil.isEmpty(buttonId)) {
            throw new RequestEmptyException("按钮id为空");
        }
        if (statusEnum == null) {
            throw new RequestEmptyException("状态请求不合法");
        }

        SysButton sysButton = this.getById(buttonId);
        sysButton.setStatus(statusEnum.getCode());
        this.updateById(sysButton);
    }

    /**
     * 根据应用id获取按钮资源集合
     *
     *
     * @date 2019/11/3
     */
    @Override
    public Set<String> getButtonResCodesByAppId(Long appId) {
        return this.baseMapper.getButtonResCodesByAppId(appId);
    }

    /**
     * 获取资源级联列表
     *
     *
     * @date 2019/11/3
     */
    @Override
    public List<Map<String, Object>> getResources(Long appId) {
        SysApp app = this.sysAppService.getById(appId);
        if (ToolUtil.isEmpty(app)) {
            throw new RequestEmptyException("无效的appId");
        }
        //根据appCode获取所有的控制器信息
        Set<Map<String, String>> controllers = this.baseMapper.getAllControllers(app.getAppCode());
        List<Map<String, Object>> result = new ArrayList<>();
        controllers.forEach(controller -> {
            String modularName = controller.get("modularName");
            String modularCode = controller.get("modularCode");
            List<Map<String, Object>> children = this.baseMapper.getResMapsByModularCode(modularCode);
            Map<String, Object> modularRes = MapUtil.builder(new HashMap<String, Object>())
                    .put("resName", modularName)
                    .put("resCode", modularCode)
                    .put("children", children).build();
            result.add(modularRes);
        });
        return result;
    }

    private Serializable getKey(SysButtonParam param) {
        return param.getButtonId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private SysButton getOldEntity(SysButtonParam param) {
        return this.getById(getKey(param));
    }

    private SysButton getEntity(SysButtonParam param) {
        SysButton entity = new SysButton();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
