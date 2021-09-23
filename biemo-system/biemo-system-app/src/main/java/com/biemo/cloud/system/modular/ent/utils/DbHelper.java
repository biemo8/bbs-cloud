package com.biemo.cloud.system.modular.ent.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据库连接辅助类
 *
 *
 * @date 2019/10/16
 */
@Component
public class DbHelper {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String url;
    private static String username;
    private static String password;
    private static Connection conn = null;

    @Value("${spring.datasource.url}")
    public void setUrl(String url) {
        DbHelper.url = url;
    }

    @Value("${spring.datasource.username}")
    public void setUsername(String username) {
        DbHelper.username = username;
    }

    @Value("${spring.datasource.password}")
    public void setPassword(String password) {
        DbHelper.password = password;
    }

    //静态代码块负责加载驱动
    static {
        try {
            Class.forName(DRIVER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 单例模式返回数据库连接对象，供外部调用
     */
    public static Connection getConnection() throws Exception {
        if (conn == null) {
            conn = DriverManager.getConnection(url, username, password);
            return conn;
        }
        return conn;
    }
}
