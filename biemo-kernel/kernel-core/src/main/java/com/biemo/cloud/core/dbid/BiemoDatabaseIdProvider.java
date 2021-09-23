package com.biemo.cloud.core.dbid;

import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库id选择器
 *
 *
 * @Date 2019/3/30 22:26
 */
public class BiemoDatabaseIdProvider implements org.apache.ibatis.mapping.DatabaseIdProvider {

    @Override
    public void setProperties(Properties p) {
    }

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        String url = dataSource.getConnection().getMetaData().getURL();

        if (url.contains("oracle")) {
            return DBID.ORACLE;
        } else if (url.contains("postgresql")) {
            return DBID.PG_SQL;
        } else if (url.contains("sqlserver")) {
            return DBID.MS_SQL;
        } else {
            return DBID.MYSQL;
        }
    }
}
