package com.biemo.cloud.system.modular.ent.service;

import com.biemo.cloud.system.modular.ent.entity.EntCompApp;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.biemo.cloud.kernel.model.page.PageResult;
import com.biemo.cloud.system.modular.ent.entity.EntCompany;
import com.biemo.cloud.system.modular.ent.model.params.EntCompanyParam;
import com.biemo.cloud.system.modular.ent.model.result.EntCompanyResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业信息 服务类
 * </p>
 *
 *
 * @since 2019-10-10
 */
public interface EntCompanyService extends IService<EntCompany> {

    /**
     * 新增
     *
     *
     * @Date 2019-10-10
     */
    void add(EntCompanyParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-10-10
     */
    void delete(EntCompanyParam param);

    /**
     * 更新
     *
     *
     * @Date 2019-10-10
     */
    void update(EntCompanyParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-10-10
     */
    PageResult<EntCompanyResult> findPageBySpec(EntCompanyParam param);

    /**
     * 获取公司详情
     *
     *
     * @date 2019/10/12
     * @return
     */
    EntCompany detail(Long companyId);

    /**
     * 启用禁用
     *
     *
     * @date 2019/10/12
     */
    void changeStatus(Long companyId, StatusEnum statusEnum);

    /**
     * 获取公司树列表
     *
     *
     * @date 2019/10/12
     */
    List<Map<String,Object>> queryCompTree();

    /**
     * 保存公司配置的应用
     *
     *
     * @date 2019/10/28
     * @param params
     */
    void saveCompApp(List<EntCompApp> params);

    /**
     * 获取应用列表
     *
     *
     * @date 2019/10/29
     */
    List<Map<String,Object>> getAppList(Long companyId);
}
