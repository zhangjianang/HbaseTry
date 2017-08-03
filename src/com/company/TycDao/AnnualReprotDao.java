package com.company.TycDao;


import com.company.DBUtils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/19.
 */
public class AnnualReprotDao {
    private DBUtils dbUtils;
    public AnnualReprotDao(){
        this.dbUtils=new DBUtils();
    }

    //根据companId 获得AnnualReport基本信息
    public List<Map<String,Object>> getAnnualReportListBaseInfo(String companyId){
        if(companyId!=null&&StringUtil.isNotEmpty(companyId)) {
            String sql = "select id,company_name,credit_code,email,employee_num,manage_state,operator_name,phone_number,postal_address,postcode,prime_bus_profit" +
                    ",reg_number,report_year,retained_profit,total_assets,total_equity,total_liability,total_profit,total_sales,total_tax from annual_report where company_id=?";
            List<Map<String,Object>> res = null;
            res = dbUtils.query(sql,companyId);
            return convertAnnualBaseData(res);
        }
        return null;
    }
    //转换查询结果
    private List<Map<String,Object>> convertAnnualBaseData(List<Map<String,Object>> input){
        Map<String,Object> dataResult=null;

        Map<String,Object> reportMap=null;

        List<Map<String,Object>> lres=null;
        if(input!=null&&input.size()>0) {
            lres=new ArrayList<Map<String, Object>>();
            for (Map<String, Object> tmpMap : input) {
                dataResult = new HashMap<String,Object>();
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
                reportMap.put("shareholderList",getAnnualReportShareHolderInfoByARId(aReportId));
                reportMap.put("webInfoList",getWebInfoByAReportId(aReportId));
                reportMap.put("outboundInvestmentList",getoutBoundInvestmentByAReportId(aReportId));
                reportMap.put("outGuaranteeInfoList",getOutGuaranteeByAReportId(aReportId));
                reportMap.put("equityChangeInfoList",getEquityChangeByAReportId(aReportId));
                reportMap.put("changeRecordList",getChangeRecordByAReportId(aReportId));
                lres.add(reportMap);
            }
        }
        return lres;
    }

//根据annualReportId得到shareHolder信息
    private List<Map<String,Object>> getAnnualReportShareHolderInfoByARId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select investor_name,paid_amount,paid_time,paid_type,subscribe_amount,subscribe_time,subscribe_type from report_shareholder where annual_report_id = ?";
            res= dbUtils.query(sql,annualReportId);
            return convertAnnualReportShareHolderData(res);
        }
        return res;
    }

    private List<Map<String,Object>> convertAnnualReportShareHolderData(List<Map<String,Object>> input){
        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("investorName",tmp.get("investor_name"));
                conMap.put("paidAmount",tmp.get("paid_amount"));
                conMap.put("paidTime",tmp.get("paid_time"));
                conMap.put("paidType",tmp.get("paid_type"));
                conMap.put("subscribeAmount",tmp.get("subscribe_amount"));
                conMap.put("subscribeTime",tmp.get("subscribe_time"));
                conMap.put("subscribeType",tmp.get("subscribe_type"));
                conList.add(conMap);
            }
        }
        return conList;
    }

    //根据annualReportId得到webInfo信息
    private List<Map<String,Object>> getWebInfoByAReportId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select web_type,name,website from report_webinfo WHERE annualreport_id= ?";
            res= dbUtils.query(sql,annualReportId);
            return convertWebInfoByAReportId(res);
        }
        return res;
    }
    private List<Map<String,Object>> convertWebInfoByAReportId(List<Map<String,Object>> input){

        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("name",tmp.get("name"));
                conMap.put("website",tmp.get("website"));
                conMap.put("webType",tmp.get("web_type"));
                conList.add(conMap);
            }
        }
        return conList;
    }

    //根据annualReportId得到outBoundInvestmentInfo信息
    private List<Map<String,Object>> getoutBoundInvestmentByAReportId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select credit_code,outcompany_name,reg_num from report_outbound_investment where annual_report_id= ?";
            res= dbUtils.query(sql,annualReportId);
            return convertoutBoundInvestmentByAReportId(res);
        }
        return res;
    }
    private List<Map<String,Object>> convertoutBoundInvestmentByAReportId(List<Map<String,Object>> input){

        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("creditCode",tmp.get("credit_code"));
                conMap.put("outcompanyName",tmp.get("outcompany_name"));
                conMap.put("regNum",tmp.get("reg_num"));
                conList.add(conMap);
            }
        }
        return conList;
    }

    //根据annualReportId得到 outGuaranteeInfoList 信息
    private List<Map<String,Object>> getOutGuaranteeByAReportId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select creditor,credito_amount,credito_term,credito_type,guarantee_scope,guarantee_term,guarantee_way,obligor from report_out_guarantee_info where annualreport_id = ?";
            res= dbUtils.query(sql,annualReportId);
            return convertOutGuaranteeByAReportId(res);
        }
        return res;
    }
    private List<Map<String,Object>> convertOutGuaranteeByAReportId(List<Map<String,Object>> input){

        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
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
                conList.add(conMap);
            }
        }
        return conList;
    }

    //根据annulReportId得到equityChangeInfoList 信息
    private List<Map<String,Object>> getEquityChangeByAReportId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select change_time,investor_name,ratio_after,ratio_before from report_equity_change_info where annualreport_id = ?";
            res= dbUtils.query(sql,annualReportId);
            return convertEquityChangeByAReportId(res);
        }
        return res;
    }
    private List<Map<String,Object>> convertEquityChangeByAReportId(List<Map<String,Object>> input){

        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("changeTime",tmp.get("change_time"));
                conMap.put("investorName",tmp.get("investor_name"));
                conMap.put("ratioAfter",tmp.get("ratio_after"));
                conMap.put("ratioBefore",tmp.get("ratio_before"));
                conList.add(conMap);
            }
        }
        return conList;
    }


    //根据annulReportId得到 changeRecordList信息
    private List<Map<String,Object>> getChangeRecordByAReportId(String annualReportId){
        List<Map<String,Object>>  res=null;
        if(annualReportId!=null&& StringUtil.isNotEmpty(annualReportId)){
            String sql="select change_item,change_time,content_after,content_before from report_change_record where annualreport_id = ?";
            res= dbUtils.query(sql,annualReportId);
            return convertChangeRecordByAReportId(res);
        }
        return res;
    }
    private List<Map<String,Object>> convertChangeRecordByAReportId(List<Map<String,Object>> input){

        List<Map<String,Object>> conList=null;
        Map<String,Object> conMap=null;
        if(input!=null&&input.size()>0){
            conList=new ArrayList<Map<String, Object>>();
            for(Map<String,Object> tmp:input) {
                conMap=new HashMap();
                conMap.put("changeItem",tmp.get("change_item"));
                conMap.put("changeTime",tmp.get("change_time"));
                conMap.put("contentAfter",tmp.get("content_after"));
                conMap.put("contentBefore",tmp.get("content_before"));
                conList.add(conMap);
            }
        }
        return conList;
    }

}
