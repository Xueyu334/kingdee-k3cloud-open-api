package com.rain.common.utils;

import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
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
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * HTTP 工具类，提供发送 HTTP GET 和 POST 请求的功能。
 * 支持配置 SSL 证书验证的启用与禁用，便于在开发测试环境中绕过证书验证。
 * 注意：禁用 SSL 证书验证会带来安全风险，仅建议在非生产环境中使用。
 *
 * @author xueyu
 */
public class HttpUtil {

    /**
     * 不对主机名进行验证的 HostnameVerifier (仅在 disableSslVerification=true 时使用)
     */
    private static final HostnameVerifier INSECURE_HOSTNAME_VERIFIER = (hostname, session) -> true;
    /**
     * 信任所有证书的 TrustManager (仅在 disableSslVerification=true 时使用)
     */
    private static final TrustManager[] INSECURE_TRUST_MANAGERS = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    // 信任所有客户端证书
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    // 信任所有服务端证书
                }
            }
    };
    /**
     * 是否禁用 SSL 证书验证
     * 警告: 禁用证书验证会导致安全风险,仅在开发/测试环境使用
     * 生产环境必须设置为 false
     * -- GETTER --
     * 获取当前 SSL 证书验证状态
     */
    @Getter
    private static volatile boolean disableSslVerification = false;

    /**
     * SSL 上下文缓存 (用于性能优化)
     */
    private static volatile SSLContext insecureSSLContext;

    /**
     * 设置是否禁用 SSL 证书验证
     *
     * @param disable true=禁用证书验证(不安全), false=启用证书验证(默认,推荐)
     * @throws IllegalStateException 如果在生产环境尝试禁用证书验证
     */
    public static void setDisableSslVerification(boolean disable) {
        if (disable) {
            System.err.println("[SECURITY WARNING] SSL证书验证已被禁用! 这会导致中间人攻击风险,仅应在开发/测试环境使用!");
        }
        disableSslVerification = disable;
        // 清除缓存的 SSL 上下文
        insecureSSLContext = null;
    }

    /**
     * 发送http get请求
     *
     * @param httpUrl        请求地址
     * @param requestHeaders 请求头部信息
     * @return 响应信息
     * @throws IOException io异常 网络异常
     */
    public static String get(String httpUrl, Map<String, String> requestHeaders) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            //构建请求URL
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 配置 HTTPS 连接
            if (httpUrl.startsWith("https")) {
                configureHttpsConnection((HttpsURLConnection) httpURLConnection);
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
                } catch (Exception ignored) {
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * 配置 HTTPS 连接的 SSL 设置
     *
     * @param connection HTTPS 连接对象
     */
    private static void configureHttpsConnection(HttpsURLConnection connection) {
        if (disableSslVerification) {
            // 禁用证书验证模式 (不安全)
            try {
                if (insecureSSLContext == null) {
                    synchronized (HttpUtil.class) {
                        if (insecureSSLContext == null) {
                            insecureSSLContext = SSLContext.getInstance("TLS");
                            insecureSSLContext.init(null, INSECURE_TRUST_MANAGERS, new SecureRandom());
                        }
                    }
                }
                connection.setSSLSocketFactory(insecureSSLContext.getSocketFactory());
                connection.setHostnameVerifier(INSECURE_HOSTNAME_VERIFIER);
            } catch (Exception e) {
                throw new RuntimeException("配置不安全的SSL上下文失败", e);
            }
        }
        // 启用证书验证时使用默认配置,无需额外设置
    }

    /**
     * 发送http post请求 (使用 Apache HttpClient)
     *
     * @param httpUrl        请求地址
     * @param jsonBody       请求体参数
     * @param requestHeaders 请求头信息
     * @return 响应信息
     * @throws IOException IO异常 网络异常
     */
    public static String post(String httpUrl, String jsonBody, Map<String, String> requestHeaders) throws IOException {
        return postWithApacheHttpClient(httpUrl, jsonBody, requestHeaders);
    }

    /**
     * 发送http post请求 (使用 HttpURLConnection)
     *
     * @param httpUrl        请求地址
     * @param jsonBody       请求体参数
     * @param requestHeaders 请求头信息
     * @return 响应信息
     * @throws IOException IO异常 网络异常
     * @deprecated 建议使用 {@link #post(String, String, Map)} 方法
     */
    @Deprecated
    public static String postWithHttpURLConnection(String httpUrl, String jsonBody, Map<String, String> requestHeaders) throws IOException {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            URL url = new URL(httpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 配置 HTTPS 连接
            if (httpUrl.startsWith("https")) {
                configureHttpsConnection((HttpsURLConnection) httpURLConnection);
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
                } catch (Exception ignored) {
                }
            }
            if (httpURLConnection != null) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception ignored) {
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
    private static String postWithApacheHttpClient(String url, String jsonBody, Map<String, String> headers) throws IOException {
        // 根据配置创建 HttpClient
        CloseableHttpClient client;
        if (disableSslVerification) {
            // 禁用 SSL 验证
            try {
                if (insecureSSLContext == null) {
                    synchronized (HttpUtil.class) {
                        if (insecureSSLContext == null) {
                            insecureSSLContext = SSLContext.getInstance("TLS");
                            insecureSSLContext.init(null, INSECURE_TRUST_MANAGERS, new SecureRandom());
                        }
                    }
                }
                SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(insecureSSLContext)
                        .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .build();

                client = HttpClients.custom()
                        .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                                .setSSLSocketFactory(sslSocketFactory)
                                .build())
                        .build();
            } catch (Exception e) {
                throw new RuntimeException("创建不安全的 HttpClient 失败", e);
            }
        } else {
            // 使用默认配置(启用 SSL 验证)
            client = HttpClients.createDefault();
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            Optional.ofNullable(headers)
                    .filter(h -> !h.isEmpty())
                    .ifPresent(h -> h.forEach(httpPost::setHeader));
            httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON,
                    StandardCharsets.UTF_8.name(), false));
            return client.execute(httpPost, classicHttpResponse -> {
                int statusCode = classicHttpResponse.getCode();
                HttpEntity entity = classicHttpResponse.getEntity();
                String responseBody = EntityUtils.toString(entity);
                if (statusCode != 200) {
                    throw new IOException("HTTP " + statusCode + ": " + classicHttpResponse.getReasonPhrase() + ", Response: " + responseBody);
                }
                return responseBody;
            });
        } finally {
            try {
                if (Objects.nonNull(client)) {
                    client.close();
                }
            } catch (Exception ignored) {
            }
        }
    }
}
