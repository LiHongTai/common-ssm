package com.roger.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class HttpClientUtil {
    // 编码格式。发送编码格式统一用UTF-8
    private static final String ENCODING = "UTF-8";
    // 设置连接超时时间，单位毫秒。
    private static final int CONNECT_TIMEOUT = 6000;
    // 请求获取数据的超时时间(即响应时间)，单位毫秒。
    private static final int SOCKET_TIMEOUT = 6000;

    /**
     * 发送get请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * 发送get请求；带请求参数
     *
     * @param url 请求地址
     * @param reqParam 请求参数集合
     * @return
     */
    public static JSONObject doGet(String url, JSONObject reqParam){
        return doGet(url, null, reqParam);
    }

    /**
     * 发送get请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param reqHeader 请求头集合
     * @param reqParam 请求参数集合
     * @return
     */
    public static JSONObject doGet(String url, JSONObject reqHeader, JSONObject reqParam){
        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http对象
        HttpGet httpGet = null;
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;
        try {
            // 创建访问的地址
            URIBuilder uriBuilder = new URIBuilder(url);
            // 封装请求参数
            if (reqParam != null) {
                Set<String> keySet = reqParam.keySet();
                for (String key : keySet) {
                    uriBuilder.setParameter(key,reqParam.getString(key));
                }
            }
            httpGet = new HttpGet(uriBuilder.build());

            /**
             * setConnectTimeout：设置连接超时时间，单位毫秒。
             * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection
             * 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
             * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
             */
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
            httpGet.setConfig(requestConfig);

            // 设置请求头
            packageHeader(reqHeader, httpGet);
        }catch (URISyntaxException e){
            log.error("doGet请求地址不合法，url = " + url,e);
        }

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpGet);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送post请求；不带请求头和请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject doPost(String url){
        return doPost(url, null, null);
    }

    /**
     * 发送post请求；带请求参数
     *
     * @param url 请求地址
     * @param reqParam 参数集合
     * @return
     */
    public static JSONObject doPost(String url, JSONObject reqParam) {
        return doPost(url, null, reqParam);
    }

    /**
     * 发送post请求；带请求头和请求参数
     *
     * @param url 请求地址
     * @param reqHeader 请求头集合
     * @param reqParam 请求参数集合
     * @return
     */
    public static JSONObject doPost(String url, JSONObject reqHeader, JSONObject reqParam){

        // 创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建http对象
        HttpPost httpPost = new HttpPost(url);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPost.setConfig(requestConfig);
        packageHeader(reqHeader, httpPost);

        // 封装请求参数
        packageParam(reqParam, httpPost);

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPost);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送put请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject doPut(String url){
        return doPut(url,null);
    }

    /**
     * 发送put请求；带请求参数
     *
     * @param url 请求地址
     * @param reqParam 参数集合
     * @return
     */
    public static JSONObject doPut(String url, JSONObject reqParam)  {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        // 创建httpResponse对象
        CloseableHttpResponse httpResponse = null;

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpPut.setConfig(requestConfig);


        packageParam(reqParam, httpPut);

        try {
            // 执行请求并获得响应结果
            return getHttpClientResult(httpResponse, httpClient, httpPut);
        } finally {
            // 释放资源
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；不带请求参数
     *
     * @param url 请求地址
     * @return
     */
    public static JSONObject doDelete(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpDelete.setConfig(requestConfig);

        CloseableHttpResponse httpResponse = null;
        try {
            return getHttpClientResult(httpResponse, httpClient, httpDelete);
        } finally {
            release(httpResponse, httpClient);
        }
    }

    /**
     * 发送delete请求；带请求参数
     *
     * @param url 请求地址
     * @param reqParam 参数集合
     * @return
     */
    public static JSONObject doDelete(String url, JSONObject reqParam) {
        if (reqParam == null) {
            reqParam = new JSONObject();
        }

        reqParam.put("_method", "delete");
        return doPost(url, reqParam);
    }

    /**
     *  封装请求头
     * @param reqHeader
     * @param httpMethod
     */
    private static void packageHeader(JSONObject reqHeader, HttpRequestBase httpMethod) {
        // 封装请求头
        if (reqHeader != null) {
            // 封装请求参数
            if (reqHeader != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Set<String> keySet = reqHeader.keySet();
                for (String key : keySet) {
                    // 设置到请求头到HttpRequestBase对象中
                    httpMethod.setHeader(key, reqHeader.getString(key));
                }
            }
        }
    }

    /**
     *  封装请求参数
     *
     * @param reqParam
     * @param httpMethod
     */
    public static void packageParam(JSONObject reqParam, HttpEntityEnclosingRequestBase httpMethod) {
        try{
            // 封装请求参数
            if (reqParam != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                Set<String> keySet = reqParam.keySet();
                for(String key : keySet){
                    NameValuePair nameValuePair = new BasicNameValuePair(key,reqParam.getString(key));
                    nvps.add(nameValuePair);
                }
                // 设置到请求的http对象中
                httpMethod.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
            }

        }catch (UnsupportedEncodingException e){
            log.error("远程调用请求参数编码异常", e);
        }

    }

    /**
     * 获得响应结果
     *
     * @param httpResponse
     * @param httpClient
     * @param httpMethod
     * @return
     */
    private static JSONObject getHttpClientResult(CloseableHttpResponse httpResponse,
                                                  CloseableHttpClient httpClient, HttpRequestBase httpMethod){
        JSONObject result = null;
        try {
            // 执行请求
            httpResponse = httpClient.execute(httpMethod);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(code == 200){
                HttpEntity httpEntity = httpResponse.getEntity();
                String returnString = EntityUtils.toString(httpEntity,ENCODING);
                result = JSON.parseObject(returnString);
            }
            if(result == null){
                result = new JSONObject();
            }
            result.put("returnHttpCode",code);
        } catch (IOException e) {
            log.error("执行Http请求I/O异常", e);
        }
        return result;
    }

    /**
     * 释放资源
     *
     * @param httpResponse
     * @param httpClient
     */
    private static void release(CloseableHttpResponse httpResponse, CloseableHttpClient httpClient) {
        try {
            // 释放资源
            if (httpResponse != null) {
                httpResponse.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            log.error("远程调用IO流关闭异常", e);
        }
    }

}
