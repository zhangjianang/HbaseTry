package com.company.batchDetail;

import com.company.ReadIndustryTxt;
import com.company.TycDao.StringUtil;
import com.company.batchDao.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
public class BatchSearchDetailInfo {
    private BatchCompanyDao companyDao;
    private BatchCompanyPunishDao punishDao;
    private BatchCompanyChageInfoDao companyChageInfoDao;
    private BatchCompanyCheckInfoDao companyCheckInfoDao;
    private BatchCompanyEquityDao companyEquityDao;
    private BatchCompanyInvestorDao companyInvestorDao;
    private BatchCompanyStaffDao companyStaffDao;
    private BatchCompanyBranchDao companyBranchDao;

    private BatchAnnualReprotDao annualReprotDao;
    private BatchMortgageInfoDao mortgageInfoDao;
    private BatchCompanyAbnormalDao companyAbnormalDao;

    public BatchSearchDetailInfo(){
        companyDao=new BatchCompanyDao();
        punishDao=new BatchCompanyPunishDao();
        companyChageInfoDao=new BatchCompanyChageInfoDao();
        companyCheckInfoDao=new BatchCompanyCheckInfoDao();
        companyEquityDao=new BatchCompanyEquityDao();
        companyInvestorDao=new BatchCompanyInvestorDao();
        companyStaffDao=new BatchCompanyStaffDao();
        companyBranchDao=new BatchCompanyBranchDao();

        annualReprotDao=new BatchAnnualReprotDao();
        mortgageInfoDao=new BatchMortgageInfoDao();
        companyAbnormalDao=new BatchCompanyAbnormalDao();
    }

    public Map<String,Object> getDetailInfoByCompanyId(String begin, String end){
        Map<String,Object> finalResMap=new HashMap<String,Object>();

        Map<String,Map<String,Object>> resultMap=null;
        Map<String,Map<String,Object>> resuRealltMap=new HashMap<String,Map<String,Object>>();

        long stepTime = new Date().getTime();
            //插入基本信息

            resultMap=companyDao.getCompanyInfo(begin,end);
        System.out.println(":"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            resuRealltMap.putAll(resultMap);


        Map<String, String> companyCategoryMap = companyDao.getCompanyCategoryCodeByCompanyId(begin, end);

        Map<String,List<Map<String,Object>>> annualReproAllMap  = annualReprotDao.getAnnualReportListBaseInfo(begin,end);
        System.out.println("annualReprotDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyBranchAllMap  = companyBranchDao.getCompanyBranchByCompanyId(begin,end);
        System.out.println("companyBranchDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyCheckAllMap= companyCheckInfoDao.getCheckInfoById(begin,end);
        System.out.println("companyCheckInfoDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyChangeAllMap = companyChageInfoDao.getCompanyChangeInfoByCompanyId(begin,end);
        System.out.println("companyChageInfoDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyAbnormalAllMap= companyAbnormalDao.getCompanyAbnormalByCompanyId(begin,end);
        System.out.println("companyAbnormalDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();

            Map<String,List<Map<String,Object>>> companyEquityAllMap = companyEquityDao.getEquityInfoByCompanyId(begin,end);
        System.out.println("companyEquityDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();

            Map<String,List<Map<String,Object>>> companyInvestorAllMap = companyInvestorDao.getCompanyInvestorByCompanyId(begin,end);
        System.out.println("companyInvestorDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyMortgageAllMap =  mortgageInfoDao.getMortgageInfoByCompanyId(begin,end);
        System.out.println("mortgageInfoDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyPunishAllMap = punishDao.getPunishInfoByCompanyId(begin,end);
        System.out.println("punishDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            Map<String,List<Map<String,Object>>> companyStaffAllMap = companyStaffDao.getCompanyStaffByCompanyId(begin,end);
        System.out.println("companyStaffDao:"+(new Date().getTime()-stepTime));
        stepTime = new Date().getTime();
            if(resultMap!=null) {

                for (Map.Entry<String, Map<String, Object>> entry : resuRealltMap.entrySet()) {
                    Map<String,Object> allResMap=new HashMap<String, Object>();
                    String companyid = entry.getKey();
                    Map<String, Object> valueMap = entry.getValue();
                    if (annualReproAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("annualReportList", annualReproAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("annualReportList", "");
                    }

                    if (companyBranchAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("branchList", companyBranchAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("branchList", "");
                    }

                    if (companyCheckAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("checkList", companyCheckAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("checkList", "");
                    }

                    if (companyChangeAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("comChanInfoList", companyChangeAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("comChanInfoList", "");
                    }

                    if (companyAbnormalAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("comAbnoInfoList", companyAbnormalAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("comAbnoInfoList", "");
                    }

                    if (companyEquityAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("equityList", companyEquityAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("equityList", "");
                    }

                    if (companyInvestorAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("investorListAll", companyInvestorAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("investorListAll", "");
                    }


                    if (companyMortgageAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("mortgageList", companyMortgageAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("mortgageList", "");
                    }

                    if (companyPunishAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("punishList", companyPunishAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("punishList", "");
                    }

                    if (companyStaffAllMap.containsKey(companyid)) {
                        resultMap.get(companyid).put("staffListAll", companyStaffAllMap.get(companyid));
                    } else {
                        resultMap.get(companyid).put("staffListAll", "");
                    }

                    if(companyCategoryMap.containsKey(companyid)){
                        resultMap.get(companyid).put("category_code", companyCategoryMap.get(companyid));

                            Object inStr = ReadIndustryTxt.p.get( companyCategoryMap.get(companyid)); /*ReadIndustryTxt.getModelByCode(conMap.get("category_code").toString());*/
                            if(inStr!=null) {
                                resultMap.get(companyid).put("industry",inStr.toString());
                            }else{
                                resultMap.get(companyid).put("industry","");
                            }

                    }else{
                        resultMap.get(companyid).put("category_code", "");
                        resultMap.get(companyid).put("industry","");
                    }


                    resultMap.put("illegalList", null);//非工商数据
                    resultMap.put("investList", null);

                    allResMap.put("error_code", 0);
                    allResMap.put("reason", "ok");
                    allResMap.put("result", valueMap);

                    finalResMap.put(companyid,allResMap);

                }
                System.out.println("merage:"+(new Date().getTime()-stepTime));
                stepTime = new Date().getTime();
            }
        return finalResMap;
    }



    private  String createInSql(String id, String end) {
        Integer intEnd = Integer.parseInt(end);
        Integer start = Integer.parseInt(id);
        StringBuilder sb = new StringBuilder("in (");
        for(int i = start ;i<=intEnd;i++){
            sb.append(i+",");
        }

        String sql =  sb.toString();
        sql= sql.substring(0,sql.length()-1)+" );";
        return sql;
    }
}
