package com.company.TycDao;



import com.company.ConvertTool;
import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
//// TODO: 2017/5/22  test,数据库表中字段province没有
public class CompanyEquityDao {
    private DBUtils dbUtils;
    public CompanyEquityDao(){
        this.dbUtils=new DBUtils();
    }
    public List<Map<String,Object>> getEquityInfoByCompanyId(String companyId){
        List<Map<String,Object>>  res=null;
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)){
            String sql="select base,certif_number_l,certif_number_r,equity_amount,pledgee,pledgor,reg_date,reg_number,state from company_equity_info where company_id = ?";
            res= dbUtils.query(sql,companyId);
            return convertEquityInfoData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertEquityInfoData(List<Map<String,Object>> input){
        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("base",tmp.get("base"));
                conMap.put("certifNumber",tmp.get("certif_number_l"));
                conMap.put("certifNumberR",tmp.get("certif_number_r"));
                conMap.put("equityAmount",tmp.get("equity_amount"));
                conMap.put("pledgee",tmp.get("pledgee"));
                conMap.put("pledgor",tmp.get("pledgor"));
                conMap.put("regDate",tmp.get("reg_date"));
                conMap.put("regNumber",tmp.get("reg_number"));
                conMap.put("state",tmp.get("state"));
                if(tmp.get("base")!=null) {
                    conMap.put("province", ConvertTool.code2province.get(tmp.get("base").toString().toUpperCase()));
                }else{
                    conMap.put("province",null);
                }
                conList.add(conMap);
            }
        }
        return conList;
    }
}
