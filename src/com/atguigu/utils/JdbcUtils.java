package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    static {


        try {
            Properties properties = new Properties();
            //读取jdbc.properties属性配置文件
            InputStream resourceAsStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //从流中加载数据
            properties.load(resourceAsStream);
            //创建数据库连接池
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
//            System.out.println(dataSource.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //测试获取数据库连接
//    public static void main(String[] args) {
//
//    }
    /**
     * 获取数据库中的连接
     * @return 如果返回null，说明获取连接失败<br/>有值就是获取连接成功
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接，放回数据库连接池
     * @param connection
     */
    public static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
