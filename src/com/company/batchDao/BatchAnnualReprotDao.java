package com.company.batchDao;


import com.company.DBUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class BatchAnnualReprotDao {
    private BatchDBUtils dbUtils;
    public BatchAnnualReprotDao(){
        this.dbUtils=new BatchDBUtils();
    }

    //根据companId 获得AnnualReport基本信息
    public Map<String,List<Map<String,Object>>> getAnnualReportListBaseInfo(String start, String end){

            String sql = "select company_id,id,company_name,credit_code,email,employee_num,manage_state,operator_name,phone_number,postal_address,postcode,prime_bus_profit" +
                    ",reg_number,report_year,retained_profit,total_assets,total_equity,total_liability,total_profit,total_sales,total_tax from annual_report where company_id>=? and company_id<?";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(sql,new Object[]{start,end});

            Map<String,List<Map<String,Object>>> shareHolderMap =  getAnnualReportShareHolderInfoByARId(start,end );
            Map<String, List<Map<String, Object>>> webInfoMap= getWebInfoByAReportId(start, end);
            Map<String, List<Map<String, Object>>> outBoundMap = getoutBoundInvestmentByAReportId(start, end);
            Map<String, List<Map<String, Object>>> outGuaranteeMap = getOutGuaranteeByAReportId(start, end);
        Map<String, List<Map<String, Object>>> equityChangeMap = getEquityChangeByAReportId(start, end);
        Map<String, List<Map<String, Object>>> changeRecordMap = getChangeRecordByAReportId(start, end);

        return convertAnnualBaseData(res,shareHolderMap,webInfoMap,outBoundMap,outGuaranteeMap,equityChangeMap,changeRecordMap);
    }

    //转换查询结果
    private Map<String,List<Map<String,Object>>> convertAnnualBaseData(List<Map<String,Object>> input ,Map<String,List<Map<String,Object>>> shareHolderMap ,Map<String, List<Map<String, Object>>> webInfoMap ,Map<String, List<Map<String, Object>>> outBoundMap,Map<String, List<Map<String, Object>>> outGuaranteeMap,Map<String, List<Map<String, Object>>> equityChangeMap,Map<String, List<Map<String, Object>>> changeRecordMap){


        //todo 循环组装
        Map<String,List<Map<String,Object>>> lres=new HashMap<String,List<Map<String,Object>>>();
        if(input!=null&&input.size()>0) {

            for (Map<String, Object> tmpMap : input) {
                Map<String,Object>  dataResult = new HashMap<String,Object>();
                Map<String,Object> reportMap=new HashMap<String,Object>();
                reportMap = new HashMap<String, Object>();

                dataResult.put("companyName", tmpMap.get("company_name"));
                dataResult.put("creditCode", tmpMap.get("credit_code"));
                dataResult.put("email", tmpMap.get("email"));
                dataResult.put("employeeNum", tmpMap.get("employee_num"));
                dataResult.put("manageState", tmpMap.get("manage_state"));
                dataResult.put("operatorName", tmpMap.get("operator_name"));
                dataResult.put("phoneNumber", tmpMap.get("phone_number"));
                dataResult.put("postalAddress", tmpMap.get("postal_address"));
                dataResult.put("postcode", tmpMap.get("postcode"));
                dataResult.put("primeBusProfit", tmpMap.get("prime_bus_profit"));
                dataResult.put("regNumber", tmpMap.get("reg_number"));
                dataResult.put("reportYear", tmpMap.get("report_year"));
                dataResult.put("retainedProfit", tmpMap.get("retained_profit"));
                dataResult.put("totalAssets", tmpMap.get("total_assets"));
                dataResult.put("totalEquity", tmpMap.get("total_equity"));
                dataResult.put("totalLiability", tmpMap.get("total_liability"));
                dataResult.put("totalProfit", tmpMap.get("total_profit"));
                dataResult.put("totalSales", tmpMap.get("total_sales"));
                dataResult.put("totalTax", tmpMap.get("total_tax"));

                String aReportId=tmpMap.get("id").toString();

                reportMap.put("baseInfo",dataResult);

                if(shareHolderMap!=null && shareHolderMap.containsKey(aReportId)) {
                    reportMap.put("shareholderList" ,shareHolderMap.get(aReportId));
                }else{
                    reportMap.put("shareholderList","");
                }

                if(webInfoMap!=null && webInfoMap.containsKey(aReportId)) {
                    reportMap.put("webInfoList" ,webInfoMap.get(aReportId));
                }else{
                    reportMap.put("webInfoList","");
                }

                if(outBoundMap!=null && outBoundMap.containsKey(aReportId)) {
                    reportMap.put("outboundInvestmentList" ,outBoundMap.get(aReportId));
                }else{
                    reportMap.put("outboundInvestmentList","");
                }

                if(outGuaranteeMap!=null && outGuaranteeMap.containsKey(aReportId)) {
                    reportMap.put("outGuaranteeInfoList" ,outGuaranteeMap.get(aReportId));
                }else{
                    reportMap.put("outGuaranteeInfoList","");
                }

                if(equityChangeMap!=null && equityChangeMap.containsKey(aReportId)) {
                    reportMap.put("equityChangeInfoList" ,equityChangeMap.get(aReportId));
                }else{
                    reportMap.put("equityChangeInfoList","");
                }

                if(changeRecordMap!=null && changeRecordMap.containsKey(aReportId)) {
                    reportMap.put("changeRecordList" ,changeRecordMap.get(aReportId));
                }else{
                    reportMap.put("changeRecordList","");
                }

//                reportMap.put("shareholderList",getAnnualReportShareHolderInfoByARId(aReportId));
//                reportMap.put("webInfoList",getWebInfoByAReportId(aReportId));
//                reportMap.put("outboundInvestmentList",getoutBoundInvestmentByAReportId(aReportId));
//                reportMap.put("outGuaranteeInfoList",getOutGuaranteeByAReportId(aReportId));
//                reportMap.put("equityChangeInfoList",getEquityChangeByAReportId(aReportId));
//                reportMap.put("changeRecordList",getChangeRecordByAReportId(aReportId));


                Object companyid = tmpMap.get("company_id");
                if(companyid!=null){
                    if(lres.containsKey(companyid.toString())){
                        lres.get(companyid.toString()).add(reportMap);
                    }else{
                        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
                        list.add(reportMap);
                        lres.put(companyid.toString(),list);
                    }
                }

            }
        }
        return lres;
    }

//根据annualReportId得到shareHolder信息
    private Map<String,List<Map<String,Object>>> getAnnualReportShareHolderInfoByARId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select investor_name,paid_amount,paid_time,paid_type,subscribe_amount,subscribe_time,subscribe_type from report_shareholder where annual_report_id = ?";
            String shareholderSql="select annual_report_id,investor_name,paid_amount,paid_time,paid_type,subscribe_amount,subscribe_time,subscribe_type from report_shareholder where annual_report_id in ( select distinct id from annual_report where company_id>=? and company_id<? ) ";

            res= dbUtils.query(shareholderSql,new Object[]{start,end});
            return convertAnnualReportShareHolderData(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertAnnualReportShareHolderData(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();

        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("investorName",tmp.get("investor_name"));
                conMap.put("paidAmount",tmp.get("paid_amount"));
                conMap.put("paidTime",tmp.get("paid_time"));
                conMap.put("paidType",tmp.get("paid_type"));
                conMap.put("subscribeAmount",tmp.get("subscribe_amount"));
                conMap.put("subscribeTime",tmp.get("subscribe_time"));
                conMap.put("subscribeType",tmp.get("subscribe_type"));

                Object anReprotId= tmp.get("annual_report_id");
                if(anReprotId!=null && StringUtil.isNotEmpty(anReprotId.toString())){
                    String sAnReportId = anReprotId.toString();
                    if(allMap.containsKey(sAnReportId)){
                        allMap.get(sAnReportId).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allMap.put(sAnReportId,conList);
                    }
                }
            }
        }
        return allMap;
    }

    //根据annualReportId得到webInfo信息
    private Map<String,List<Map<String,Object>>> getWebInfoByAReportId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select web_type,name,website from report_webinfo WHERE annualreport_id= ?";
            String webInfoSql="select annualreport_id,web_type,name,website from report_webinfo WHERE annualreport_id in ( select distinct id from annual_report where company_id>=? and company_id<? )";
            res= dbUtils.query(webInfoSql,new Object[]{start,end});
            return convertWebInfoByAReportId(res);
        }
        return null;
    }
    private Map<String,List<Map<String,Object>>> convertWebInfoByAReportId(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allMap=new HashMap<String,List<Map<String,Object>>>();


        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("name",tmp.get("name"));
                conMap.put("website",tmp.get("website"));
                conMap.put("webType",tmp.get("web_type"));

                Object aid=tmp.get("annualreport_id");
                if(aid!=null && StringUtil.isNotEmpty(aid.toString())){
                    String said=aid.toString();
                    if(allMap.containsKey(said)){
                        allMap.get(said).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allMap.put(said,conList);
                    }
                }
            }
        }
        return allMap;
    }

    //根据annualReportId得到outBoundInvestmentInfo信息
    private Map<String,List<Map<String,Object>>> getoutBoundInvestmentByAReportId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start)&& end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select credit_code,outcompany_name,reg_num from report_outbound_investment where annual_report_id= ?";
            String outBoundSql="select annual_report_id,credit_code,outcompany_name,reg_num from report_outbound_investment where annual_report_id in ( select distinct id from annual_report where company_id>=? and company_id<? )";
            res= dbUtils.query(outBoundSql,new Object[]{start,end});
            return convertoutBoundInvestmentByAReportId(res);
        }
        return null;
    }

    private Map<String,List<Map<String,Object>>> convertoutBoundInvestmentByAReportId(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap=new HashMap<String,List<Map<String,Object>>>();


        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("creditCode",tmp.get("credit_code"));
                conMap.put("outcompanyName",tmp.get("outcompany_name"));
                conMap.put("regNum",tmp.get("reg_num"));
                Object aid = tmp.get("annual_report_id");
                if(aid!=null&& StringUtil.isNotEmpty(aid.toString())){
                    String sid=aid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else {
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allmap.put(sid,conList);
                    }
                }
            }
        }
        return allmap;
    }

    //根据annualReportId得到 outGuaranteeInfoList 信息
    private Map<String,List<Map<String,Object>>> getOutGuaranteeByAReportId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select creditor,credito_amount,credito_term,credito_type,guarantee_scope,guarantee_term,guarantee_way,obligor from report_out_guarantee_info where annualreport_id = ?";
            String sql="select annualreport_id,creditor,credito_amount,credito_term,credito_type,guarantee_scope,guarantee_term,guarantee_way,obligor from report_out_guarantee_info where annualreport_id in ( select distinct id from annual_report where company_id>=? and company_id<? )";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertOutGuaranteeByAReportId(res);
        }
        return null;
    }
    private Map<String,List<Map<String,Object>>> convertOutGuaranteeByAReportId(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap= new HashMap<String,List<Map<String,Object>>>();

        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("creditoAmount",tmp.get("credito_amount"));
                conMap.put("creditor",tmp.get("creditor"));
                conMap.put("creditoTerm",tmp.get("credito_term"));
                conMap.put("creditoType",tmp.get("credito_type"));
                conMap.put("guaranteeScope",tmp.get("guarantee_scope"));
                conMap.put("guaranteeTerm",tmp.get("guarantee_term"));
                conMap.put("guaranteeWay",tmp.get("guarantee_way"));
                conMap.put("obligor",tmp.get("obligor"));
                Object oid = tmp.get("annualreport_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid=oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>>  conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allmap.put(sid,conList);
                    }
                }
            }
        }
        return allmap;
    }

    //根据annulReportId得到equityChangeInfoList 信息
    private Map<String,List<Map<String,Object>>> getEquityChangeByAReportId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select change_time,investor_name,ratio_after,ratio_before from report_equity_change_info where annualreport_id = ?";
            String sql="select annualreport_id, change_time,investor_name,ratio_after,ratio_before from report_equity_change_info where annualreport_id in ( select distinct id from annual_report where company_id>=? and company_id<? )";
            res= dbUtils.query(sql,new Object[]{start,end});
            return convertEquityChangeByAReportId(res);
        }
        return null;
    }
    private Map<String,List<Map<String,Object>>> convertEquityChangeByAReportId(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap=new HashMap<String,List<Map<String,Object>>>();


        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("changeTime",tmp.get("change_time"));
                conMap.put("investorName",tmp.get("investor_name"));
                conMap.put("ratioAfter",tmp.get("ratio_after"));
                conMap.put("ratioBefore",tmp.get("ratio_before"));
                Object oid=tmp.get("annualreport_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allmap.put(sid,conList);
                    }
                }

            }
        }
        return allmap;
    }


    //根据annulReportId得到 changeRecordList信息
    private Map<String,List<Map<String,Object>>> getChangeRecordByAReportId(String start,String end){
        List<Map<String,Object>>  res=null;
        if(start!=null&& StringUtil.isNotEmpty(start) && end!=null&& StringUtil.isNotEmpty(end)){
//            String sql="select change_item,change_time,content_after,content_before from report_change_record where annualreport_id = ?";
            String sql="select annualreport_id, change_item,change_time,content_after,content_before from report_change_record where annualreport_id in ( select distinct id from annual_report where company_id>=? and company_id<? )";

            res= dbUtils.query(sql,new Object[]{start,end});
            return convertChangeRecordByAReportId(res);
        }
        return null;
    }
    private Map<String,List<Map<String,Object>>> convertChangeRecordByAReportId(List<Map<String,Object>> input){
        Map<String,List<Map<String,Object>>> allmap=new HashMap<String,List<Map<String,Object>>>();

        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){

            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("changeItem",tmp.get("change_item"));
                conMap.put("changeTime",tmp.get("change_time"));
                conMap.put("contentAfter",tmp.get("content_after"));
                conMap.put("contentBefore",tmp.get("content_before"));
                Object oid =tmp.get("annualreport_id");
                if(oid!=null && StringUtil.isNotEmpty(oid.toString())){
                    String sid= oid.toString();
                    if(allmap.containsKey(sid)){
                        allmap.get(sid).add(conMap);
                    }else{
                        List<Map<String,Object>> conList=new ArrayList<Map<String, Object>>();
                        conList.add(conMap);
                        allmap.put(sid,conList);
                    }
                }

            }
        }
        return allmap;
    }

}
