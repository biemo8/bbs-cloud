package com.biemo.cloud.system.modular.mgr.service;

import com.biemo.cloud.system.modular.mgr.model.InitManagerParam;

/**
 * 初始化默认管理员服务
 *
 *
 * @Date 2019-09-27 15:54
 */
public interface InitManagerService {

    /**
     * 初始化系统管理员账号
     *
     *
     * @Date 2019-09-27 15:54
     */
    void initSystemManagerAccount();

    /**
     * 初始化管理员账号
     *
     *
     * @Date 2018/3/1 15:54
     */
    void initSystemAccount(InitManagerParam initManagerParam);

}
