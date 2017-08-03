package com.company.batchDao;


import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
//// TODO: 2017/5/22  test
public class BatchCompanyBranchDao {
    private DBUtils dbUtils;
    public BatchCompanyBranchDao(){
        this.dbUtils=new DBUtils();
    }

    public Map<String,List<Map<String,Object>>> getCompanyBranchByCompanyId(String start ,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end) ){
            String sql="select parent_id,category_code,name,reg_number,reg_status,legal_person_name from company LEFT JOIN company_category_20170411 on company.id = company_category_20170411.company_id  where company.parent_id >= ? and  company.parent_id < ?";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertCompanyBranchData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertCompanyBranchData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> lres=new HashMap<String,List<Map<String,Object>>>();
        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {

                Map conMap = new HashMap();
                conMap.put("childname", tmpMap.get("name"));
                conMap.put("regNumber", tmpMap.get("reg_number"));
                conMap.put("regStatus", tmpMap.get("reg_status"));
                //// TODO: 2017/5/23 确定category_code
                //company_category
                conMap.put("category_code", tmpMap.get("category_code"));
                conMap.put("legalPersonName", tmpMap.get("legal_person_name"));

                Object companyid = tmpMap.get("parent_id");
                if(companyid!=null){
                    if(lres.containsKey(companyid.toString())){
                        lres.get(companyid.toString()).add(conMap);
                    }else{
                        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                        list.add(conMap);
                        lres.put(companyid.toString(),list);
                    }
                }

            }
        }
        return lres;
    }
}
