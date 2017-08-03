package com.company.batchDetail;

import com.alibaba.fastjson.JSONObject;
import com.company.MD5;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BatchMain {

    public static BatchSearchDetailInfo searchDetailInfo=new BatchSearchDetailInfo();
    public static Configuration configuration;
    static {
        configuration = HBaseConfiguration.create();
//        configuration.set("hbase.zookeeper.property.clientPort", "2181");
//        configuration.set("hbase.zookeeper.quorum", "192.168.0.189:2181");
//        configuration.set("hbase.master", "192.168.0.191:60000");
//        configuration.set("hbase.rootdir", "hdfs://192.168.0.191:8020/hbase");

    }
//真正进行添加工作！

    public  void dealOne(String start,String end){
        try {
//            String inSql = createInSql(id,num);
            Map<String, Object> res = searchDetailInfo.getDetailInfoByCompanyId(start,end);
            for(Map.Entry<String, Object> entry :res.entrySet()) {

                String companyid = entry.getKey();

                System.out.println(Thread.currentThread().getName() + ":已处理到" + companyid);
                String jres = JSONObject.toJSONString(entry);
                String encryptedPwd = null;
                encryptedPwd = MD5.MD5_32bit(companyid);
                addData(encryptedPwd, "company", "data", jres);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(Thread.currentThread().getName()+" :处理id :"+ start +"失败!!!!");
        }
    }



    public static void main(String[] args) {

       // args= new String[]{"90000000","100000000","1"};


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(format.format(new Date())+"开始处理！") ;

        int threadNum = Integer.parseInt(args[2]);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        int intStart = Integer.parseInt(args[0]);
        int  endStart = Integer.parseInt(args[1]);
        long starttime = new Date().getTime();
        long stepTime = 0;
        int step = 1000;
        while(intStart<endStart){
            int linend = intStart + step;

            final int sa = intStart;
            final int se = linend;


          /*  fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {*/
                   stepTime = new Date().getTime();
                    Map<String, Object> res = searchDetailInfo.getDetailInfoByCompanyId(sa+"", se+"" );

                    System.out.println(Thread.currentThread().getName()+"组装数据:"+(new Date().getTime()-stepTime));
                    stepTime = new Date().getTime();
                    int count=0;
                    List<List<String>> puts = new ArrayList<List<String>>();
                    for(Map.Entry<String,Object> entry : res.entrySet()){
                        List<String> valueList = new ArrayList<String>();
                        count++;
                        String key = entry.getKey();
                        Object allValue = entry.getValue();

                        String jres=JSONObject.toJSONString(entry.getValue());
                        String encryptedPwd = null;
                        try {
                            Map<String,Map<String,Object>> nameMap=(Map<String,Map<String,Object>>)allValue;
                            String name = nameMap.get("result").get("name").toString();
                            encryptedPwd = MD5.MD5_32bit(name);

                            valueList.add(encryptedPwd);
                            valueList.add(jres);
                            puts.add(valueList);

                            //addData(encryptedPwd,"company","data",jres);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.err.println(Thread.currentThread().getName()+" : 处理"+count+"失败!");
                        }
                    }
                    try {
//                        addListData(puts,"company");
                        addListData(puts,"companies");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println(format.format(new Date())+Thread.currentThread().getName()+"插入:"+(new Date().getTime()-stepTime) +"处理"+sa +" -  " +se+"");
         /*       }
            });*/


            intStart = linend;

        }

        System.out.println(format.format(new Date())+"结束！") ;
        System.out.println("共用时:"+(new Date().getTime()-starttime));

        //fixedThreadPool.shutdown();
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
//        System.out.println("add data Success!");
    }

    public static Put getPut(String rowKey, String value1)
            throws IOException {
        Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
        // 获取表
        put.add(Bytes.toBytes("datas"),
                Bytes.toBytes("data"), Bytes.toBytes(value1));
        return put;
//        System.out.println("add data Success!");
    }

    public static void addListData(List<List<String>> list, String tableName)
            throws IOException {
        List<Put> puts = new ArrayList<Put>();
        for (List<String> l:list) {
            puts.add(getPut(l.get(0),l.get(1)));
        }
        HTable table = new HTable(configuration, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
        // 获取表
        table.put(puts);
//        System.out.println("add data Success!");
    }
}
