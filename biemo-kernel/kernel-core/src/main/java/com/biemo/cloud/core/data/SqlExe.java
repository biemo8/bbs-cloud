package com.biemo.cloud.core.data;

import cn.hutool.db.DbUtil;
import cn.hutool.db.handler.RsHandler;
import cn.hutool.db.sql.SqlExecutor;
import com.biemo.cloud.core.util.SpringContextHolder;
import com.biemo.cloud.core.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sql操作工具
 *
 *
 * @Date 2019/12/29 16:37
 */
@Slf4j
public class SqlExe {

    /**
     * 获取一条数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static Map<String, Object> selectOne(DataSource dataSource, String sql, Object... params) {

        RsHandler<Map<String, Object>> rsHandler = SqlUtil::resultSet2Map;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.query(conn, sql, rsHandler, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return new HashMap<>();
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 获取一条数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static Map<String, Object> selectOne(String sql, Object... params) {
        DataSource dataSource = SpringContextHolder.getBean(DataSource.class);
        return selectOne(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static List<Map<String, Object>> selectList(DataSource dataSource, String sql, Object... params) {

        RsHandler<List<Map<String, Object>>> rsHandler = SqlUtil::resultSet2ListMap;

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.query(conn, sql, rsHandler, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return new ArrayList<>();
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 查询多条记录
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static List<Map<String, Object>> selectList(String sql, Object... params) {
        DataSource dataSource = SpringContextHolder.getBean(DataSource.class);
        return selectList(dataSource, sql, params);
    }

    /**
     * 更新数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int update(DataSource dataSource, String sql, Object... params) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            return SqlExecutor.execute(conn, sql, params);
        } catch (SQLException e) {
            log.error("sql执行错误!", e);
            return 0;
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 更新数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int update(String sql, Object... params) {
        DataSource dataSource = SpringContextHolder.getBean(DataSource.class);
        return update(dataSource, sql, params);
    }

    /**
     * 新增数据
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int insert(DataSource dataSource, String sql, Object... params) {
        return update(dataSource, sql, params);
    }

    /**
     * 新增数据
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int insert(String sql, Object... params) {
        DataSource dataSource = SpringContextHolder.getBean(DataSource.class);
        return insert(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param dataSource 数据源名称
     * @param sql        被执行的sql(sql中有参数用?代替)
     * @param params     sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int delete(DataSource dataSource, String sql, Object... params) {
        return update(dataSource, sql, params);
    }

    /**
     * 查询多条记录
     *
     * @param sql    被执行的sql(sql中有参数用?代替)
     * @param params sql执行时候的参数
     *
     * @Date 2019/12/29 16:37
     */
    public static int delete(String sql, Object... params) {
        DataSource dataSource = SpringContextHolder.getBean(DataSource.class);
        return delete(dataSource, sql, params);
    }

}
