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
package com.biemo.cloud.core.util;

import com.biemo.cloud.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql语句工具类
 *
 *
 * @date 2016年12月6日 下午1:01:54
 */
@Slf4j
public class SqlUtil {

    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符，防止注入绕过
     */
    public static String escapeOrderBySql(String value)
    {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value))
        {
            throw new BaseException("参数不符合规范，不能进行查询");
        }
        return value;
    }

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value)
    {
        return value.matches(SQL_PATTERN);
    }

    /**
     * 根据集合的大小，输出相应个数"?"
     *
     *
     */
    public static String parse(List<?> list) {
        String str = "";
        if (list != null && list.size() > 0) {
            str = str + "?";
            for (int i = 1; i < list.size(); i++) {
                str = str + ",?";
            }
        }
        return str;
    }

    /**
     * 结果集转化为map
     *
     *
     * @Date 2020/1/29 6:12 下午
     */
    public static Map<String, Object> resultSet2Map(ResultSet resultSet) {
        try {
            HashMap<String, Object> result = new HashMap<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = resultSet.getObject(i);
                result.put(columnName, columnValue);
            }
            return result;
        } catch (SQLException e) {

            log.error("转化结果集错误！", e);

            //返回空map
            return new HashMap<>();
        }
    }

    /**
     * 结果集转化为map
     *
     *
     * @Date 2020/1/29 6:12 下午
     */
    public static List<Map<String, Object>> resultSet2ListMap(ResultSet resultSet) {

        ArrayList<Map<String, Object>> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Map<String, Object> map = resultSet2Map(resultSet);
                result.add(map);
            }
            return result;
        } catch (SQLException e) {

            log.error("转化结果集错误！", e);

            //返回空map
            return result;
        }
    }

}
