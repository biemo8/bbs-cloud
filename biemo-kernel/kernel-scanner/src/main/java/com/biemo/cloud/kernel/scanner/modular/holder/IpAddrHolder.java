package com.biemo.cloud.kernel.scanner.modular.holder;

/**
 * IP地址的临时存储 用在资源扫描
 *
 *
 * @Date 2019/9/20 10:17
 */
public class IpAddrHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置ip地址
     *
     * @param ip ip地址
     */
    public static void set(String ip) {
        contextHolder.set(ip);
    }

    /**
     * 获取ip地址
     */
    public static String get() {
        return contextHolder.get();
    }

    /**
     * 清除ip地址
     */
    public static void clear() {
        contextHolder.remove();
    }
}
