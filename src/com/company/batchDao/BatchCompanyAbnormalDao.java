package com.company.batchDao;



import com.amazonaws.util.json.JSONObject;
import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/23.
 */
//// TODO: 2017/5/23  比对值
public class BatchCompanyAbnormalDao {

    private DBUtils dbUtils;
    public BatchCompanyAbnormalDao(){
        this.dbUtils=new DBUtils();
    }
    public Map<String,List<Map<String,Object>>> getCompanyAbnormalByCompanyId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select id,company_id,createTime,put_date,put_department,put_reason,remove_date,remove_department,remove_reason from company_abnormal_info where company_id >= ? and company_id < ?";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertCompanyAbnormalData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCompanyAbnormalData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();

        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("companyId", tmpMap.get("company_id"));
                conMap.put("createtime", tmpMap.get("createTime"));
                conMap.put("id", tmpMap.get("id"));

                conMap.put("putDate", tmpMap.get("put_date"));
                conMap.put("putDepartment", tmpMap.get("put_department"));
                conMap.put("putReason", tmpMap.get("put_reason"));

                conMap.put("removeDate", tmpMap.get("remove_date"));
                conMap.put("removeDepartment", tmpMap.get("remove_department"));
                conMap.put("removeReason", tmpMap.get("remove_reason"));

                Object oid =tmpMap.get("company_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres= new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allMap.put(sid,lres);
                    }
                }
            }
        }
        return allMap;
    }

    private JSONObject getCompanyAbnormalData(List<Map<String,Object>> input){
        return new JSONObject(convertCompanyAbnormalData(input));
    }
}
