package com.company.Utils;

/**
 * Created by Administrator on 2016/5/19.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

public class HttpRequestUtils {
    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);    //日志记录

    private static CloseableHttpClient createHttpClient(){
        RequestConfig.Builder requestBuilder = RequestConfig.custom();
        requestBuilder = requestBuilder.setConnectTimeout(1000);
        requestBuilder = requestBuilder.setConnectionRequestTimeout(1000);
        requestBuilder = requestBuilder.setSocketTimeout(1000);
        return HttpClientBuilder.create().setDefaultRequestConfig(requestBuilder.build()).build();
    }

    /**
     * httpPost
     * @param url  路径
     * @param jsonParam 参数
     * @return
     */
    public static String httpPost(String url,JSONObject jsonParam){
        return httpPost(url, jsonParam, false);
    }

    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static String httpPost(String url,JSONObject jsonParam, boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = createHttpClient();
        HttpPost method = new HttpPost(url);
        String strResult = null;
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {

                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return strResult;
    }


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static String httpPost(String url,JSONObject jsonParam, Map<String,String> headers,boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = createHttpClient();
        HttpPost method = new HttpPost(url);
        String strResult = null;
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");

                for(Map.Entry<String,String> entry:headers.entrySet()){
                    method.setHeader(entry.getKey(),entry.getValue());
                }
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {

                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return strResult;
    }


    /**
     * post请求
     * @param url         url地址
     * @param jsonParam     参数
     * @param noNeedResponse    不需要返回结果
     * @return
     */
    public static String httpDelete(String url,JSONObject jsonParam,Map<String,String> headers,boolean noNeedResponse){
        //post请求返回结果
        CloseableHttpClient httpClient = createHttpClient();
        HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);

        String strResult = null;
        try {
            if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpDeleteWithBody.setHeader("Content-Type", "application/json; charset=UTF-8");

                for(Map.Entry<String,String> entry:headers.entrySet()){
                    httpDeleteWithBody.setHeader(entry.getKey(),entry.getValue());
                }
                httpDeleteWithBody.setEntity(entity);

            }

            HttpResponse result = httpClient.execute(httpDeleteWithBody);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {

                try {
                    /**读取服务器返回过来的json字符串数据**/
                    strResult = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }

                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
        return strResult;
    }

    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url){
        //get请求返回结果
        String strResult = null;

        try {
            CloseableHttpClient httpClient = createHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity());
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }


    /**
     * 发送get请求
     * @param url    路径
     * @return
     */
    public static String httpGet(String url,Map<String,String> headers){
        //get请求返回结果
        String strResult = null;

        try {
            CloseableHttpClient httpClient = createHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);

            for(Map.Entry<String,String> entry:headers.entrySet()){
                request.setHeader(entry.getKey(),entry.getValue());
            }
            HttpResponse response = httpClient.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                strResult = EntityUtils.toString(response.getEntity(),Charset.forName("UTF8"));
            } else {
                logger.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            logger.error("get请求提交失败:" + url, e);
        }
        return strResult;
    }
}
