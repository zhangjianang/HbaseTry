package com.company.RandomGetCheck;

import com.alibaba.fastjson.JSONObject;
import com.company.TycDao.StringUtil;
import com.company.Utils.HttpRequestUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by adimn on 2017/6/12.
 */
public class RandomCheck {
    private final static String GET_URL_PREFIX = "http://api.tianyancha.com/services/v3/open/allDetail2";
    //传入两个值，一个更新id文件名，一个是文件行数
    //随机选取十条数据，对比结果，一旦有不同值，就直接抛出错误
    public static void main(String[] args){
        TreeSet<Integer> reset=new TreeSet<Integer>();
        randomSet(1,100,10,reset);
        System.out.println(reset);

//        List companyIds=readFromFile("",reset);
        RandomCheck rc=new RandomCheck();
//        rc.get("深圳市堃鹏文化发展有限公司");
        Map<String, Object> urlRes = JsonTools.getMapByUrl(genGetUrl("深圳市堃鹏文化发展有限公司"));
        System.out.println(urlRes);
    }


    //根据输入id，查询company详细信息
    public static Boolean checkData(String companyId){
        return  true;
    }
    /**
     * 随机指定范围内N个不重复的数
     * 利用HashSet的特征，只能存放不同的值
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     * @param set 随机数结果集
     */
    public static void randomSet(int min, int max, int n, TreeSet<Integer> set) {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            // 调用Math.random()方法
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        int setSize = set.size();
        // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，直到达到指定大小
        if (setSize < n) {
            randomSet(min, max, n - setSize, set);// 递归
        }
    }

    public static List<String> readFromFile(String fileName,TreeSet<Integer> set){
        ArrayList<String> companyIds=new ArrayList<String>();
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
                if(set.contains(countline)){
                    companyIds.add(tempString);
                }
                countline++;
                //按行处理内容：
            }
            reader.close();
            System.out.println("读取结束，处理完成！！");
            return companyIds;
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
        return null;
    }

    private static String genGetUrl(String name) {
        if (StringUtil.isNotEmpty(name)) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return GET_URL_PREFIX + "?companyName=" + name + "&type=100001";
    }

    private  String getValue(JSONObject jsonObject, String key) {
//        return (jsonObject!=null && jsonObject.has(key)) ? jsonObject.getString(key) : "";
        return null;
    }

}
