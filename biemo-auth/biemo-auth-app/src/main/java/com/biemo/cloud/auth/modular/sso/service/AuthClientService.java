package com.biemo.cloud.auth.modular.sso.service;

import com.biemo.cloud.auth.modular.sso.entity.AuthClient;
import com.biemo.cloud.auth.modular.sso.model.params.AuthClientParam;
import com.biemo.cloud.auth.modular.sso.model.result.AuthClientResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 应用表 服务类
 *
 *
 * @Date 2019/9/25 22:32
 */
public interface AuthClientService extends IService<AuthClient> {

    /**
     * 获取应用下拉列表
     *
     *
     * @Date 2019/12/3 22:24
     */
    List<Map<String, Object>> getAppSelectList();

    /**
     * 获取客户端分页列表
     *
     *
     * @Date 2019/12/3 22:24
     */
    Page<Map<String, Object>> pageList(AuthClientParam authClientParam);

    /**
     * 新增
     *
     *
     * @Date 2019/12/3 22:24
     */
    void add(AuthClientParam param);

    /**
     * 更新
     *
     *
     * @Date 2019/12/3 22:24
     */
    void update(AuthClientParam param);

    /**
     * 删除客户端
     *
     *
     * @Date 2019/12/3 22:24
     */
    void delete(Long clientId);

    /**
     * 详情
     *
     *
     * @Date 2019/12/3 22:24
     */
    AuthClientResult detail(Long clientId);


    /**
     * 生成随机密钥
     *
     *
     * @Date 2019/12/3 22:25
     */
    String generateKey();

}
