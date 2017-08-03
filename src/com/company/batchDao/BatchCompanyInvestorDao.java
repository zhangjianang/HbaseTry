package com.company.batchDao;



import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class BatchCompanyInvestorDao {
    private DBUtils dbUtils;
    public BatchCompanyInvestorDao(){
        this.dbUtils=new DBUtils();
    }
    public Map<String,List<Map<String,Object>>> getCompanyInvestorByCompanyId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,a.amount,b.id,b.name,b.type from company_investor as a LEFT JOIN human as b on a.investor_id=b.id where company_id >= ? and company_id < ?";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertCompanyInvestorData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCompanyInvestorData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();
        if(input!=null) {
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("amount", tmpMap.get("amount"));
                conMap.put("id", tmpMap.get("id"));
                conMap.put("name", tmpMap.get("name"));
                conMap.put("type", tmpMap.get("type"));
                Object oid=tmpMap.get("company_id");
                if(oid!=null&& StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>>  lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allMap.put(sid,lres);
                    }
                }
            }
        }

        return allMap;
    }
}
