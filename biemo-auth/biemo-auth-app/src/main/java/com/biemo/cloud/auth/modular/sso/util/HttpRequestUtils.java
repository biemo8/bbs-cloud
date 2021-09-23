package com.biemo.cloud.auth.modular.sso.util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLDecoder;

/**
 * HttpClient 工具类
 *
 *
 * @Date 2019-09-27 19:34
 */
@Slf4j
public class HttpRequestUtils {

    /**
     * httpPost
     *
     * @param url       路径
     * @param jsonParam 参数
     *
     * @Date 2019-09-27 19:34
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, String token) {
        return httpPost(url, jsonParam, token, false);
    }

    /**
     * post请求
     *
     * @param url            url地址
     * @param jsonParam      参数
     * @param noNeedResponse 不需要返回结果
     *
     * @Date 2019-09-27 19:34
     */
    public static JSONObject httpPost(String url, JSONObject jsonParam, String token, boolean noNeedResponse) {
        //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);//超时时间毫秒
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);

        if (null != jsonParam) {
            //解决中文乱码问题
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            method.setEntity(entity);
            method.setHeader(JwtConstants.AUTH_HEADER, token);
        }

        //失败重试
        int retryCount = 0;
        while (true) {
            try {
                HttpResponse result = httpClient.execute(method);
                url = URLDecoder.decode(url, "UTF-8");
                // 请求发送成功，并得到响应
                if (result.getStatusLine().getStatusCode() == 200) {
                    String str;

                    //读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                    //把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                    log.info("web-service post请求系统退出地址成功！");
                    break;
                }
            } catch (Exception e) {
                if (retryCount > 2) {
                    log.error("web-service post请求超过最大重复次数!失败!!!", e);
                    break;
                }
                retryCount++;
                log.error("web-service post请求提交失败{},重试第{}次", url, retryCount);
            }
        }
        httpClient.close();
        return jsonResult;
    }


    /**
     * 发送get请求
     *
     * @param url 路径
     *
     * @Date 2019-09-27 19:34
     */
    public static JSONObject httpGet(String url) {
        //get请求返回结果
        JSONObject jsonResult = null;
        try {
            DefaultHttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.parseObject(strResult);
                url = URLDecoder.decode(url, "UTF-8");
            } else {
                log.error("get请求提交失败:" + url);
            }
        } catch (IOException e) {
            log.error("get请求提交失败:" + url, e);
        }
        return jsonResult;
    }
}
