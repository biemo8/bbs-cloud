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
package com.biemo.cloud.kernel.model.request;

/**
 * 远程调用请求参数的holder
 *
 *
 * @Date 2019/5/14 22:59
 */
public class RmiRequestHolder {

    private static final ThreadLocal<AbstractBaseRequest> spanIdContext = new ThreadLocal<>();

    public static void set(AbstractBaseRequest spanId) {
        spanIdContext.set(spanId);
    }

    public static AbstractBaseRequest get() {
        return spanIdContext.get();
    }

    public static void remove() {
        spanIdContext.remove();
    }
}
