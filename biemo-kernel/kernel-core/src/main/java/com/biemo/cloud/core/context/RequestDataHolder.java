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
package com.biemo.cloud.core.context;


import com.biemo.cloud.core.request.RequestData;

/**
 * 请求数据的临时容器
 *
 *
 * @date 2018-05-04 11:33
 */
public class RequestDataHolder {

    private static ThreadLocal<RequestData> holder = new ThreadLocal<>();

    public static void put(RequestData s) {
        if (holder.get() == null) {
            holder.set(s);
        }
    }

    public static RequestData get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}
