package com.biemo.cloud.system.modular.mgr.flag;

/**
 * 初始化标记，防止初始化多次
 *
 *
 * @date 2019-09-27-17:23
 */
public class InitManagerFlag {

    private static Boolean INIT_MANAGER_FLAG = false;

    public static synchronized Boolean getFlag() {
        return INIT_MANAGER_FLAG;
    }

    public static synchronized void setFlag() {
        INIT_MANAGER_FLAG = true;
    }

}
