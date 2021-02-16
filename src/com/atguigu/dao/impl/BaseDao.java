package com.atguigu.dao.impl;

import com.atguigu.utils.JdbcUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
    //使用DbUtils操作数据库
    private QueryRunner queryRunner = new QueryRunner();

    /**
     * update()方法执行：Inster\Update\Delete语句
     * @return 如果返回-1，说明执行失败<br/>返回其他影响的行数
     */
    public int update(String sql,Object ...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.update(connection,sql,args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(connection);
        }
        return -1;
    }

    /**
     * 查询返回一个javaBean的sql语句
     * @param tClass    返沪的数据类型
     * @param sql       执行的sql语句
     * @param args      sql对应的参数值
     * @param <T>       返回的数据类型的泛型
     * @return 如果返回-1，说明执行失败<br/>返回其他影响的行数
     */
    public <T> T queryForOne(Class<T> tClass,String sql,Object ...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection, sql, new BeanHandler<T>(tClass),args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    /**
     * 查询返回多个javaBean的sql语句
     * @param tClass  返沪的数据类型
     * @param sql 执行的sql语句
     * @param args sql对应的参数值
     * @param <T> 返回的数据类型的泛型
     * @return
     */
    public <T> List<T> queryForList(Class<T> tClass,String sql, Object ...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new BeanListHandler<T>(tClass),args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    /**
     * 执行返回一行一列的sql语句
     * @param sql 执行的sql语句
     * @param args sql对应的参数值
     * @return
     */
    public Object queryForSingleValue(String sql,Object ...args){
        Connection connection = JdbcUtils.getConnection();
        try {
            return queryRunner.query(connection,sql,new ScalarHandler(),args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

}
