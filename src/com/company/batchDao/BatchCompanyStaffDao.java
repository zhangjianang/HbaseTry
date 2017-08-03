package com.company.batchDao;


import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class BatchCompanyStaffDao {
    private DBUtils dbUtils;
    public BatchCompanyStaffDao(){
        this.dbUtils=new DBUtils();
    }
    public Map<String,List<Map<String,Object>>> getCompanyStaffByCompanyId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,human.id,name,type,staff_type_name from company_staff left join human on company_staff.staff_id = human.id where company_id >=? and company_id < ?";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertCompanyStaffData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCompanyStaffData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap = new HashMap<String,List<Map<String,Object>>>();

        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("id", tmpMap.get("id"));
                conMap.put("name", tmpMap.get("name"));
                conMap.put("type", tmpMap.get("type"));

                conMap.put("typeJoin", tmpMap.get("staff_type_name"));
                Object oid = tmpMap.get("company_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid = oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allMap.put(sid,lres);
                    }
                }
            }
        }
        return allMap;
    }
}
