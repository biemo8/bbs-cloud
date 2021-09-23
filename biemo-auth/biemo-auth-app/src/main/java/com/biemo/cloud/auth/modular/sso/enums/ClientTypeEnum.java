package com.biemo.cloud.auth.modular.sso.enums;

/**
 * 登良类型枚举
 *
 *
 * @Date 2019/12/4 21:07
 */
public enum ClientTypeEnum {

    IBS(1, "Web浏览器"),
    SPS(2, "手机客户端");

    private int code;
    private String name;

    ClientTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(int code) {
        for (ClientTypeEnum enumItem : ClientTypeEnum.values()) {
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
