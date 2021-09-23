package com.biemo.cloud.biz.dict.core.enums;

import lombok.Getter;

/**
 * 数据字典类型
 *
 * @author wangzhongqiang
 * @date 2018/4/19 15:49
 */
@Getter
public enum DictTypeEnum {

    BUSINESS(1, "业务类"), SYSTEM(2, "系统类"), BASIS(3, "基础类");

    private Integer code;

    private String name;

    DictTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
