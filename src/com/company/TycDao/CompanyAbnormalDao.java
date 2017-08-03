package com.company.TycDao;



import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/23.
 */
//// TODO: 2017/5/23  比对值
public class CompanyAbnormalDao {

    private DBUtils dbUtils;
    public CompanyAbnormalDao(){
        this.dbUtils=new DBUtils();
    }
    public List<Map<String,Object>> getCompanyAbnormalByCompanyId(String companyId){
        List<Map<String,Object>> res=null;
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)){
            String sql="select id,company_id,createTime,put_date,put_department,put_reason,remove_date,remove_department,remove_reason from company_abnormal_info where company_id = ?";
            res= dbUtils.query(sql,companyId);
            return convertCompanyAbnormalData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertCompanyAbnormalData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
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
                lres.add(conMap);
            }
        }
        return lres;
    }
}
