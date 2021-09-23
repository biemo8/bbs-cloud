package com.biemo.cloud.biz.file.core.db;


import com.biemo.cloud.biz.file.api.entity.Fileinfo;
import com.biemo.cloud.core.db.DbInitializer;

/**
 * 文件信息表的初始化程序
 *
 *
 * @date 2018-07-30-上午9:29
 */
public class FileInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `sys_fileinfo` (\n" +
                "  `FILE_ID` char(32) NOT NULL COMMENT '文件ID',\n" +
                "  `APP_CODE` varchar(255) DEFAULT NULL COMMENT '应用编码',\n" +
                "  `FILE_URL` varchar(255) DEFAULT NULL COMMENT '文件路径',\n" +
                "  `FILE_ORIGIN_NAME` varchar(255) DEFAULT NULL COMMENT '文件名称',\n" +
                "  `FILE_SUFFIX` varchar(64) DEFAULT NULL COMMENT '文件后缀',\n" +
                "  `FILE_SIZE` decimal(10,0) DEFAULT NULL COMMENT '文件大小',\n" +
                "  `FILE_STORAGE_NAME` varchar(512) DEFAULT NULL COMMENT '文件唯一名称',\n" +
                "  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`FILE_ID`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='文件信息表';";
    }

    @Override
    public String getTableName() {
        return "sys_fileinfo";
    }

    @Override
    public Class<?> getEntityClass() {
        return Fileinfo.class;
    }
}
