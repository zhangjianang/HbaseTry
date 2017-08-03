package com.company.Utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/13.
 */
public class Common {
    public static String timestamps2str(Long timestamps,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Timestamp(timestamps);
        return sdf.format(date);
    }


    public static Date formatDate(Object date){
        if(date == null){
            return null;
        }
        String sDate = date.toString();
        try {
            if(sDate.matches("[\\d]+年[\\d]+月[\\d]+日")){

                return (new SimpleDateFormat("yyyy年M月dd日")).parse(sDate);

            }else if(sDate.matches("[\\d]+-[\\d]+-[\\d]+")){
                return (new SimpleDateFormat("yyyy-MM-dd")).parse(sDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
