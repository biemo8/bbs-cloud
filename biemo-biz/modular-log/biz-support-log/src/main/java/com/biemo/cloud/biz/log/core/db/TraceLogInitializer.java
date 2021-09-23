package com.biemo.cloud.biz.log.core.db;


import com.biemo.cloud.biz.log.api.entity.TraceLog;
import com.biemo.cloud.core.db.DbInitializer;

/**
 * 调用链日志的初始化
 *
 *
 * @date 2018-07-30-上午9:29
 */
public class TraceLogInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `log_trace_log` (\n" +
                "  `ID` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `APP_CODE` varchar(100) DEFAULT NULL COMMENT '应用编码',\n" +
                "  `IP` varchar(25) DEFAULT NULL COMMENT 'ip地址',\n" +
                "  `SERVLET_PATH` varchar(200) DEFAULT NULL COMMENT '请求路径',\n" +
                "  `RPC_PHASE` varchar(10) DEFAULT NULL COMMENT 'rpc调用类型，\\n    G1,     //网关发送请求\\n\\n    G2,     //接收网关请求（切controller）\\n\\n    P1,     //调用端发送请求（切consumer）\\n\\n    P2,     //被调用端接收到请求（切provider）\\n\\n    P3,     //被调用端发送响应成功\\n\\n    P4,     //调用端接收到响应成功\\n\\n    EP3,    //被调用端发送响应失败\\n\\n    EP4,    //调用端接收到响应失败\\n\\n    G3,     //控制器响应网关成功\\n\\n    G4,     //网关接收到成功请求\\n\\n    EG3,    //控制器接收到错误响应\\n\\n    EG4,    //网关接收到错误响应',\n" +
                "  `TRACE_ID` varchar(100) DEFAULT NULL COMMENT '唯一请求号',\n" +
                "  `SPAN_ID` varchar(100) DEFAULT NULL COMMENT '节点id',\n" +
                "  `PARENT_SPAN_ID` varchar(100) DEFAULT NULL COMMENT '节点父id',\n" +
                "  `CONTENT` text COMMENT '日志内容',\n" +
                "  `CREATE_TIMESTAMP` bigint(20) DEFAULT NULL COMMENT '生成时间戳',\n" +
                "  PRIMARY KEY (`ID`),\n" +
                "  KEY `_APP_CODE` (`APP_CODE`) USING BTREE,\n" +
                "  KEY `_RPC_PHASE` (`RPC_PHASE`) USING BTREE,\n" +
                "  KEY `_CREATE_TIME` (`CREATE_TIMESTAMP`) USING BTREE,\n" +
                "  KEY `_TRACE_ID` (`TRACE_ID`) USING BTREE\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    @Override
    public String getTableName() {
        return "log_trace_log";
    }

    @Override
    public Class<?> getEntityClass() {
        return TraceLog.class;
    }
}
