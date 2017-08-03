package com.company.batchDao;



import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
//// TODO: 2017/5/22  test
public class BatchCompanyCheckInfoDao {
    private DBUtils dbUtils;
    public BatchCompanyCheckInfoDao(){
        this.dbUtils=new DBUtils();
    }
    public Map<String,List<Map<String,Object>>> getCheckInfoById(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,check_org,check_type,check_date,check_result from company_check_info where company_id >= ? and company_id < ?" ;
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertCheckInfoData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCheckInfoData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("checkDate",tmp.get("check_date"));
                conMap.put("checkOrg",tmp.get("check_org"));
                conMap.put("checkResult",tmp.get("check_result"));
                conMap.put("checkType",tmp.get("check_type"));
                Object cId=tmp.get("company_id");
                if(cId!=null&&StringUtil.isNotEmpty(cId.toString())) {
                    String companyId= cId.toString();
                    if (allMap.containsKey(companyId)) {
                        allMap.get(companyId).add(conMap);
                    }else{
                        List<Map<String,Object>> addList=new ArrayList<Map<String,Object>>();
                        addList.add(conMap);
                        allMap.put(companyId,addList);
                    }
                }
            }
        }
        return allMap;
    }
}
