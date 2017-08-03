package com.company;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.jruby.compiler.ir.operands.Label.index;

/**
 * Created by adimn on 2017/5/27.
 */
public class MultiTry {

    public static void main(String[] args) {
//        args= new String[]{"1","100","5"};
        Integer start = Integer.parseInt(args[0]);
        Integer end = Integer.parseInt(args[1]);
        Integer threadNum = Integer.parseInt(args[2]);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        
        splitData(start,end,threadNum);

        for (int i = start; i <= end; i++) {
            final int id = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                     Main.dealOne(id+"");
                }
            });
        }

        fixedThreadPool.shutdown();
    }

    private static List<Model> splitData(Integer start, Integer end, Integer threadNum) {

        return null;
    }
}