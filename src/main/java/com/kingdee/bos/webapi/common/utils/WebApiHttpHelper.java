package com.kingdee.bos.webapi.common.utils;

import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.convert.ConvertApiResponse;
import com.kingdee.bos.webapi.common.convert.fastjson.FastJsonConvertApiResponse;
import com.kingdee.bos.webapi.common.exception.WebApiInvokeException;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.domain.dto.request.save.SaveRequest;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.LoginResult;
import com.kingdee.bos.webapi.domain.dto.response.result.SaveResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * WebApiHttpHelper 是一个用于处理金蝶 K3Cloud Web API 的辅助类。
 * 该类通过封装 WebApiProperties 和 ConvertApiResponse，提供对 Web API 请求的配置和响应解析支持。
 * <p>
 * WebApiProperties 包含了与 Web API 交互所需的配置信息，例如服务地址、账套ID、用户凭据等。
 * ConvertApiResponse 提供了将 API 响应字符串解析为特定业务对象的功能，支持多种业务场景的响应解析。
 * <p>
 * 该类采用私有构造函数设计，确保实例化时必须提供必要的依赖项。
 * 通过这种方式，保证了类的使用安全性和依赖注入的灵活性。
 *
 * @author xueyu
 */
@Slf4j
public class WebApiHttpHelper implements AutoCloseable {

    /**
     * Kingdee K3Cloud Web API 的配置属性。
     */
    private final WebApiProperties webApiProperties;
    /**
     * API 响应的转换器。
     */
    private final ConvertApiResponse convertApiResponse;
    /**
     * 用于存储当前登录结果，包括会话ID等信息。
     * 使用 volatile 关键字确保多线程环境下对 loginResult 的可见性。
     */
    private volatile LoginResult loginResult;
    /**
     * 持久化的 CloseableHttpClient 实例，用于发送 HTTP 请求并自动管理会话。
     */
    private CloseableHttpClient httpClient;

    /**
     * 私有构造函数，用于创建 WebApiHttpHelper 的实例。
     * 通过该构造函数，将 WebApiProperties 和 ConvertApiResponse 注入到当前类中，
     * 确保类的依赖项在实例化时被正确初始化。
     *
     * @param webApiProperties   包含与金蝶 K3Cloud Web API 交互所需的配置信息，例如服务地址、账套ID、用户凭据等。
     *                           该参数不能为空，且其内容通常通过配置文件或外部化方式进行管理。
     * @param convertApiResponse 用于解析 API 响应字符串的转换器实例。
     *                           该参数提供了将 API 响应解析为特定业务对象的能力，支持多种业务场景的响应处理。
     */
    private WebApiHttpHelper(WebApiProperties webApiProperties, ConvertApiResponse convertApiResponse) {
        this.webApiProperties = webApiProperties;
        this.convertApiResponse = convertApiResponse;
        initHttpClient();
    }

    /**
     * 创建一个 WebApiHttpHelper 实例，用于处理金蝶 K3Cloud Web API 的请求和响应。
     * 该方法通过指定的 WebApiProperties 配置信息进行初始化，并使用默认的 FastJsonConvertApiResponse 作为响应解析器。
     * 确保在与 Web API 交互时具备必要的配置支持和响应解析能力。
     *
     * @param webApiProperties 包含与金蝶 K3Cloud Web API 交互所需的配置信息，例如服务地址、账套ID、用户凭据等。
     *                         该参数不能为空，且其内容通常通过配置文件或外部化方式进行管理。
     * @return 返回一个初始化完成的 WebApiHttpHelper 实例，用于处理 Web API 请求和响应。
     */
    public static WebApiHttpHelper of(final WebApiProperties webApiProperties) {
        return of(webApiProperties, new FastJsonConvertApiResponse());
    }

    /**
     * 创建一个 WebApiHttpHelper 实例，用于处理金蝶 K3Cloud Web API 的请求和响应。
     * 该方法通过指定的 WebApiProperties 和 ConvertApiResponse 实例进行初始化，
     * 确保在与 Web API 交互时能够正确配置和解析响应消息。
     *
     * @param webApiProperties   包含与金蝶 K3Cloud Web API 交互所需的配置信息，例如服务地址、账套ID、用户凭据等。
     *                           该参数不能为空，且其内容通常通过配置文件或外部化方式进行管理。
     * @param convertApiResponse 用于解析 API 响应字符串的转换器实例。
     *                           该参数提供了将 API 响应解析为特定业务对象的能力，支持多种业务场景的响应处理。
     *                           该参数不能为空。
     * @return 返回一个 WebApiHttpHelper 实例，用于处理金蝶 K3Cloud Web API 的请求和响应。
     */
    private static WebApiHttpHelper of(WebApiProperties webApiProperties, ConvertApiResponse convertApiResponse) {
        Assert.notNull(webApiProperties, () -> new NullPointerException("云星空WebApi客户端不能为空!"));
        Assert.notNull(convertApiResponse, () -> new NullPointerException("响应消息转换插件不能为空!"));
        return new WebApiHttpHelper(webApiProperties, convertApiResponse);
    }

    /**
     * 初始化 CloseableHttpClient 实例。
     * 配置了一个请求拦截器 {@code HttpRequestInterceptor}，它会在每个请求发出前，
     * 检查当前是否已登录并获取到有效的 kdservice-sessionid，然后将其添加到请求头中。
     */
    private void initHttpClient() {
        // 配置连接层参数（TCP 连接建立超时）
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(Timeout.ofSeconds(webApiProperties.getConnectTimeout()))
                .build();
        // 配置请求层参数（获取连接、等待响应超时）
        RequestConfig requestConfig = RequestConfig.custom()
                // 从连接池获取连接的等待时间
                .setConnectionRequestTimeout(Timeout.ofSeconds(webApiProperties.getRequestTimeout()))
                // 等待服务端响应的超时时间
                .setResponseTimeout(Timeout.ofSeconds(webApiProperties.getStockTimeout()))
                .build();
        // 构建连接管理器，并应用连接配置
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                // 设置默认连接配置（包含 connectTimeout）
                .setDefaultConnectionConfig(connectionConfig)
                .build();
        // 构建 HttpClient
        this.httpClient = HttpClients.custom()
                // 设置连接管理器
                .setConnectionManager(connectionManager)
                // 设置默认请求配置
                .setDefaultRequestConfig(requestConfig)
                // 添加请求拦截器，在每次请求前注入 SessionId
                .addRequestInterceptorLast((request, entity, context) -> {
                    if (loginResult != null && loginResult.isLoginSuccess() && loginResult.getKdsvcSessionId() != null) {
                        request.setHeader("kdservice-sessionid", loginResult.getKdsvcSessionId());
                    }
                }).build();
    }

    /**
     * 获取金蝶 K3Cloud Web API 服务接口的完整 URL。
     * 该方法会根据配置的服务器地址和提供的服务名称构建完整的请求 URL。
     *
     * @param serviceName API 服务名称，例如 "Kingdee.BOS.WebApi.ServicesStub.AuthService.LoginBySign"
     * @return 完整的 API 服务 URL。
     */
    private String getServiceUrl(String serviceName) {
        String url = this.webApiProperties.getServerUrl();
        if (url == null) {
            url = "";
        }
        if (!url.endsWith("/")) {
            url += "/";
        }
        return url + serviceName + ".common.kdsvc";
    }

    /**
     * 构建 LoginBySign 方法所需的参数数组。
     * 该方法根据 WebApiProperties 中配置的账套ID、用户名、应用ID、应用秘钥和语言ID，
     * 并生成时间戳和签名，最终返回一个包含这些信息的 Object 数组。
     *
     * @return 包含 LoginBySign 所需参数的 Object 数组。
     */
    private Object[] buildLoginBySignParams() {
        String acctId = webApiProperties.getAcctId();
        String userName = webApiProperties.getUserName();
        String appId = webApiProperties.getAppId();
        String appSec = webApiProperties.getAppSec();
        int lcId = webApiProperties.getLcId();
        // 生成时间戳（秒）
        long timestamp = System.currentTimeMillis() / 1000;
        // 生成签名
        String sign = generateSign(acctId, userName, appId, appSec, timestamp);
        // 参数依次为账套ID、用户名、应用ID、时间戳、签名信息、语言ID
        return new Object[]{acctId, userName, appId, timestamp, sign, lcId};
    }

    /**
     * 生成 SHA-256 签名字符串。
     * 该方法将账套ID、用户名、应用ID、应用秘钥和时间戳进行排序，
     * 然后使用 SHA-256 算法进行加密，最终返回十六进制表示的签名字符串。
     *
     * @param acctId    账套ID。
     * @param userName  用户名。
     * @param appId     应用ID。
     * @param appSecret 应用秘钥。
     * @param timestamp 时间戳（秒）。
     * @return 生成的 SHA-256 签名字符串。
     * @throws WebApiInvokeException 如果生成签名失败（例如，不支持 SHA-256 算法）。
     */
    private String generateSign(String acctId, String userName, String appId, String appSecret, long timestamp) {
        try {
            // 将参数放入数组并排序
            String[] arr = new String[]{acctId, userName, appId, appSecret, String.valueOf(timestamp)};
            Arrays.sort(arr);
            // SHA-256 加密
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            for (String str : arr) {
                sha256.update(str.getBytes(StandardCharsets.UTF_8));
            }
            byte[] hashBytes = sha256.digest();
            // 转换为十六进制字符串
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hashString.append('0');
                }
                hashString.append(hex);
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new WebApiInvokeException("生成签名失败", e);
        }
    }

    /**
     * 执行金蝶 K3Cloud Web API 的 LoginBySign 登录操作。
     * 该方法构建登录请求，发送到 API 服务器，并解析登录响应。
     * 在登录成功后，会更新 {@code loginResult} 成员变量，以便后续请求使用会话ID。
     * 如果登录失败或发生 IO 异常，{@code loginResult} 将被置为 null。
     *
     * @return 包含登录结果的 {@code LoginResult} 对象。
     * @throws WebApiInvokeException 如果 LoginBySign 调用失败。
     */
    public LoginResult loginBySign() {
        String url = this.getServiceUrl(ApiConsts.LOGIN_BY_SIGN);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("parameters", this.buildLoginBySignParams());
        String jsonBody = JSON.toJSONString(bodyMap);

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(jsonBody,
                    ContentType.APPLICATION_JSON,
                    StandardCharsets.UTF_8.name(), false));

            String response = this.httpClient.execute(httpPost, classicHttpResponse -> {
                HttpEntity entity = classicHttpResponse.getEntity();
                // 尝试获取并打印Set-Cookie头部信息
                Header[] cookies = classicHttpResponse.getHeaders("Set-Cookie");
                if (cookies != null) {
                    for (Header cookie : cookies) {
                        if (log.isDebugEnabled()) {
                            log.debug("登录响应 Set-Cookie: {}", cookie.getValue());
                        }
                    }
                }
                return EntityUtils.toString(entity);
            });
            if (log.isDebugEnabled()) {
                log.debug("登录响应: {}", response);
            }
            LoginResult result = convertApiResponse.parseLoginResponse(response);
            // 更新登录结果
            this.loginResult = result;
            return result;
        } catch (IOException e) {
            this.loginResult = null; // 在登录失败时将 loginResult 置为 null
            throw new WebApiInvokeException("LoginBySign failed", e);
        }
    }

    /**
     * 确保当前会话已登录且有效。
     * 如果未登录或会话已失效，则尝试重新登录。
     * 该方法是同步的，以防止多线程环境下重复登录。
     * 在重新登录前，会清除任何旧的、可能已失效的会话信息。
     *
     * @throws WebApiInvokeException 如果重新登录失败。
     */
    private synchronized void ensureLogin() {
        if (this.loginResult == null || !this.loginResult.isLoginSuccess()) {
            if (log.isDebugEnabled()) {
                log.debug("当前未登录或登录已失效，尝试重新登录...");
            }
            this.loginResult = null; // 确保在重新登录前清除任何旧的、可能已失效的会话信息。
            LoginResult reloginResult = loginBySign();
            if (!reloginResult.isLoginSuccess()) {
                throw new WebApiInvokeException("重新登录失败!");
            }
            if (log.isDebugEnabled()) {
                log.debug("重新登录成功，SessionId: {}", reloginResult.getKdsvcSessionId());
            }
        }
    }

    /**
     * 执行金蝶 K3Cloud Web API 的通用操作。
     * 该方法会先确保登录状态有效，然后构建并发送 API 请求，并返回原始的 API 响应字符串。
     * kdservice-sessionid 会通过配置的请求拦截器自动添加。
     *
     * @param serviceName API 服务名称，例如 "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save"
     * @param parameters  API 请求参数，通常是一个 Object 数组，会被序列化为 JSON。
     * @return 原始的 API 响应字符串。
     * @throws WebApiInvokeException 如果调用 K3Cloud Web API 出现异常。
     */
    public String execute(String serviceName, Object[] parameters) {
        // 确保登录状态
        ensureLogin();
        String url = this.getServiceUrl(serviceName);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("parameters", parameters);
        String jsonBody = JSON.toJSONString(bodyMap);
        if (log.isDebugEnabled()) {
            log.debug("Request URL: {}", url);
            log.debug("Request Body: {}", jsonBody);
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            // kdservice-sessionid 现在由拦截器自动添加
            httpPost.setEntity(new StringEntity(jsonBody,
                    ContentType.APPLICATION_JSON,
                    StandardCharsets.UTF_8.name(), false));
            return this.httpClient.execute(httpPost, classicHttpResponse -> {
                HttpEntity entity = classicHttpResponse.getEntity();
                String responseString = EntityUtils.toString(entity);
                if (log.isDebugEnabled()) {
                    log.debug("Response: {}", responseString);
                }
                return responseString;
            });
        } catch (IOException e) {
            log.error("调用K3Cloud Web API [{}] 出现异常!", serviceName, e);
            throw new WebApiInvokeException(String.format("调用K3Cloud Web API [%s] 出现异常!", serviceName), e);
        }
    }

    /**
     * 关闭内部的 CloseableHttpClient 实例。
     * 在 WebApiHttpHelper 实例不再使用时，应调用此方法释放资源。
     * 实现 {@code AutoCloseable} 接口，允许在 try-with-resources 语句中使用。
     *
     * @throws IOException 如果关闭 HttpClient 过程中发生 IO 异常。
     */
    @Override
    public void close() throws IOException {
        if (httpClient != null) {
            httpClient.close();
        }
    }

    /**
     * 执行金蝶 K3Cloud Web API 的保存操作。
     * 该方法封装了通用的 API 执行逻辑，专门用于处理保存操作。
     *
     * @param formId 表单标识，例如 "BD_Material"。
     * @param data   要保存的数据对象，将被序列化为 JSON 字符串。
     * @return 包含保存结果的 {@code WebApiResp<SaveResult>} 对象。
     */
    public WebApiResp<SaveResult> save(String formId, SaveRequest data) {
        Object[] parameters = new Object[]{formId, JSON.toJSONString(data)};
        String response = execute(ApiConsts.SAVE, parameters);
        return convertApiResponse.parseSaveWebApiResponse(response);
    }

    /**
     * 执行金蝶云星空单据查询（ExecuteBillQuery）
     *
     * @param data 查询 JSON 字符串
     * @return 查询结果二维列表
     */
    public List<List<Object>> executeBillQuery(String data) {
        // 参数校验
        if (data == null || data.isBlank()) {
            throw new IllegalArgumentException("executeBillQuery 参数 data 不能为空");
        }
        // 构造 API 参数
        Object[] parameters = new Object[]{data};
        // 调用接口
        String response = execute(ApiConsts.EXECUTE_BILL_QUERY, parameters);
        // 返回值校验
        if (response == null || response.isBlank()) {
            return Collections.emptyList();
        }
        try {
            // 解析为二维数组 List<List<Object>>
            return convertApiResponse.parseListListObjectApiResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("解析 ExecuteBillQuery 返回结果失败，原始响应：" + response, e);
        }
    }

    /**
     * 金蝶K3Cloud Web API 常量。
     */
    public static final class ApiConsts {
        /**
         * 登录服务接口名称
         */
        public static final String LOGIN_BY_SIGN = "Kingdee.BOS.WebApi.ServicesStub.AuthService.LoginBySign";
        /**
         * 保存服务接口名称
         */
        public static final String SAVE = "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save";
        /**
         * 单据查询服务接口名称
         */
        public static final String EXECUTE_BILL_QUERY = "Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery";

        private ApiConsts() {
            // 私有构造函数，防止实例化
        }
    }


}
