package com.biemo.cloud.system.core.enums;

/**
 * 角色类型枚举类
 *
 *
 * @Date 2019/9/26 18:53
 */
public enum RoleTypeEnum {

    /**
     * 角色的类型
     */
    PLATFORM_MANAGEMENT("1", "平台"),
    BUSINESS_MANAGEMENT("2", "企业"),
    BUSINESS_BUSINESS("3", "业务"),
    PROJECT_BUSINESS("4", "项目");

    private String code;
    private String name;

    RoleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return "";
        } else {
            for (RoleTypeEnum enumItem : RoleTypeEnum.values()) {
                if (enumItem.getCode().equals(code)) {
                    return enumItem.getName();
                }
            }
            return "";
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
