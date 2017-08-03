package com.company;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;
import com.company.batchDao.StringUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.jruby.RubyProcess;

import static org.apache.hadoop.hbase.security.visibility.TestVisibilityLabels.conf;

public class Main {

    public static SearchDetailInfo searchDetailInfo=new SearchDetailInfo();
    public static Configuration configuration;
    static {
        configuration = HBaseConfiguration.create();
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        configuration.set("hbase.zookeeper.quorum", "192.168.0.189:2181");
//        configuration.set("hbase.master", "192.168.0.191:60000");
//        configuration.set("hbase.rootdir", "hdfs://192.168.0.191:8020/hbase");

    }
//真正进行添加工作！

    public static void dealOne(String id){
        try {
            Map<String, Object> res = searchDetailInfo.getDetailInfoByCompanyId(id);
            System.out.println(Thread.currentThread().getName() +":已处理到"+id);
            if(res.get("result")==null){
                System.out.println( "处理id :"+id+"组装失败！！");
                return;
            }
            Map resultMap=(Map)res.get("result");
            if(resultMap.get("name")!=null){
                String name = resultMap.get("name").toString();
                String jres= JSONObject.toJSONString(res);
                String  encryptedPwd = MD5.MD5_32bit(name);

                addData(encryptedPwd,"company_update","datas",jres);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName()+" :处理id :"+id+"失败!!!!");
        }
    }



    public static void main(String[] args) {
        String companyId=args[0];
        //92280000"
//        String companyId="92280000";
        //根据company name，处理一条数据，添加到hbase中
//        dealOne(companyId);
        // 传入数据为文件名
        readFileDealOne(companyId);
    }


    public static void addData(String rowKey, String tableName,
                               String column1, String value1) {
        try {
            Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
            HTable table = new HTable(configuration, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
            // 获取表
            put.add(Bytes.toBytes("datas"),
                    Bytes.toBytes(column1), Bytes.toBytes(value1));
            table.put(put);
            System.out.println("add data Success!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileDealOne(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int countline = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + countline + ": " + tempString);
                countline++;
                //按行处理内容：
                dealOne(tempString.trim());
            }
            reader.close();
            System.out.println("读取结束，处理完成！！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
