package com.company.TycDao;



import com.company.DBUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by adimn on 2017/5/19.
 */
public class CompanyChageInfoDao {
    private DBUtils dbUtils;
    public CompanyChageInfoDao(){
        this.dbUtils=new DBUtils();
    }
    public List<Map<String,Object>> getCompanyChangeInfoByCompanyId(String companyId){
        List<Map<String,Object>> res=null;
        if(companyId!=null&& StringUtil.isNotEmpty(companyId)){
            String sql="select id,company_id,change_item,change_time,content_after,content_before,createTime from company_change_info where company_id = ?";
            res=dbUtils.query(sql,companyId);
            return convertCompanyChangeInoData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertCompanyChangeInoData(List<Map<String,Object>> input){
        List<Map<String,Object>> lres=null;
        if(input!=null){
            lres=new ArrayList<Map<String,Object>>();
            for(Map<String,Object> tmpMap:input) {
                Map conMap=new HashMap();
                conMap.put("id", tmpMap.get("id"));
                conMap.put("companyId", tmpMap.get("company_id"));
                conMap.put("changeItem", tmpMap.get("change_item"));
                conMap.put("changeTime", tmpMap.get("change_time"));
                conMap.put("contentAfter", tmpMap.get("content_after"));
                conMap.put("contentBefore", tmpMap.get("content_before"));
                conMap.put("createtime", tmpMap.get("createTime"));
                //待定，数据库中没有此字段
                conMap.put("companyName", null);
                lres.add(conMap);
            }
        }
        return lres;
    }
}
