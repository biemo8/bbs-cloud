package com.biemo.cloud.auth.modular.sso.enums;

/**
 * 两种单点登录类型
 *
 *
 * @Date 2019/9/25 22:27
 */
public enum LoginTypeEnum {

    IBS(1, "自定义页面登录"),
    SPS(2, "统一页面登录");

    private int code;
    private String name;

    LoginTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        for (LoginTypeEnum enumItem : LoginTypeEnum.values()) {
            if (enumItem.getCode() == code) {
                return enumItem.getName();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
