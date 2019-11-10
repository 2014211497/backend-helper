package net.engyne.backend.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String get(String url) throws IOException {
        return HttpUtil.get(url, new JSONObject());
    }

    public static String get(String url, JSONObject settings) throws IOException {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        IOException error = null;
        try {
            // 创建远程url连接对象
            URL urlObj = new URL(url);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) urlObj.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(((Integer) settings.getOrDefault("connectTimeout", 15000)));
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(((Integer) settings.getOrDefault("readTimeout", 60000)));
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, ((String) settings.getOrDefault("charset", "UTF-8"))));
                // 存放数据
                StringBuilder sbf = new StringBuilder();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\n");
                }
                result = sbf.toString();
            }
        } catch (IOException e) {
            error = e;
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                error = e;
            }
        }
        if (error != null) {
            throw error;
        }
        return result;
    }

    public static String post(String url, String params) throws Exception {
        String result = "";
        Exception error = null;
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            BasicResponseHandler handler = new BasicResponseHandler();
            StringEntity entity = new StringEntity(params, "utf-8");
            entity.setContentEncoding("utf-8");
            entity.setContentType("application/json");
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(entity);
            result = client.execute(httpPost, handler);
        } catch (Exception e) {
            error = e;
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                error = e;
            }
        }
        if (error != null) {
            throw error;
        }
        return result;
    }
}
