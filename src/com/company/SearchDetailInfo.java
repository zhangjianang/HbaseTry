package com.company;

import com.company.TycDao.*;

import com.company.TycDao.StringUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by adimn on 2017/5/22.
 */
public class SearchDetailInfo {
    private CompanyDao companyDao;
    private CompanyPunishDao punishDao;
    private CompanyChageInfoDao companyChageInfoDao;
    private CompanyCheckInfoDao companyCheckInfoDao;
    private CompanyEquityDao companyEquityDao;
    private CompanyInvestorDao companyInvestorDao;
    private CompanyStaffDao companyStaffDao;
    private CompanyBranchDao companyBranchDao;

    private AnnualReprotDao annualReprotDao;
    private MortgageInfoDao mortgageInfoDao;
    private CompanyAbnormalDao companyAbnormalDao;

    public SearchDetailInfo(){
        companyDao=new CompanyDao();
        punishDao=new CompanyPunishDao();
        companyChageInfoDao=new CompanyChageInfoDao();
        companyCheckInfoDao=new CompanyCheckInfoDao();
        companyEquityDao=new CompanyEquityDao();
        companyInvestorDao=new CompanyInvestorDao();
        companyStaffDao=new CompanyStaffDao();
        companyBranchDao=new CompanyBranchDao();

        annualReprotDao=new AnnualReprotDao();
        mortgageInfoDao=new MortgageInfoDao();
        companyAbnormalDao=new CompanyAbnormalDao();
    }

    public Map<String,Object> getDetailInfoByCompanyId(String companyId){
        Map<String,Object> resultMap=null;
        Map<String,Object> allResMap=new HashMap<String, Object>(3);
        if(companyId!=null&&StringUtil.isNotEmpty(companyId)){
            //插入基本信息
            resultMap=companyDao.getCompanyInfo(companyId);
            if(resultMap!=null){
                //增加其他信息
                resultMap.put("annualReportList",annualReprotDao.getAnnualReportListBaseInfo(companyId));
                resultMap.put("branchList",companyBranchDao.getCompanyBranchByCompanyId(companyId));
                resultMap.put("checkList",companyCheckInfoDao.getCheckInfoById(companyId));
                resultMap.put("comChanInfoList",companyChageInfoDao.getCompanyChangeInfoByCompanyId(companyId));
                resultMap.put("comAbnoInfoList",companyAbnormalDao.getCompanyAbnormalByCompanyId(companyId));
                resultMap.put("equityList",companyEquityDao.getEquityInfoByCompanyId(companyId));
                resultMap.put("illegalList",null);//非工商数据
                resultMap.put("investList",null);//非工商数据
                resultMap.put("investorListAll",companyInvestorDao.getCompanyInvestorByCompanyId(companyId));
                resultMap.put("mortgageList",mortgageInfoDao.getMortgageInfoByCompanyId(companyId));
                resultMap.put("punishList",punishDao.getPunishInfoByCompanyId(companyId));
                resultMap.put("staffListAll",companyStaffDao.getCompanyStaffByCompanyId(companyId));

                allResMap.put("error_code",0);
                allResMap.put("reason","ok");
                allResMap.put("result",resultMap);
            }else{
                allResMap.put("error_code",300001);
                allResMap.put("reason","result无数据");
                allResMap.put("result",null);
            }
        }else{
            allResMap.put("error_code",300001);
            allResMap.put("reason","无数据");
            allResMap.put("result",null);
        }
        return allResMap;
    }
}
