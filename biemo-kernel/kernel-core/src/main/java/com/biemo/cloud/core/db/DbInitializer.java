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
package com.biemo.cloud.core.db;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import com.biemo.cloud.core.data.SqlExe;
import com.biemo.cloud.core.util.ToolUtil;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.biemo.cloud.kernel.model.exception.enums.CoreExceptionEnum.INIT_TABLE_EMPTY_PARAMS;

/**
 * 数据库初始化，可初始化表，校验字段，校验表名是否存在等
 *
 *
 * @date 2018-07-29 22:05
 */
@Slf4j
@Getter
@Setter
public abstract class DbInitializer {

    /**
     * 如果为true，则数据库校验失败会抛出异常
     */
    private Boolean fieldValidatorExceptionFlag = true;

    public DbInitializer() {

    }

    public DbInitializer(Boolean fieldValidatorExceptionFlag) {
        this.fieldValidatorExceptionFlag = fieldValidatorExceptionFlag;
    }

    /**
     * 初始化数据库
     *
     *
     * @Date 2018/7/30 上午10:30
     */
    public void dbInit() {

        /**
         * 初始化表
         */
        initTable();

        /**
         * 校验实体和对应表结构是否有不一致的
         */
        fieldsValidate();
    }

    /**
     * 初始化表结构
     *
     *
     * @Date 2018/7/30 上午10:24
     */
    private void initTable() {

        //校验参数
        String tableName = this.getTableName();
        String tableInitSql = this.getTableInitSql();
        if (ToolUtil.isOneEmpty(tableName, tableInitSql)) {
            if (fieldValidatorExceptionFlag) {
                throw new ServiceException(INIT_TABLE_EMPTY_PARAMS);
            }
        }

        //列出数据库中所有的表
        List<Map<String, Object>> tables = SqlExe.selectList("SHOW TABLES");

        boolean haveSmsTableFlag = false;
        for (Map<String, Object> tableInfo : tables) {
            if (tableInfo.containsValue(tableName.toUpperCase()) || tableInfo.containsValue(tableName.toLowerCase())) {
                haveSmsTableFlag = true;
            }
        }

        //判断数据库中是否有这张表，如果没有就初始化
        if (!haveSmsTableFlag) {
            SqlExe.update(tableInitSql);
            log.info("初始化" + getTableName() + "成功！");
        }

    }

    /**
     * 校验实体和对应表结构是否有不一致的
     *
     *
     * @Date 2018/7/30 上午10:24
     */
    private void fieldsValidate() {

        //校验参数
        String sql = this.showColumnsSql();
        if (ToolUtil.isOneEmpty(sql)) {
            if (fieldValidatorExceptionFlag) {
                throw new ServiceException(INIT_TABLE_EMPTY_PARAMS);
            }
        }

        //检查数据库中的字段，是否和实体字段一致
        List<Map<String, Object>> tableFields = SqlExe.selectList(sql);
        if (tableFields != null && !tableFields.isEmpty()) {

            //用于保存实体中不存在的字段的名称集合
            List<String> fieldsNotInClass = new ArrayList<>();

            //反射获取字段的所有字段名称
            List<String> classFields = this.getClassFields();
            for (Map<String, Object> tableField : tableFields) {
                String fieldName = (String) tableField.get("COLUMN_NAME");
                if (!classFields.contains(fieldName.toLowerCase())) {
                    fieldsNotInClass.add(fieldName);
                }
            }

            //如果集合不为空，代表有实体和数据库不一致的数据
            if (!fieldsNotInClass.isEmpty()) {
                log.error("实体中和数据库字段不一致的字段如下：" + JSON.toJSONString(fieldsNotInClass));
                if (fieldValidatorExceptionFlag) {
                    throw new ServiceException(CoreExceptionEnum.FIELD_VALIDATE_ERROR);
                }
            }
        }
    }

    /**
     * 反射获取类的所有字段
     *
     *
     * @Date 2018/7/30 上午10:06
     */
    private List<String> getClassFields() {
        Class<?> entityClass = this.getEntityClass();
        Field[] declaredFields = ClassUtil.getDeclaredFields(entityClass);
        ArrayList<String> filedNamesUnderlineCase = new ArrayList<>();
        for (Field declaredField : declaredFields) {
            String fieldName = StrUtil.toUnderlineCase(declaredField.getName());
            filedNamesUnderlineCase.add(fieldName);
        }
        return filedNamesUnderlineCase;
    }

    /**
     * 获取表的字段
     *
     * @author makesoft
     * @Date 2018/7/29 22:49
     */
    private String showColumnsSql() {
        return "SHOW COLUMNS FROM " + this.getTableName();
    }

    /**
     * 获取表的初始化语句
     *
     * @author makesoft
     * @Date 2018/7/29 22:10
     */
    protected abstract String getTableInitSql();

    /**
     * 获取表的名称
     *
     * @author makesoft
     * @Date 2018/7/29 22:10
     */
    protected abstract String getTableName();

    /**
     * 获取表对应的实体
     *
     * @author makesoft
     * @Date 2018/7/29 22:49
     */
    protected abstract Class<?> getEntityClass();
}
