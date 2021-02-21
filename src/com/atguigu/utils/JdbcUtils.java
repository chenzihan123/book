package com.atguigu.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    private static ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<Connection>();
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
        Connection connection = connectionThreadLocal.get();
        if (connection == null){
            try {
                //从数据库连接池中获取连接
                connection = dataSource.getConnection();
                //保存到ThreadLocal对象中，供后面的jdbc使用
                connectionThreadLocal.set(connection);
                //设置为手动管理事务
                connection.setAutoCommit(false);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * 提交事务并释放连接
     */
    public static void commitAndClose(){
        Connection connection = connectionThreadLocal.get();
        if (connection != null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.commit();//提交事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接，释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        connectionThreadLocal.remove();
    }

    /**
     * 回滚事务并释放连接
     */
    public static void rollbackAndClose(){
        Connection connection = connectionThreadLocal.get();
        if (connection != null){//如果不等于null，说明之前使用过连接，操作过数据库
            try {
                connection.rollback();//回滚事务
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    connection.close();//关闭连接，释放资源
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要执行remove操作，否则就会出错。（因为Tomcat服务器底层使用了线程池技术）
        connectionThreadLocal.remove();
    }

    /**
     * 关闭连接，放回数据库连接池
     * @param connection

    public static void closeConnection(Connection connection){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    */
}
