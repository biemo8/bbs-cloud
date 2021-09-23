package com.biemo.cloud.auth.modular.ssomgr.constants;

/**
 * 万能密码常量池
 *
 *
 * @Date 2019/12/7 18:12
 */
public interface GodKeyConst {


    /**
     * redis中存储的key
     */
    String GOD_KEY_TICKET = "GOD_KEY_TICKET";

    /**
     * 万能密码的刷新时间间隔
     */
    long REFRESH_INTERVAL = 120 * 1000;

    /**
     * 万能密码状态的键名称
     */
    String GOD_KEY_STATUS = "godKeyStatus";

    /**
     * 万能密码值的键名称
     */
    String GOD_KEY_VALUE = "godKeyValue";

    /**
     * 失效时间的键名称
     */
    String EXPIRE_TIME = "expireTime";

    /**
     * 万能密码启用
     */
    String ENABLE_GOD_KEY = "enable";

    /**
     * 万能密码禁用
     */
    String DISABLED_GOD_KEY = "disabled";
}
