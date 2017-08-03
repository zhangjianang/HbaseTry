package com.company.TycDao;

import com.company.ConvertTool;
import com.company.DBUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class MortgageInfoDao {
    private DBUtils dbUtils;
    public MortgageInfoDao(){
        this.dbUtils=new DBUtils();
    }

//待优化
    //select * from company_mortgage_info as a LEFT JOIN mortgage_people_info as b on a.id =b.mortgage_id  LEFT JOIN mortgage_pawn_info as c on b.mortgage_id = c.mortgage_id  LEFT JOIN mortgage_change_info as d  on d.mortgage_id=c.mortgage_id where a.company_id="19463293";
    public List<Map<String,Object>> getMortgageInfoByCompanyId(String companyId){
        List<Map<String,Object>> res=null;
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)){
            String sql="select id,amount,base,overview_term,publish_date,reg_date,reg_department,reg_num,remark,scope,status,type from company_mortgage_info where company_id = ?";
            res= dbUtils.query(sql,companyId);
            return convertMortgageInfoData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertMortgageInfoData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("amount", tmpMap.get("amount"));
                conMap.put("base", tmpMap.get("base"));
                conMap.put("id", tmpMap.get("id"));
                conMap.put("overviewTerm", tmpMap.get("overview_term"));
                conMap.put("province", ConvertTool.code2province.get( tmpMap.get("base").toString().toUpperCase() ));//需要转化
                conMap.put("publishDate", tmpMap.get("publish_date"));
                conMap.put("regDate", tmpMap.get("reg_date"));
                conMap.put("regDepartment", tmpMap.get("reg_department"));
                conMap.put("regNum", tmpMap.get("reg_num"));
                conMap.put("remark", tmpMap.get("remark"));

                conMap.put("scope", tmpMap.get("scope"));
                conMap.put("status", tmpMap.get("status"));
                conMap.put("type", tmpMap.get("type"));

                String mortId=tmpMap.get("id").toString();

                conMap.put("mchangelist",this.getMchangeListByMortId(mortId));
                conMap.put("mpawnlist",this.getMpawnListByMortId(mortId));
                conMap.put("mpeoplelist",this.getMpeopleListByMortId(mortId));
                lres.add(conMap);
            }
        }
        return lres;
    }

    //得到mpeopleList
    private List<Map<String,Object>> getMpeopleListByMortId(String mortId){
        List<Map<String,Object>> res=null;
        if(mortId!=null&& StringUtil.isNotEmpty(mortId)){
            // 待改
           String sql="select license_num,license_type,people_name from mortgage_people_info  where mortgage_id = ?";
            res= dbUtils.query(sql,mortId);
            return convertMpeopleListData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertMpeopleListData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();

                conMap.put("licenseNum", tmpMap.get("license_num"));
                conMap.put("liceseType", tmpMap.get("license_type"));
                conMap.put("peopleName", tmpMap.get("people_name"));
                lres.add(conMap);
            }
        }
        return lres;
    }

   //得到mchangeList
    private List<Map<String,Object>> getMchangeListByMortId(String mortId){
        List<Map<String,Object>> res=null;
        if(mortId!=null&& StringUtil.isNotEmpty(mortId)){

            String sql="select change_content,change_date from mortgage_change_info where mortgage_id = ?";
            res= dbUtils.query(sql,mortId);
            return convertMchangeListData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertMchangeListData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("changeContent", tmpMap.get("change_content"));
                conMap.put("changeDate", tmpMap.get("change_date"));
                lres.add(conMap);
            }
        }
        return lres;
    }


    //得到MpawnList
    private List<Map<String,Object>> getMpawnListByMortId(String mortId){
        List<Map<String,Object>> res=null;
        if(mortId!=null&& StringUtil.isNotEmpty(mortId)){
            String sql="select detail,ownership,pawn_name from mortgage_pawn_info where mortgage_id = ?";
            res= dbUtils.query(sql,mortId);
            return convertMpawnListData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertMpawnListData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null) {
            lres = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("detail", tmpMap.get("detail"));
                conMap.put("ownership", tmpMap.get("ownership"));
                conMap.put("pawnName", tmpMap.get("pawn_name"));
                lres.add(conMap);
            }
        }
        return lres;
    }
}
