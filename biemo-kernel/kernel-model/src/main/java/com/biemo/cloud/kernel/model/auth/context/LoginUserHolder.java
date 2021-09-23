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
package com.biemo.cloud.kernel.model.auth.context;

import com.biemo.cloud.kernel.model.auth.AbstractLoginUser;

/**
 * <pre>
 * 当前登录用户的临时保存容器
 *
 *  说明：
 *     当OPEN_UP_FLAG标识在ThreadLocal里为true
 * </pre>
 *
 *
 * @Date 2018/7/3 下午5:29
 */
public class LoginUserHolder {

    private static final ThreadLocal<Boolean> OPEN_UP_FLAG = new ThreadLocal<>();
    private static final ThreadLocal<AbstractLoginUser> LONGIN_USER_HOLDER = new ThreadLocal<>();

    /**
     * 初始化
     *
     *
     * @Date 2018/7/4 下午12:31
     */
    public static void init() {
        OPEN_UP_FLAG.set(true);
    }

    /**
     * 这个方法如果OPEN_UP_FLAG标识没开启，则会set失效
     *
     *
     * @Date 2018/7/4 上午11:09
     */
    public static void set(AbstractLoginUser abstractLoginUser) {
        Boolean openUpFlag = OPEN_UP_FLAG.get();
        if (openUpFlag == null || openUpFlag.equals(false)) {
            return;
        } else {
            LONGIN_USER_HOLDER.set(abstractLoginUser);
        }
    }

    /**
     * 这个方法如果OPEN_UP_FLAG标识没开启，则会get值为null
     *
     *
     * @Date 2018/7/4 上午11:09
     */
    public static AbstractLoginUser get() {
        Boolean openUpFlag = OPEN_UP_FLAG.get();
        if (openUpFlag == null || openUpFlag.equals(false)) {
            return null;
        } else {
            return LONGIN_USER_HOLDER.get();
        }
    }

    /**
     * 删除保存的用户
     *
     *
     * @Date 2018/7/24 下午3:28
     */
    public static void remove() {
        OPEN_UP_FLAG.remove();
        LONGIN_USER_HOLDER.remove();
    }
}
