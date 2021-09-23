package com.biemo.cloud.system.core.enums;

/**
 * 管理员角色的枚举
 *
 *
 * @Date 2018年1月23日 15:20:33
 */
public enum MgrEnum {

    /**
     * 管理员角色的类型
     */
    MANAGER("1", "管理角色"),
    BUSSINESS("2", "业务角色");

    private String code;
    private String name;

    MgrEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return "";
        } else {
            for (MgrEnum enumItem : MgrEnum.values()) {
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
