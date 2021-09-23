package com.biemo.cloud.biz.log.core.db;


import com.biemo.cloud.biz.log.api.entity.CommonLog;
import com.biemo.cloud.core.db.DbInitializer;

/**
 * 通用日志的初始化
 *
 *
 * @date 2018-07-30-上午9:29
 */
public class CommonLogInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `log_common_log` (\n" +
                "  `ID` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `APP_CODE` varchar(32) DEFAULT NULL COMMENT '应用编码',\n" +
                "  `LEVEL` varchar(255) DEFAULT NULL COMMENT '日志级别 info，error，warn，debug',\n" +
                "  `CLASS_NAME` varchar(255) DEFAULT NULL COMMENT '类名',\n" +
                "  `METHOD_NAME` varchar(255) DEFAULT NULL COMMENT '打日志的方法的名称',\n" +
                "  `IP` varchar(25) DEFAULT NULL COMMENT '远程访问IP地址',\n" +
                "  `ACCOUNT_ID` bigint(20) DEFAULT NULL COMMENT '用户账号id',\n" +
                "  `REQUEST_NO` varchar(32) DEFAULT NULL COMMENT '日志号',\n" +
                "  `URL` varchar(200) DEFAULT NULL COMMENT '请求地址 ',\n" +
                "  `REQUEST_DATA` text COMMENT '请求的数据内容',\n" +
                "  `LOG_CONTENT` text COMMENT '日志详情',\n" +
                "  `CREATE_TIMESTAMP` bigint(20) DEFAULT NULL COMMENT '创建时间',\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  KEY `_APP_CODE` (`APP_CODE`) USING BTREE,\n" +
                "  KEY `_LEVEL` (`LEVEL`) USING BTREE,\n" +
                "  KEY `_REQUEST_NO` (`REQUEST_NO`) USING BTREE,\n" +
                "  KEY `_CREATE_TIME` (`CREATE_TIMESTAMP`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    @Override
    public String getTableName() {
        return "log_common_log";
    }

    @Override
    public Class<?> getEntityClass() {
        return CommonLog.class;
    }
}
