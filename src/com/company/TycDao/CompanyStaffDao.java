package com.company.TycDao;



import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * Created by adimn on 2017/5/19.
 */
public class CompanyStaffDao {
    private DBUtils dbUtils;
    public CompanyStaffDao(){
        this.dbUtils=new DBUtils();
    }
    public List<Map<String,Object>> getCompanyStaffByCompanyId(String companyId){
        List<Map<String,Object>> res=null;
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)){
            String sql="select human.id,name,type,staff_type_name from company_staff left join human on company_staff.staff_id = human.id where company_id = ?";
            res= dbUtils.query(sql,companyId);
            return convertCompanyStaffData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertCompanyStaffData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("id", tmpMap.get("id"));
                conMap.put("name", tmpMap.get("name"));
                conMap.put("type", tmpMap.get("type"));

                conMap.put("typeJoin", tmpMap.get("staff_type_name"));
                lres.add(conMap);
            }
        }
        return lres;
    }
}
