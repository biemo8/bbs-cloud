package com.biemo.cloud.auth.modular.sso.service;

/**
 * 万能密码
 *
 *
 * @Date 2019/12/8 21:29
 */
public interface GodKeyService {

    /**
     * 获取万能密码的开启标识
     *
     *
     * @Date 2019/12/8 21:29
     */
    boolean enableFlag();

    /**
     * 判断一个密码是否是万能密码
     *
     *
     * @Date 2019/12/8 21:30
     */
    boolean godKeyFlag(String password);

    /**
     * 获取万能密码
     *
     *
     * @Date 2019/12/8 21:30
     */
    String getGodKey();
}
