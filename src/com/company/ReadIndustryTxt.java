package com.company;

import org.jruby.RubyProcess;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Created by adimn on 2017/5/27.
 */
public class ReadIndustryTxt {
    public static Properties p = new Properties();
    static{
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("categoryCodeNew.properties");
        try {
            p.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPropertyByParam(String filename,String colum){
        Properties rp=new Properties();
        String rstr=null;
        if(filename!=null&&colum!=null) {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            try {
                rp.load(is);
                rstr = rp.getProperty(colum);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rstr;
    }

    public static void main(String[] args) {
     //  System.out.println(getIndustryModelMap());
        System.out.println(ReadIndustryTxt.p.get("651"));
        System.out.println(getPropertyByParam("categoryCodeNew.properties","651"));
    }
}
