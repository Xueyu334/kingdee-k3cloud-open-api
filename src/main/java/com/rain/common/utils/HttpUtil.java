package com.rain.common.utils;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import javax.net.ssl.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * http工具类
 *
 * @author xueyu
 */
public class HttpUtil {

    /**
     * 不对主机名进行验证 绕过 SSL/TLS 证书验证
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    /**
     * SSL 信任所有证书
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }};

    /**
     * 发送http get请求
     *
     * @param httpUrl        请求地址
     * @param requestHeaders 请求头部信息
     * @return 响应信息
     * @throws IOException io异常 网络异常
     */
    public static String doGet(String httpUrl, Map<String, String> requestHeaders) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            //构建请求URL
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            //是否使用了https
            boolean useHttps = httpUrl.startsWith("https");
            if (useHttps) {
                HttpsURLConnection https = (HttpsURLConnection) httpURLConnection;
                trustAllHosts(https);
                https.setHostnameVerifier(DO_NOT_VERIFY);
            }
            httpURLConnection.setRequestMethod("GET");
            //设置在打开指向此URLConnection引用的资源的通信链接时要使用的指定超时值 (以毫秒为单位)
            httpURLConnection.setConnectTimeout(5000);
            //将读取超时设置为指定的超时 (以毫秒为单位)。
            httpURLConnection.setReadTimeout(5000);
            //设置请求头部信息
            if (requestHeaders != null && !requestHeaders.isEmpty()) {
                Set<Map.Entry<String, String>> entries = requestHeaders.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpURLConnection.setRequestProperty(key, value);
                }
            }
            // 发送请求并获取响应代码
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                StringBuilder stringBuilder = new StringBuilder();
                byte[] b = new byte[2048];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(b)) != -1) {
                    String str = new String(b, 0, bytesRead, StandardCharsets.UTF_8);
                    stringBuilder.append(str);
                }
                return stringBuilder.toString();
            } else {
                String errorMessage = responseCode + ": " + httpURLConnection.getResponseMessage();
                System.out.println(errorMessage);
                throw new IOException(errorMessage);
            }
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * SSL 信任所有的证书
     *
     * @param connection 连接
     * @return SSLSocketFactory
     */
    private static SSLSocketFactory trustAllHosts(HttpsURLConnection connection) {
        SSLSocketFactory oldFactory = connection.getSSLSocketFactory();
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.3");
            sc.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory newFactory = sc.getSocketFactory();
            connection.setSSLSocketFactory(newFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oldFactory;
    }

    /**
     * 发送http post请求
     *
     * @param httpUrl        请求地址
     * @param jsonBody       请求体参数
     * @param requestHeaders 请求头信息
     * @return 响应信息
     * @throws IOException IO异常 网络异常
     */
    public static String doPostJson(String httpUrl, String jsonBody, Map<String, String> requestHeaders) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            boolean useHttps = httpUrl.startsWith("https");
            if (useHttps) {
                HttpsURLConnection https = (HttpsURLConnection) httpURLConnection;
                trustAllHosts(https);
                https.setHostnameVerifier(DO_NOT_VERIFY);
            }
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            if (requestHeaders != null && !requestHeaders.isEmpty()) {
                Set<Map.Entry<String, String>> entries = requestHeaders.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    httpURLConnection.setRequestProperty(key, value);
                }
            }
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            //DoOutput设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            //DoInput设置是否从httpUrlConnection读入
            httpURLConnection.setDoInput(true);
            // 发送请求体
            if (jsonBody != null && !jsonBody.isEmpty()) {
                try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream())) {
                    bufferedOutputStream.write(jsonBody.getBytes(StandardCharsets.UTF_8));
                    bufferedOutputStream.flush();
                }
            }
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                StringBuilder stringBuilder = new StringBuilder();
                byte[] b = new byte[2048];
                int bytesRead;
                while ((bytesRead = bufferedInputStream.read(b)) != -1) {
                    String str = new String(b, 0, bytesRead, StandardCharsets.UTF_8);
                    stringBuilder.append(str);
                }
                return stringBuilder.toString();
            } else {
                String errorMessage = responseCode + ": " + httpURLConnection.getResponseMessage();
                System.out.println(errorMessage);
                throw new IOException(errorMessage);
            }
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 利用 apache httpClient 发送 http post 请求
     *
     * @param url      地址
     * @param jsonBody json请求数据
     * @param headers  请求头
     * @return 响应数据 json字符
     * @throws IOException io异常
     */
    public static String sendPostJson(String url, String jsonBody, Map<String, String> headers) throws IOException {
        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault();) {
            HttpPost httpPost = new HttpPost(url);
            Optional.ofNullable(headers)
                    .filter(h -> !h.isEmpty())
                    .ifPresent(h -> h.forEach(httpPost::setHeader));
            httpPost.setEntity(new StringEntity(jsonBody,
                    ContentType.APPLICATION_JSON,
                    StandardCharsets.UTF_8.name(), false));
            return closeableHttpClient.execute(httpPost, classicHttpResponse -> {
                HttpEntity entity = classicHttpResponse.getEntity();
                return EntityUtils.toString(entity);
            });
        }
    }


    public static void main(String[] args) throws IOException {
        String url = "http://apis.juhe.cn/fapigw/air/provinces?key=81a32ae376a7783786d869522d39b643";
        String s = sendPostJson(url, "", null);
        System.out.println(s);
    }
}
