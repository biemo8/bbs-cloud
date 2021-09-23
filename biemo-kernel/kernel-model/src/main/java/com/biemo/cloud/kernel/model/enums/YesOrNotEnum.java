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
 * 是或者否的枚举
 *
 * @author makesoft
 * @Date 2018/4/18 23:05
 */
@Getter
public enum YesOrNotEnum {

    Y(true, "是", 1),

    N(false, "否", 0);

    private Boolean flag;
    private String desc;
    private Integer code;

    YesOrNotEnum(Boolean flag, String desc, Integer code) {
        this.flag = flag;
        this.desc = desc;
        this.code = code;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (YesOrNotEnum s : YesOrNotEnum.values()) {
                if (s.getCode().equals(status)) {
                    return s.getDesc();
                }
            }
            return "";
        }
    }

}
