package com.biemo.cloud.system.modular.ent.service.impl;

import cn.hutool.core.map.MapBuilder;
import com.biemo.cloud.system.core.util.CodeUtil;
import com.biemo.cloud.system.modular.ent.entity.EntCompApp;
import com.biemo.cloud.system.modular.ent.entity.EntCompany;
import com.biemo.cloud.system.modular.ent.mapper.EntCompanyMapper;
import com.biemo.cloud.system.modular.ent.model.params.EntCompanyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntCompanyResult;
import com.biemo.cloud.system.modular.ent.service.EntCompAppService;
import com.biemo.cloud.system.modular.ent.service.EntCompanyService;
import com.biemo.cloud.system.modular.ent.utils.EntUtil;
import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.service.SysAppService;
import com.biemo.cloud.core.page.PageFactory;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.exception.RequestEmptyException;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.biemo.cloud.system.modular.ent.common.EntConst.ROOT_ID;
import static com.biemo.cloud.system.modular.ent.common.EntConst.ROOT_PID;
import static com.biemo.cloud.system.modular.ent.enums.EntException.*;
import static com.biemo.cloud.kernel.model.enums.StatusEnum.DISABLE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * <p>
 * 企业信息 服务实现类
 * </p>
 *
 *
 * @since 2019-10-10
 */
@Service
public class EntCompanyServiceImpl extends ServiceImpl<EntCompanyMapper, EntCompany> implements EntCompanyService {

    @Autowired
    private EntCompAppService compAppService;

    @Autowired
    private SysAppService sysAppService;

    /**
     * 新增公司
     *
     *
     * @date 2019/10/12
     */
    @Override
    public void add(EntCompanyParam param) {
        EntCompany entity = getEntity(param);

        //参数校验
        if (ToolUtil.isOneEmpty(entity.getName(), entity.getShortName())) {
            throw new ServiceException(REQ_PARAM_EXIST_NULL);
        }
        Boolean nullParentId = ToolUtil.isEmpty(entity.getParentId()) ? TRUE : FALSE;
        if (nullParentId) {
            throw new ServiceException(PID_NOT_EXIST);
        }

        //生成反序父ids
        String adversePids = getPids(entity.getParentId(), new StringBuffer());
        entity.setParentIds(EntUtil.transToPids(adversePids));

        //生成编码
        entity.setCpCode(CodeUtil.getEntCode(entity.getName()));

        //排序
        entity.setOrderNo(EntUtil.getMaxSort(EntCompany.class));

        //默认为启用
        entity.setStatus(StatusEnum.ENABLE.getCode());
        this.save(entity);
    }

    /**
     * 获取父ids
     *
     *
     * @date 2019/10/12
     */
    private String getPids(Long parentId, StringBuffer pids) {
        if (parentId.equals(ROOT_ID)) {
            return pids.append(ROOT_ID).append(",").toString();
        } else {
            pids.append(parentId).append(",");
        }
        EntCompany parentComp = this.getById(parentId);
        return getPids(parentComp.getParentId(), pids);
    }

    @Override
    public void delete(EntCompanyParam param) {
        this.removeById(getKey(param));
    }

    /**
     * 公司更新
     *
     *
     * @date 2019/10/12
     */
    @Override
    public void update(EntCompanyParam param) {
        EntCompany oldEntity = getOldEntity(param);
        EntCompany newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);

        //更新公司pids
        String reversePids = getPids(oldEntity.getParentId(), new StringBuffer());
        oldEntity.setParentIds(EntUtil.transToPids(reversePids));
        this.updateById(oldEntity);
    }

    @Override
    public PageResult<EntCompanyResult> findPageBySpec(EntCompanyParam param) {
        Page pageContext = getPageContext();
        IPage<EntCompanyResult> page = this.baseMapper.customPageList(pageContext, param);
        return new PageResult<>(page);
    }

    /**
     * 获取公司详情
     *
     * @return
     *
     * @date 2019/10/12
     */
    @Override
    public EntCompany detail(Long companyId) {
        return this.getById(companyId);
    }

    /**
     * 公司启用禁用
     *
     *
     * @date 2019/10/12
     */
    @Override
    public void changeStatus(Long companyId, StatusEnum statusEnum) {
        if (ToolUtil.isEmpty(companyId)) {
            throw new RequestEmptyException("companyId为空");
        }
        if (statusEnum == null) {
            throw new RequestEmptyException("状态请求不合法");
        }
        if (statusEnum == DISABLE) {
            //禁用操作
            int sonCount = this.count(new QueryWrapper<EntCompany>()
                    .eq("parent_id", companyId)
                    .eq("status", StatusEnum.ENABLE.getCode()));
            if (sonCount != 0) {
                throw new ServiceException(CHECKED_FAILED);
            }
        }
        EntCompany company = this.getById(companyId);
        company.setStatus(statusEnum.getCode());
        this.updateById(company);
    }

    /**
     * 获取公司树列表
     *
     *
     * @date 2019/10/12
     */
    @Override
    public List<Map<String, Object>> queryCompTree() {
        List<Map<String, Object>> compMaps = this.listMaps(new QueryWrapper<EntCompany>()
                .select("company_id as nodeId", "name as nodeName",
                        "short_name as shortName", "parent_id as nodePid")
                .eq("status", StatusEnum.ENABLE.getCode()));

        //装配根节点
        Map<String, Object> rootNode = MapBuilder.create(new HashMap<String, Object>())
                .put("nodeId", ROOT_ID)
                .put("nodeName", "顶级")
                .put("nodePid", ROOT_PID).build();
        compMaps.add(rootNode);
        return compMaps;
    }

    /**
     * 保存公司对应的角色
     *
     * @param params
     *
     * @date 2019/10/28
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCompApp(List<EntCompApp> params) {
        params.forEach(entCompApp -> this.compAppService.save(entCompApp));
    }

    /**
     * 获取应用列表
     *
     *
     * @date 2019/10/29
     */
    @Override
    public List<Map<String, Object>> getAppList(Long companyId) {
        QueryWrapper<SysApp> entityWrapper = new QueryWrapper<>();
        entityWrapper = entityWrapper
                .eq("status", StatusEnum.ENABLE.getCode())
                .select("app_id as appId", "app_name as appName");
        List<Map<String, Object>> appSelect = this.sysAppService.listMaps(entityWrapper);
        List<Long> appIds = this.baseMapper.getCompAppIds(companyId);

        for (Map<String, Object> app : appSelect) {
            if (ToolUtil.isEmpty(appIds)){
                app.put("configFlag", "N");
            }
            Long appId = (Long) app.get("appId");
            if (appIds.contains(appId)) {
                app.put("configFlag", "Y");
            }else {
                app.put("configFlag", "N");
            }
        }
        return appSelect;
    }

    private Serializable getKey(EntCompanyParam param) {
        return param.getCompanyId();
    }

    private Page getPageContext() {
        return PageFactory.defaultPage();
    }

    private EntCompany getOldEntity(EntCompanyParam param) {
        return this.getById(getKey(param));
    }

    private EntCompany getEntity(EntCompanyParam param) {
        EntCompany entity = new EntCompany();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
