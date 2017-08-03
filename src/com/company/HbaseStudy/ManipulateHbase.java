package com.company.HbaseStudy;

import com.company.MD5;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by adimn on 2017/5/27.
 */
public class ManipulateHbase {

    public static Configuration configuration;
    static {
        configuration = HBaseConfiguration.create();
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        configuration.set("hbase.zookeeper.quorum", "192.168.0.189:2181");
//        configuration.set("hbase.master", "192.168.0.191:60000");
//        configuration.set("hbase.rootdir", "hdfs://192.168.0.191:8020/hbase");

    }

    public static void main(String args[]){
//        SearchDetailInfo searchDetailInfo=new SearchDetailInfo();
//        System.out.println(searchDetailInfo.getDetailInfoByCompanyId("53"));
        try {
            String encryptedPwd = MD5.MD5_32bit( "凤阳县板桥供销合作社旅社");
             getOneDataByRowKey("companies",encryptedPwd);
        } catch (Exception e) {
            System.out.println("读取hbase出错了");
            e.printStackTrace();
        }
    }

    public static void getOneDataByRowKey(String tableName,String rowkey)throws Exception{
        HTable h=new HTable(configuration, tableName);

        Get g=new Get(Bytes.toBytes(rowkey));
        Result r=h.get(g);
        for(KeyValue k:r.raw()){
            System.out.println("行号:  "+ Bytes.toStringBinary(k.getRow()));
            System.out.println("时间戳:  "+k.getTimestamp());
            System.out.println("列簇:  "+Bytes.toStringBinary(k.getFamily()));
            System.out.println("列:  "+Bytes.toStringBinary(k.getQualifier()));
            //if(Bytes.toStringBinary(k.getQualifier()).equals("myage")){
            //  System.out.println("值:  "+Bytes.toInt(k.getValue()));
            //}else{
            String ss=  Bytes.toString(k.getValue());
            System.out.println("值:  "+ss);
            //}
        }
        h.close();

    }



    public static void addData(String rowKey, String tableName,
                               String column1, String value1)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        HTable table = new HTable(configuration, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
        // 获取表
        put.add(Bytes.toBytes("datas"),
                Bytes.toBytes(column1), Bytes.toBytes(value1));
        table.put(put);
        System.out.println("add data Success!");
    }
}
