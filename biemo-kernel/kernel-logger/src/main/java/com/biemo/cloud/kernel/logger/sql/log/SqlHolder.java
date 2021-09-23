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
package com.biemo.cloud.kernel.logger.sql.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行过程sql存放器
 *
 *
 * @Date 2018/7/3 下午1:23
 */
public class SqlHolder {

    private static final ThreadLocal<List<String>> SQL_INFO_HOLDER = new ThreadLocal<>();

    public static void init() {
        SQL_INFO_HOLDER.set(new ArrayList<>());
    }

    public static void addSqlInfo(String sql) {
        List<String> strings = SQL_INFO_HOLDER.get();
        if (strings == null) {
            return;
        } else {
            strings.add(sql);
            SQL_INFO_HOLDER.set(strings);
        }
    }

    public static List<String> getSqlInfos() {
        return SQL_INFO_HOLDER.get();
    }

    public static String getSqlInfoStrings() {
        List<String> strings = SQL_INFO_HOLDER.get();
        StringBuilder stringBuffer = new StringBuilder();
        if (strings != null && !strings.isEmpty()) {
            for (String string : strings) {
                stringBuffer.append(string).append("<br/>");
            }
        }
        return stringBuffer.toString();
    }

    public static void cleanTempSqlInfos() {
        SQL_INFO_HOLDER.remove();
    }

}
