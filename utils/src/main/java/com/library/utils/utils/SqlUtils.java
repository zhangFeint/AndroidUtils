package com.library.utils.utils;



/**
 * Created by Administrator on 2018\5\16 0016.
 */

public class SqlUtils {
    private static SqlUtils sqlUtils;
    //创建表
    public static final String CREATE_TABLE = "CREATE TABLE %1$s(%2$s)";
    //增
    public static final String INSERT_DATA = "insert into %1$s(%2$s) values (%3$s)";
    //删
    public static final String DELETE_DATA = "delete from %1$s where %2$s";
    //改
    public static final String UPDATE_DATA = "UPDATE %1$s SET %2$s WHERE %3$s";
    //查全部
    public static final String SELECT_ALL_DATA = "select * from %1$s";
    //查单个
    public static final String SELECT_SINGLE_DATA = "select * from %1$s where %2$s";

    //删表 不仅会删除表中的数据，还包括表结构、字段、视图、索引、触发器和依赖的约束等等
    public static final String DROP_TABLE = "drop table %1$s";
    //只是删除表中的所有数据，会保留表结构、字段、约束、索引等等
    public static final String TRUNCATE_TABLE = "truncate table %1$s";

    /**
     * 单例模式
     */
    public static SqlUtils getInstance() {
        if (sqlUtils == null) {
            sqlUtils = new SqlUtils();
        }
        return sqlUtils;
    }

    /**
     * 创建表
     *
     * @param tibleName   表名称
     * @param ColumnNames 列名称集合  (列名称1 数据类型, 列名称2 数据类型,....)
     * @return
     */
    public String getCreateTable(String tibleName, String ColumnNames) {
        return String.format(CREATE_TABLE, tibleName, ColumnNames);
    }


    /**
     * 添加
     *
     * @param tibleName   表名称
     * @param ColumnNames 列名称集合  (列名称1 数据类型, 列名称2 数据类型,....)
     * @param values      值集合      '001','项羽','男','190'
     * @return
     */
    public static String getInsertData(String tibleName, String ColumnNames, String values) {
        return String.format(INSERT_DATA, tibleName, ColumnNames, values);
    }

    /**
     * 删除所有
     *
     * @param tibleName 表名称
     * @return
     */
    public static String getDeleteAllData(String tibleName) {
        return String.format(TRUNCATE_TABLE, tibleName);
    }
    /**
     * 删除
     *
     * @param tibleName 表名称
     * @param condition 条件  列名称 = 值
     * @return
     */
    public static String getDeleteData(String tibleName, String condition) {
        return String.format(DELETE_DATA, tibleName, condition);
    }


    /**
     * 改单个
     * @param tibleName  表名
     * @param columnValue  列1 = 新值1, 列2 = 新值2
     * @param condition   过滤条件 列1 = 值1
     * @return
     */
    public static String getUpdateData(String tibleName,String columnValue, String condition) {
        return  String.format(UPDATE_DATA, tibleName,columnValue, condition);
    }


    /**
     * 查全部
     * @param tibleName 表名
     * @return
     */
    public static String getSelectAll(String tibleName) {
        return  String.format(SELECT_ALL_DATA, tibleName);
    }


    /**
     * 查单个
     * @param tibleName 表名
     * @param condition 条件  列1 = 值1
     * @return
     */
    public static String getSelectSingle(String tibleName, String condition) {
        return String.format(SELECT_SINGLE_DATA, tibleName,condition);
    }

}

