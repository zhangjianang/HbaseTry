package com.company.pinBatchDao;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by adimn on 2017/5/19.
 */
public class BatchChangeDBUtils {

    private final static Logger LOG = LoggerFactory.getLogger(BatchChangeDBUtils.class);
    private  static DataSource dataSource = null ;
    static {
        createDbCache();
    }

    private static void createDbCache(){
        InputStream in = null;
        Connection connection = null;
        try {
            if(dataSource == null){
                Properties properties = new Properties();
                in = BatchChangeDBUtils.class.getResourceAsStream("/changesqldb.properties");
                properties.load(in);
//                properties.setProperty("url","jdbc:mysql://192.168.0.191:3306/prism");
//                properties.setProperty("username","root");
//                properties.setProperty("password","root");
                dataSource = BasicDataSourceFactory.createDataSource(properties);
                in.close();
                connection = dataSource.getConnection();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            releaseInputStream(in);
            releaseConnection(connection);
        }
    }

    /**
     * 执行查询操作
     *
     * @param sql
     *            查询SQL语句
     * @param args
     *            SQL语句 参数
     * @return 返回查询结果
     */
    public  List<Map<String, Object>> query(String sql, Object... args) {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement querySql = null;
        List<Map<String, Object>> lres=null;
        try {
            connection = dataSource.getConnection();
            querySql = connection.prepareStatement(sql);
//            PreparedStatement pst = null;
            ResultSet rs = null;

            // 设置参数
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null)
                    querySql.setObject(i + 1, args[i]);
            }
            // 执行查询语句
            rs = querySql.executeQuery();

            lres=new ArrayList<Map<String, Object>>();
            while(rs.next()){
                lres.add(rsToMap(rs));
            }
            return lres;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releasePreparedStatement(querySql);
            releaseResultSet(resultSet);
            releaseConnection(connection);
        }
        return null;
    }


    /**
     * 将结果集转换成Map
     *
     * @param rs
     *            结果集
     * @return map
     * @throws SQLException
     */
    public static Map<String, Object> rsToMap(ResultSet rs) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取结果集的元信息(列名，列类型，大小，列数量等等)
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();// 获取结果集中的列的数量
        String id = "";
        for (int i = 1; i <= count; i++) {
            // 根据列的下标获取列名称
            String columnName = rsmd.getColumnName(i);

            Object value = null;
            try {
                 value = rs.getObject(i);
            }catch (Exception e){
                if("updatetime".equalsIgnoreCase(columnName)){
                    String timeValue = rs.getString(i);
                    if(timeValue!=null&&timeValue.length()>19)
                    value = timeValue.substring(0,19);
                   // System.out.println(value);
                }else{
                    System.out.println(columnName);
                    e.printStackTrace();
                }
            }
            map.put(columnName, value);

        }
        // map.get("uname");
        return map;
    }

    public static void releaseResultSet(ResultSet resultSet){
        if(resultSet!=null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void releasePreparedStatement(PreparedStatement statement){
        if(statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void releaseConnection(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void releaseInputStream(InputStream in) {
        if (null != in) {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
