package com.company.batchDao;

import com.company.ConvertTool;
import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class BatchMortgageInfoDao {
    private DBUtils dbUtils;
    public BatchMortgageInfoDao(){
        this.dbUtils=new DBUtils();
    }

//待优化
    //select * from company_mortgage_info as a LEFT JOIN mortgage_people_info as b on a.id =b.mortgage_id  LEFT JOIN mortgage_pawn_info as c on b.mortgage_id = c.mortgage_id  LEFT JOIN mortgage_change_info as d  on d.mortgage_id=c.mortgage_id where a.company_id="19463293";
    public Map<String,List<Map<String,Object>>> getMortgageInfoByCompanyId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
            String sql="select company_id,id,amount,base,overview_term,publish_date,reg_date,reg_department,reg_num,remark,scope,status,type from company_mortgage_info where company_id >= ? and company_id< ?";
            res= dbUtils.query(sql,new Object[]{start,end});

            Map<String, List<Map<String, Object>>> mpeopleMap = getMpeopleListByMortId(start, end);
            Map<String, List<Map<String, Object>>> mchangeMap = getMchangeListByMortId(start, end);
            Map<String, List<Map<String, Object>>> mpawnMap = getMpawnListByMortId(start, end);

            return convertMortgageInfoData(res,mpeopleMap,mchangeMap,mpawnMap);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertMortgageInfoData(List<Map<String,Object>> input,Map<String, List<Map<String, Object>>> mpeopleMap,Map<String, List<Map<String, Object>>> mchangeMap,Map<String, List<Map<String, Object>>> mpawnMap){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();

        if(input!=null) {


            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("amount", tmpMap.get("amount"));
                conMap.put("base", tmpMap.get("base"));
                conMap.put("id", tmpMap.get("id"));
                conMap.put("overviewTerm", tmpMap.get("overview_term"));
                try{
                    conMap.put("province", ConvertTool.code2province.get( tmpMap.get("base").toString().toUpperCase() ));//需要转化
                }catch(Exception e){
                    conMap.put("province", "");//需要转化
                }

                conMap.put("publishDate", tmpMap.get("publish_date"));
                conMap.put("regDate", tmpMap.get("reg_date"));
                conMap.put("regDepartment", tmpMap.get("reg_department"));
                conMap.put("regNum", tmpMap.get("reg_num"));
                conMap.put("remark", tmpMap.get("remark"));

                conMap.put("scope", tmpMap.get("scope"));
                conMap.put("status", tmpMap.get("status"));
                conMap.put("type", tmpMap.get("type"));

                String mortId=tmpMap.get("id").toString();

                if(mchangeMap!=null&& mchangeMap.containsKey(mortId)){
                    conMap.put("mchangelist",mchangeMap.get(mortId));
                }else{
                    conMap.put("mchangelist","");
                }
                if(mpawnMap!=null&& mpawnMap.containsKey(mortId)){
                    conMap.put("mpawnlist",mpawnMap.get(mortId));
                }else{
                    conMap.put("mpawnlist","");
                }

                if(mpeopleMap!=null&& mpeopleMap.containsKey(mortId)){
                    conMap.put("mpeoplelist",mpeopleMap.get(mortId));
                }else{
                    conMap.put("mpeoplelist","");
                }

//                conMap.put("mchangelist",this.getMchangeListByMortId(mortId));
//                conMap.put("mpawnlist",this.getMpawnListByMortId(mortId));
//                conMap.put("mpeoplelist",this.getMpeopleListByMortId(mortId));

                Object oid = tmpMap.get("company_id");
                if(oid!=null&& StringUtil.isNotEmpty(oid.toString())){
                    String sid = oid.toString();
                    if(allMap.containsKey(sid)){
                        allMap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allMap.put(sid,lres);
                    }
                }
            }
        }
        return allMap;
    }

    //得到mpeopleList
    private Map<String,List<Map<String,Object>>> getMpeopleListByMortId(String start, String end ){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&& end!=null&& StringUtil.isNotEmpty(end)){
            // 待改
           String sql="select mortgage_id,license_num,license_type,people_name from mortgage_people_info  where mortgage_id in (select distinct id from company_mortgage_info where company_id >= ? and company_id< ?)";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertMpeopleListData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertMpeopleListData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap = new HashMap<String,List<Map<String,Object>>>();
        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("licenseNum", tmpMap.get("license_num"));
                conMap.put("liceseType", tmpMap.get("license_type"));
                conMap.put("peopleName", tmpMap.get("people_name"));
                Object oid =tmpMap.get("mortgage_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allmap.put(sid,lres);
                    }
                }
            }
        }
        return allmap;
    }

   //得到mchangeList
    private Map<String,List<Map<String,Object>>> getMchangeListByMortId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&end!=null&& StringUtil.isNotEmpty(end)){

            String sql="select mortgage_id,change_content,change_date from mortgage_change_info where mortgage_id in (select distinct id from company_mortgage_info where company_id >= ? and company_id< ?)";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertMchangeListData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertMchangeListData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap=new HashMap<String,List<Map<String,Object>>>();

        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("changeContent", tmpMap.get("change_content"));
                conMap.put("changeDate", tmpMap.get("change_date"));
                Object oid =tmpMap.get("mortgage_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allmap.put(sid,lres);
                    }
                }
            }
        }
        return allmap;
    }


    //得到MpawnList
    private Map<String,List<Map<String,Object>>> getMpawnListByMortId(String start,String end){
        List<Map<String,Object>> res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&&start!=end&& StringUtil.isNotEmpty(end)){
            String sql="select mortgage_id,detail,ownership,pawn_name from mortgage_pawn_info where mortgage_id in (select distinct id from company_mortgage_info where company_id >= ? and company_id< ?)";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertMpawnListData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertMpawnListData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap=new HashMap<String,List<Map<String,Object>>>();

        if(input!=null) {

            for (Map<String, Object> tmpMap : input) {
                Map conMap = new HashMap();
                conMap.put("detail", tmpMap.get("detail"));
                conMap.put("ownership", tmpMap.get("ownership"));
                conMap.put("pawnName", tmpMap.get("pawn_name"));
                Object oid = tmpMap.get("mortgage_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>>  lres = new ArrayList<Map<String, Object>>();
                        lres.add(conMap);
                        allmap.put(sid,lres);
                    }
                }
            }
        }
        return allmap;
    }
}
