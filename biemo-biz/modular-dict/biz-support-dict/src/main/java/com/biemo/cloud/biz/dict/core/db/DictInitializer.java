package com.biemo.cloud.biz.dict.core.db;


import com.biemo.cloud.biz.dict.api.entity.Dict;
import com.biemo.cloud.core.db.DbInitializer;

/**
 * 字典表的初始化程序
 *
 *
 * @date 2018-07-30-上午9:29
 */
public class DictInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `sys_dict` (\n" +
                "  `DICT_ID` bigint(20) NOT NULL COMMENT '字典id',\n" +
                "  `DICT_TYPE_CODE` varchar(255) NOT NULL COMMENT '字典类型编码',\n" +
                "  `DICT_CODE` varchar(50) NOT NULL COMMENT '字典编码',\n" +
                "  `DICT_NAME` varchar(255) NOT NULL COMMENT '字典名称',\n" +
                "  `DICT_SHORT_NAME` varchar(255) DEFAULT NULL COMMENT '简称',\n" +
                "  `DICT_SHORT_CODE` varchar(255) DEFAULT NULL COMMENT '字典简拼',\n" +
                "  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级代码id',\n" +
                "  `STATUS` smallint(6) NOT NULL COMMENT '状态(1:启用,2:禁用)',\n" +
                "  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`DICT_ID`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础字典'";
    }

    @Override
    public String getTableName() {
        return "sys_dict";
    }

    @Override
    public Class<?> getEntityClass() {
        return Dict.class;
    }
}
