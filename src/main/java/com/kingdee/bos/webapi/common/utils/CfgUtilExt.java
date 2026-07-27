package com.kingdee.bos.webapi.common.utils;

import com.kingdee.bos.webapi.entity.AppCfg;
import com.kingdee.bos.webapi.sdk.WebApiClient;
import com.kingdee.bos.webapi.utils.CfgUtil;
import com.kingdee.bos.webapi.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;


/**
 * 金蝶云星空 WebAPI SDK 默认配置的内存初始化扩展。
 * <p>
 * SDK 获取代理配置时，{@link HttpUtils#getProxy()} 会通过
 * {@link CfgUtil#getAppDefaultCfg()} 读取全局默认配置；
 * {@link WebApiClient} 在认证信息不完整时也会读取该配置。
 * 当 SDK 内部尚未初始化默认配置时，{@code CfgUtil} 会尝试从
 * {@code kdwebapi.properties} 加载配置。
 * </p>
 * <p>
 * 本扩展用于根据应用配置构建 {@link AppCfg}，并将其设置为
 * {@code CfgUtil} 的全局默认配置，使 SDK 复用内存中的配置，
 * 避免再次读取 {@code kdwebapi.properties}。
 * </p>
 *
 * @author xueyu
 * @see HttpUtils#getProxy()
 * @see CfgUtil#getAppDefaultCfg()
 * @see WebApiClient
 */
public class CfgUtilExt {

    /**
     * 将指定配置设置为金蝶 WebAPI SDK 的全局默认配置。
     * <p>
     * 该方法通过反射写入 {@link CfgUtil} 内部的静态 {@code instance} 字段。
     * 设置后，{@link CfgUtil#getAppDefaultCfg()} 将优先返回该实例，
     * 不再尝试加载 {@code kdwebapi.properties}。
     * </p>
     * <p>
     * 此操作会影响当前 JVM 内所有使用 {@code CfgUtil} 默认配置的 SDK 调用，
     * 且依赖 SDK 8.2.0 的内部字段结构；升级金蝶 SDK 时需重新确认兼容性。
     * </p>
     *
     * @param appCfg 要设置的全局默认配置
     * @throws RuntimeException 无法找到或写入 SDK 内部 {@code instance} 字段时抛出
     * @see CfgUtil#getAppDefaultCfg()
     */
    public static void setAppCfgToCfgUtil(AppCfg appCfg) {
        try {
            Field field = CfgUtil.class.getDeclaredField("instance");
            //将字段的访问权限设为true
            field.setAccessible(true);
            field.set(null, appCfg);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用提供的配置参数构建并返回一个 {@link AppCfg} 实例。
     *
     * @param serverUrl      要设置在 {@link AppCfg} 实例中的服务器 URL。
     * @param acctId         要设置在 {@link AppCfg} 实例中的账套 ID。
     * @param userName       要设置在 {@link AppCfg} 实例中的用户名。
     * @param appId          要设置在 {@link AppCfg} 实例中的应用程序 ID。
     * @param appSec         要设置在 {@link AppCfg} 实例中的应用程序密钥。
     * @param lcId           要设置在 {@link AppCfg} 实例中的多语言代码。
     * @param orgNum         要设置在 {@link AppCfg} 实例中的组织编号。
     * @param connectTimeout 要设置在 {@link AppCfg} 实例中的连接超时时间（秒）。
     * @param requestTimeout 要设置在 {@link AppCfg} 实例中的请求超时时间（秒）。
     * @param stockTimeout   要设置在 {@link AppCfg} 实例中的库存超时时间（秒）。
     * @param proxy          要设置在 {@link AppCfg} 实例中的代理信息。
     * @return 使用提供参数初始化的 {@link AppCfg} 实例。
     * @deprecated 请改用 {@link #builder()}
     */
    @Deprecated
    public static AppCfg buildAppCfg(String serverUrl, String acctId, String userName,
                                     String appId, String appSec, int lcId,
                                     String orgNum, int connectTimeout, int requestTimeout,
                                     int stockTimeout, String proxy) {
        return builder()
                .serverUrl(serverUrl)
                .acctId(acctId)
                .userName(userName)
                .appId(appId)
                .appSecret(appSec)
                .lcId(lcId)
                .orgNum(orgNum)
                .connectTimeout(connectTimeout)
                .requestTimeout(requestTimeout)
                .stockTimeout(stockTimeout)
                .proxy(proxy)
                .build();
    }

    /**
     * 创建并返回一个新的 {@link AppCfgBuilder} 实例。
     * 该构建器用于通过链式调用配置各项参数，最终构建一个 {@link AppCfg} 配置对象。
     *
     * @return 一个新的 {@link AppCfgBuilder} 构建器实例
     */
    public static AppCfgBuilder builder() {
        return new AppCfgBuilder();
    }

    /**
     * 用于构建 {@link AppCfg} 配置对象的构建器类。
     * 提供链式方法设置各项配置参数，并通过 {@link #build()} 方法生成最终的配置实例。
     * 支持设置服务器地址、账套信息、用户凭证、应用标识、超时设置及代理配置等。
     * 此构建器遵循不可变对象构建模式，确保配置数据在构建完成后不被修改。
     */
    public static class AppCfgBuilder {
        /**
         * 服务器的基础URL地址。
         * 用于配置应用程序连接的目标服务器，通常包含协议、主机名和端口号。
         * 该地址是构建完整API请求的基础，所有后续的网络调用都将基于此URL进行。
         * 必须确保提供的URL格式正确且服务器可达，否则可能导致连接失败。
         * 此值在构建应用程序配置时设置，且不应为空。
         */
        private String serverUrl;
        /**
         * 账户标识符，用于唯一标识一个账户。
         * 该字段通常与系统内的账户管理功能关联，确保操作的准确性和安全性。
         * 必须为非空字符串，且应符合系统定义的账户ID格式规范。
         */
        private String acctId;
        /**
         * 用户名，用于标识当前配置所关联的用户身份。
         * 该字段通常用于认证或个性化服务场景，确保操作与特定用户绑定。
         * 在构建应用配置时，此值将被传递至最终的 {@link AppCfg} 对象中。
         */
        private String userName;
        /**
         * 应用程序的唯一标识符。
         * 用于在系统中识别和区分不同的应用程序实例。
         * 此标识符通常由系统分配或预先定义，并在应用程序的整个生命周期中保持不变。
         * 在配置构建过程中，此字段用于设置应用程序的身份验证和授权信息。
         * 确保提供的标识符与系统中注册的应用程序匹配，以保证后续操作的正常进行。
         */
        private String appId;
        /**
         * 应用程序密钥，用于身份验证和加密通信。
         * 此密钥应妥善保管，避免泄露，以确保系统安全。
         */
        private String appSecret;
        /**
         * 逻辑通道标识符。
         * 用于在系统中唯一标识一个逻辑通道，通常与网络通信或资源分配相关。
         * 该标识符为整型数值，应在有效范围内设置以确保系统正常运行。
         */
        private int lcId;
        /**
         * 组织编号，用于标识特定的组织或机构。
         * 该字段在配置应用程序时作为组织标识符使用，通常与账户、应用等信息关联。
         * 组织编号应为字符串格式，确保在系统内唯一标识目标组织。
         */
        private String orgNum;
        /**
         * 连接超时时间，单位为毫秒。
         * 该值用于设置建立网络连接时的最长等待时间。
         * 若在指定时间内未能成功建立连接，将抛出连接超时异常。
         * 值为0表示无限等待，通常不建议在生产环境中使用。
         * 合理设置此参数有助于避免因网络延迟或服务不可用导致的长时间阻塞。
         */
        private int connectTimeout;
        /**
         * 请求超时时间，单位为毫秒。
         * 该值定义了从建立连接到服务器返回完整响应所允许的最大时间。
         * 若在此时间内未收到完整响应，则请求将被中断。
         * 设置为0表示无限等待。
         */
        private int requestTimeout;
        /**
         * 库存相关操作的超时时间，单位为毫秒。
         * 该参数用于设置与库存服务交互时的最大等待时间，超过此时间未响应将抛出超时异常。
         * 合理设置此值可以避免因网络延迟或服务端处理缓慢导致的线程长时间阻塞。
         * 默认值或具体取值范围需参考实现类的初始化逻辑或配置说明。
         */
        private int stockTimeout;
        /**
         * 代理服务器地址。
         * 用于配置网络请求时使用的HTTP代理，格式通常为"主机名:端口"。
         * 若为空则不使用代理。
         */
        private String proxy;

        public AppCfgBuilder serverUrl(String serverUrl) {
            this.serverUrl = serverUrl;
            return this;
        }

        public AppCfgBuilder acctId(String acctId) {
            this.acctId = acctId;
            return this;
        }

        public AppCfgBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public AppCfgBuilder appId(String appId) {
            this.appId = appId;
            return this;
        }

        public AppCfgBuilder appSecret(String appSecret) {
            this.appSecret = appSecret;
            return this;
        }

        public AppCfgBuilder lcId(int lcId) {
            this.lcId = lcId;
            return this;
        }

        public AppCfgBuilder orgNum(String orgNum) {
            this.orgNum = orgNum;
            return this;
        }

        public AppCfgBuilder connectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public AppCfgBuilder requestTimeout(int requestTimeout) {
            this.requestTimeout = requestTimeout;
            return this;
        }

        public AppCfgBuilder stockTimeout(int stockTimeout) {
            this.stockTimeout = stockTimeout;
            return this;
        }

        public AppCfgBuilder proxy(String proxy) {
            this.proxy = proxy;
            return this;
        }

        /**
         * 根据当前构建器实例中已设置的配置参数，创建并返回一个{@link AppCfg}对象。
         * 该方法会将构建器中所有已配置的属性值（如服务器地址、账户信息、超时设置等）复制到新创建的{@link AppCfg}实例中。
         * 如果代理地址不为空，也会将其设置到配置对象中。
         *
         * @return 一个包含当前所有配置的{@link AppCfg}实例
         */
        public AppCfg build() {
            AppCfg appCfg = new AppCfg();
            appCfg.setServerUrl(this.serverUrl);
            appCfg.setdCID(this.acctId);
            appCfg.setUserName(this.userName);
            appCfg.setAppId(this.appId);
            appCfg.setAppSecret(this.appSecret);
            appCfg.setlCID(this.lcId);
            appCfg.setOrgNum(this.orgNum);
            appCfg.setConnectTimeout(this.connectTimeout);
            appCfg.setRequestTimeout(this.requestTimeout);
            appCfg.setStockTimeout(this.stockTimeout);
            if (StringUtils.isNotBlank(this.proxy)) {
                appCfg.setProxy(this.proxy);
            }
            return appCfg;
        }
    }


}
