package com.company.TycDao;




import com.company.DBUtils;
import com.company.IndustryModel;
import com.company.ReadIndustryTxt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
public class CompanyDao {
    private DBUtils dbUtils;
    public CompanyDao(){
        this.dbUtils=new DBUtils();
    }

    public Map<String,Object> getCompanyInfo(String companyId){
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)) {
            String sql = "select" +
                    " actual_capital,approved_time,base,business_scope,id,company_org_type,company_type,flag,from_time,legal_person_id," +
                    "legal_person_name,name,org_number,reg_capital,reg_institute,reg_location,reg_number,reg_status,source_flag,updatetime," +
                    "to_time,legal_person_type ,property1 " +
                    " from company where id = ? and company_org_type != '个体户' limit 1";
            String newsql= "select * from company where id=? and company_org_type !='个体户' limit 1";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(newsql,companyId);
            return convertCompanyData(res);
        }
        return null;
    }

    private Map<String,Object> convertCompanyData(List<Map<String,Object>> input){
//        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("actualCapital",tmp.get("actual_capital"));
                conMap.put("approvedTime",tmp.get("approved_time"));
                conMap.put("base",tmp.get("base"));
                conMap.put("businessScope",tmp.get("business_scope"));
                conMap.put("companyId",tmp.get("id"));
                conMap.put("id",tmp.get("id"));
                conMap.put("companyOrgType",tmp.get("company_org_type"));
                conMap.put("companyType",tmp.get("company_type"));
                conMap.put("flag",tmp.get("flag"));
                conMap.put("fromTime",tmp.get("from_time"));
                conMap.put("legalPersonId",tmp.get("legal_person_id"));//不一样，don't know why
                conMap.put("legalPersonName",tmp.get("legal_person_name"));
                conMap.put("name",tmp.get("name"));
                conMap.put("orgNumber",tmp.get("org_number"));
                conMap.put("regCapital",tmp.get("reg_capital"));
                conMap.put("regInstitute",tmp.get("reg_institute"));

                conMap.put("regLocation",tmp.get("reg_location"));
                conMap.put("regNumber",tmp.get("reg_number"));  //又不一样
                conMap.put("regStatus",tmp.get("reg_status"));
                conMap.put("sourceFlag",tmp.get("source_flag"));
                conMap.put("updatetime",tmp.get("updatetime"));

                // 沟通之后，增加字段
                conMap.put("creditCode",tmp.get("property1"));
                conMap.put("updateTimes",tmp.get("updatetime"));
                conMap.put("term",tmp.get("from_time")+"至"+tmp.get("to_time"));
                conMap.put("type",tmp.get("legal_person_type"));
                conMap.put("correctCompanyId",tmp.get("id"));//id

                conMap.put("categoryScore",null);
                conMap.put("category_code", getCompanyCategoryCodeByCompanyId(tmp.get("id").toString()));
                conMap.put("companyUrl",null);

                conMap.put("estiblishTime",tmp.get("from_time"));
                if(conMap.get("category_code")!=null){
                   Object inStr =ReadIndustryTxt.p.get(conMap.get("category_code").toString()); /*ReadIndustryTxt.getModelByCode(conMap.get("category_code").toString());*/
                    if(inStr!=null) {
                        conMap.put("industry",inStr.toString());
                    }else{
                        conMap.put("industry","");
                    }
                }else{
                    conMap.put("industry",null);
                }
                conMap.put("keyword",null);
                conMap.put("percentileScore",null);
                conMap.put("investList",null);
            }
        }
        return conMap;
    }

    private String getCompanyCategoryCodeByCompanyId(String companyId){
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)) {
            String sql ="select category_code from company_category_20170411 where company_id = ? limit 1";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(sql,companyId);
            if(res!=null&&res.size()>0) {
                String str ="";
                try {
                    str = res.get(0).get("category_code").toString();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return str;
            }
        }
        return null;
    }


}
