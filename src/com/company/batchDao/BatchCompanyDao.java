package com.company.batchDao;




import com.company.DBUtils;
import com.company.ReadIndustryTxt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
public class BatchCompanyDao {
    private BatchDBUtils dbUtils;
    public BatchCompanyDao(){
        this.dbUtils=new BatchDBUtils();
    }

    public Map<String,Map<String,Object>> getCompanyInfo(String companyId, String end){
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)) {
            String sql = "select" +
                    " actual_capital,approved_time,base,business_scope,id,company_org_type,company_type,flag,from_time,legal_person_id," +
                    "legal_person_name,name,org_number,reg_capital,reg_institute,reg_location,reg_number,reg_status,source_flag,updatetime," +
                    "to_time,legal_person_type ,property1 " +
                    " from company where id = ? and company_org_type != '个体户' limit 1";
            String newsql= "select * from company where company_org_type !='个体户'  and id >=?  and id  < ?";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(newsql,new Object[]{companyId,end});

            return convertCompanyData(res);
        }
        return null;
    }

    private Map<String,Map<String,Object>> convertCompanyData(List<Map<String,Object>> input){
//        List<Map<String,Object>> conList=null;
        Map<String,Map<String,Object>> allMap=new HashMap<String,Map<String,Object>>();
        if(input!=null&&input.size()>0){
            for(Map<String,Object> tmp:input) {
                Map<String,Object> conMap=new HashMap();
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
               // conMap.put("category_code", getCompanyCategoryCodeByCompanyId(tmp.get("id").toString()));
                conMap.put("companyUrl",null);

                conMap.put("estiblishTime",tmp.get("from_time"));

                conMap.put("keyword",null);
                conMap.put("percentileScore",null);
                conMap.put("investList",null);


                allMap.put(conMap.get("id").toString(),conMap);
            }
        }
        return allMap;
    }



    /*private String getCompanyCategoryCodeByCompanyId(String companyId){
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)) {
            String sql ="select category_code from company_category_20170411 where company_id = ? limit 1";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(sql,companyId);
            if(res!=null&&res.size()>0) {
                Object str =null;
                try {
                    str = res.get(0).get("category_code");
                    if(str==null){
                        return "";
                    }else{
                        return
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return str;
            }
        }
        return null;
    }*/


    public Map<String,String> getCompanyCategoryCodeByCompanyId(String begin, String end) {

            String sql ="select company_id,category_code from company_category_20170411 where company_id >="+begin+" and company_id <"+end;
            List<Map<String,Object>> res = null;
            res = dbUtils.query(sql);

            Map<String,String> returnMap = new HashMap<String,String>();
            if(res!=null){
               for(Map<String,Object> map : res){
                   Object company_id = map.get("company_id");
                   Object category_code = map.get("category_code");
                   if(company_id!=null&&category_code!=null){
                       returnMap.put(company_id.toString(),category_code.toString());
                   }
               }
            }
        return returnMap;
    }


}
