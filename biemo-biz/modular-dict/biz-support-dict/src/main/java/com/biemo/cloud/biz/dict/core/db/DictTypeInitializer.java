package com.biemo.cloud.biz.dict.core.db;


import com.biemo.cloud.biz.dict.api.entity.DictType;
import com.biemo.cloud.core.db.DbInitializer;

/**
 * 字典类型表的初始化程序
 *
 *
 * @date 2018-07-30-上午9:29
 */
public class DictTypeInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `sys_dict_type` (\n" +
                "  `DICT_TYPE_ID` bigint(20) NOT NULL COMMENT '字典类型id',\n" +
                "  `DICT_TYPE_CLASS` smallint(1) DEFAULT NULL COMMENT '类型1：业务类型2：系统类型',\n" +
                "  `DICT_TYPE_CODE` varchar(255) NOT NULL COMMENT '字典类型编码',\n" +
                "  `DICT_TYPE_NAME` varchar(255) NOT NULL COMMENT '字典类型名称',\n" +
                "  `DICT_TYPE_DESC` varchar(1000) DEFAULT NULL COMMENT '字典描述',\n" +
                "  `STATUS` smallint(11) NOT NULL DEFAULT '1' COMMENT '状态1：启用2：禁用',\n" +
                "  `APP_CODE` varchar(32) DEFAULT NULL COMMENT '应用编码',\n" +
                "  `CREATE_TIME` datetime DEFAULT NULL COMMENT '添加时间',\n" +
                "  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',\n" +
                "  PRIMARY KEY (`DICT_TYPE_ID`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表'";
    }

    @Override
    public String getTableName() {
        return "sys_dict_type";
    }

    @Override
    public Class<?> getEntityClass() {
        return DictType.class;
    }
}
