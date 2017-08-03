package com.company.pinBatchDao;


import com.company.batchDao.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class PinBatchCompanyChageInfoDao {
//    private BatchChangeDBUtils dbUtils;
    private BatchDBUtils dbUtils;
    public PinBatchCompanyChageInfoDao(){
        this.dbUtils=new BatchDBUtils();
    }

    public Map<String,List<Map<String,Object>>> getCompanyChangeInfoByCompanyId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select id,company_id,change_item,change_time,content_after,content_before,createTime from company_change_info where company_id >= ? and company_id < ?";
            res=dbUtils.query(sql,new Object[]{start,end});
            return convertCompanyChangeInoData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCompanyChangeInoData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();
        if(input!=null){
            for(Map<String,Object> tmpMap:input) {
                Map conMap=new HashMap();
                conMap.put("id", tmpMap.get("id"));
                conMap.put("companyId", tmpMap.get("company_id"));
                conMap.put("changeItem", tmpMap.get("change_item"));
                conMap.put("changeTime", tmpMap.get("change_time"));
                conMap.put("contentAfter", tmpMap.get("content_after"));
                conMap.put("contentBefore", tmpMap.get("content_before"));
                conMap.put("createtime", tmpMap.get("createTime"));
                //待定，数据库中没有此字段
                conMap.put("companyName", null);
                Object cid= tmpMap.get("company_id");
                if(cid!=null && StringUtil.isNotEmpty(cid.toString())){
                    String sid= cid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres=new ArrayList<Map<String,Object>>();
                        lres.add(conMap);
                        allMap.put(sid,lres);
                    }
                }
            }
        }
        return allMap;
    }
}
