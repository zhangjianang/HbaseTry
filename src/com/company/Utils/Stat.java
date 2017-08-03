package com.company.Utils;

import java.util.List;

/**
 * Created by Administrator on 2016/5/27.
 */
public class Stat {

    public static double getAverage(List<Long> numList){
        double sum = 0;
        int num = 0;
        for(Long n:numList){
            sum += Double.valueOf(n.toString());
        }
        return (sum / numList.size());
    }


    public static double getStandardDeviation(List<Long> numList){
        double sum = 0;
        double avg = getAverage(numList);

        for(Long n:numList){
            sum += Math.pow((Double.valueOf(n.toString())-avg),2);
        }

        return Math.sqrt(sum / numList.size());
    }


    public static Long max(List<Long> numList){
        Long maxValue = Long.MIN_VALUE;
        for(Long n:numList){
            maxValue = (n>maxValue)?n:maxValue;
        }
        return maxValue ;
    }

    public static Long min(List<Long> numList){
        Long minValue = Long.MAX_VALUE;
        for(Long n:numList){
            minValue = (n<minValue)?n:minValue;
        }
        return minValue;
    }


}
