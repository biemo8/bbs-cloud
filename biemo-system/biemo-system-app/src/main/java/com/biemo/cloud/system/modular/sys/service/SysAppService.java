package com.biemo.cloud.system.modular.sys.service;

import com.biemo.cloud.system.modular.sys.entity.SysApp;
import com.biemo.cloud.system.modular.sys.model.params.SysAppParam;
import com.biemo.cloud.kernel.model.enums.StatusEnum;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用管理 服务类
 * </p>
 *
 *
 * @since 2019-09-10
 */
public interface SysAppService extends IService<SysApp> {

    /**
     * 新增
     *
     *
     * @Date 2019-09-10
     */
    void add(SysAppParam param);

    /**
     * 删除
     *
     *
     * @Date 2019-09-10
     */
    void delete(Long appId);

    /**
     * 更新
     *
     *
     * @Date 2019-09-10
     */
    void update(SysAppParam param);

    /**
     * 查询分页数据，Specification模式
     *
     *
     * @Date 2019-09-10
     */
    Page<Map<String, Object>> findPageBySpec(String appName);

    /**
     * 详情
     *
     *
     * @Date 2019/9/26 23:03
     */
    Map<String, Object> detail(Long appId);

    /**
     * 启用禁用
     *
     *
     * @Date 2019/9/26 23:03
     */
    void changeStatus(Long appId, StatusEnum statusEnum);

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/9/26 23:03
     */
    List<Map<String, Object>> getAppSelect();

    /**
     * getAppByCode 通过编码获取app
     *
     *
     * @Date 2018/3/1 18:06
     */
    SysApp getAppByCode(String appCode);

    /**
     * 根据当前登录人获取应用下拉列表
     *
     *
     * @date 2019/11/26
     * @return
     */
    List<SysAppParam> getAppSelectByCurrentUser();
}
