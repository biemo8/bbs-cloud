package com.biemo.cloud.system.modular.ent.utils;

import com.biemo.cloud.core.util.ToolUtil;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 组织信息的通用工具类
 *
 *
 * @date 2019/10/11
 */
public class EntUtil {

    /**
     * 将反序的pids重新排序
     *
     *
     * @date 2019/10/12
     */
    public static String transToPids(String pids) {
        String formatPids = pids.substring(0, pids.lastIndexOf(','));
        String[] pidArray = formatPids.split(",");
        StringBuilder resultPids = new StringBuilder();
        for (int i = pidArray.length - 1; i >= 0; i--) {
            resultPids.append(pidArray[i]);
            if (i != 0) {
                resultPids.append(",");
            }
        }
        return resultPids.toString();
    }

    /**
     * 获取最大排序码通用方法
     *
     *
     * @date 2019/10/16
     */
    public static BigDecimal getMaxSort(Class<?> clazz) {
        TableName annotation = clazz.getAnnotation(TableName.class);
        String tableName = annotation.value();
        //默认排序间隔
        BigDecimal maxSort = new BigDecimal(1);
        return accessdb(tableName, maxSort);
    }

    /**
     * 数据库访问操作
     *
     *
     * @date 2019/10/16
     */
    private static BigDecimal accessdb(String tableName, BigDecimal maxSort) {
        // 数据库连接对象
        Connection conn = null;

        // PreparedStatement对象用来执行SQL语句
        PreparedStatement pst = null;

        //结果集
        ResultSet rs = null;

        try {
            conn = DbHelper.getConnection();

            pst = conn.prepareStatement("SELECT max(order_no) FROM " + tableName);

            rs = pst.executeQuery();

            if (ToolUtil.isNotEmpty(rs) && rs.next()) {
                maxSort = rs.getBigDecimal(1).add(maxSort);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connClose(pst, rs);
        }
        return maxSort;
    }

    /**
     * 连接关闭
     *
     *
     * @date 2019/10/16
     */
    private static void connClose(PreparedStatement pst, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            //注意：如果后续还要使用connection，则不用关闭
            /*if (conn != null)
                conn.close(); */
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
