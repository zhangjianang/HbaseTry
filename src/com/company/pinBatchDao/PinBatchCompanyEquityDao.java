package com.company.pinBatchDao;



import com.company.ConvertTool;
import com.company.DBUtils;
import com.company.batchDao.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
//// TODO: 2017/5/22  test,数据库表中字段province没有
public class PinBatchCompanyEquityDao {
    private DBUtils dbUtils;
    public PinBatchCompanyEquityDao(){
        this.dbUtils=new DBUtils();
    }
    public Map<String,List<Map<String,Object>>> getEquityInfoByCompanyId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&& end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,base,certif_number_l,certif_number_r,equity_amount,pledgee,pledgor,reg_date,reg_number,state from company_equity_info where company_id >= ? and company_id < ?";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertEquityInfoData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertEquityInfoData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();

        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

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

                Object oid=tmp.get("company_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid=oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allMap.put(sid,conList);
                    }
                }

            }
        }
        return allMap;
    }
}
