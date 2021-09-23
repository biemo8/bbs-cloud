/**
 * Copyright  2018-2020 &   (admin@makesoft.cn)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.biemo.cloud.kernel.model.enums;

import lombok.Getter;

/**
 * 启用禁用标识
 *
 *
 * @Date 2018/7/24 下午5:31
 */
@Getter
public enum StatusEnum {

    ENABLE(1, "启用"),

    DISABLE(2, "禁用");

    private Integer code;
    private String desc;

    StatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getNameByCode(Integer code) {
        if (code == null) {
            return "";
        } else {
            for (StatusEnum enumItem : StatusEnum.values()) {
                if (enumItem.getCode().equals(code)) {
                    return enumItem.getDesc();
                }
            }
            return "";
        }
    }

    public static StatusEnum toEnum(Integer code) {
        if (null == code) {
            return null;
        } else {
            for (StatusEnum e : StatusEnum.values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
            return null;
        }
    }

}
