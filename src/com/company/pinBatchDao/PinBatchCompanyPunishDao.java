package com.company.pinBatchDao;


import com.company.ConvertTool;
import com.company.DBUtils;
import com.company.batchDao.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class PinBatchCompanyPunishDao {
    private DBUtils dbUtils;
    public PinBatchCompanyPunishDao(){
        this.dbUtils=new DBUtils();
    }


    public Map<String,List<Map<String,Object>>> getPunishInfoByCompanyId(String start,String end){
        List<Map<String,Object>> result=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,base,content,department_name,name,punish_number,type from company_punishment_info where company_id >=? and company_id < ?";
            result= dbUtils.query(sql,new Object[]{start,end});
            return conPunishData(result);
        }
        return null;
    }
    private Map<String,List<Map<String,Object>>> conPunishData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap = new HashMap<String,List<Map<String,Object>>>();

        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("base",tmp.get("base"));
                conMap.put("content",tmp.get("content"));
                conMap.put("punishNumber",tmp.get("punish_number"));
                conMap.put("type",tmp.get("type"));
                conMap.put("name",tmp.get("name"));
                conMap.put("departmentName",tmp.get("department_name"));
                if( tmp.get("base")!=null) {
                    conMap.put("province", ConvertTool.code2province.get(tmp.get("base").toString().toUpperCase()));
                }else{
                    conMap.put("province",null);
                }
                Object oid = tmp.get("company_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allMap.put(sid,conList);
                    }
                }

            }
        }
        return allMap;
    }
}
