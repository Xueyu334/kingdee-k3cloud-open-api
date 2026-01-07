package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 登录结果上下文信息
 */
@Getter
@Setter
@ToString
public class LoginResultContext implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端类型
     */
    @JsonProperty("ClientType")
    private Integer clientType;

    /**
     * 数据中心编号
     */
    @JsonProperty("DataCenterNumber")
    private String dataCenterNumber;

    /**
     * 数据库类型
     */
    @JsonProperty("DatabaseType")
    private Integer databaseType;

    /**
     * 全局会话ID
     */
    @JsonProperty("Gsid")
    private String gsid;

    /**
     * 数据库ID
     */
    @JsonProperty("DBid")
    private String dbid;

    /**
     * 用户时区信息
     */
    @JsonProperty("UTimeZone")
    private TimeZoneInfo uTimeZone;

    /**
     * 日志区域设置
     */
    @JsonProperty("LogLocale")
    private String logLocale;

    /**
     * 使用的语言信息列表
     */
    @JsonProperty("UseLanguages")
    private List<UseLanguageInfo> useLanguages;

    /**
     * 用户区域设置
     */
    @JsonProperty("UserLocale")
    private String userLocale;

    /**
     * 会话ID
     */
    @JsonProperty("SessionId")
    private String sessionId;

    /**
     * 显示版本
     */
    @JsonProperty("DisplayVersion")
    private String displayVersion;

    /**
     * 当前组织信息
     */
    @JsonProperty("CurrentOrganizationInfo")
    private CurrentOrganizationInfo currentOrganizationInfo;

    /**
     * 是否自动翻译CH_ZH
     */
    @JsonProperty("IsCH_ZH_AutoTrans")
    private Boolean isCH_ZH_AutoTrans;

    /**
     * 用户令牌
     */
    @JsonProperty("UserToken")
    private String userToken;

    /**
     * 用户名
     */
    @JsonProperty("UserName")
    private String userName;

    /**
     * GDCID
     */
    @JsonProperty("GDCID")
    private String gdcid;

    /**
     * 上下文结果类型
     */
    @JsonProperty("ContextResultType")
    private Integer contextResultType;

    /**
     * 是否部署为公共云
     */
    @JsonProperty("IsDeployAsPublicCloud")
    private Boolean isDeployAsPublicCloud;

    /**
     * 产品版本
     */
    @JsonProperty("ProductEdition")
    private Integer productEdition;

    /**
     * 数据中心名称
     */
    @JsonProperty("DataCenterName")
    private String dataCenterName;

    /**
     * 事务级别
     */
    @JsonProperty("TRLevel")
    private Integer trLevel;

    /**
     * 租户ID
     */
    @JsonProperty("TenantId")
    private String tenantId;

    /**
     * 自定义名称
     */
    @JsonProperty("CustomName")
    private String customName;

    /**
     * 用户ID
     */
    @JsonProperty("UserId")
    private Long userId;

    /**
     * 微博认证信息
     */
    @JsonProperty("WeiboAuthInfo")
    private WeiboAuthInfo weiboAuthInfo;

    /**
     * 服务器时区信息
     */
    @JsonProperty("STimeZone")
    private TimeZoneInfo sTimeZone;

    /**
     * 时区信息
     */
    @Getter
    @Setter
    @ToString
    public static class TimeZoneInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 偏移量（Ticks）
         */
        @JsonProperty("OffsetTicks")
        private Long offsetTicks;

        /**
         * 标准名称
         */
        @JsonProperty("StandardName")
        private String standardName;

        /**
         * ID
         */
        @JsonProperty("Id")
        private Integer id;

        /**
         * 编号
         */
        @JsonProperty("Number")
        private String number;

        /**
         * 是否可用
         */
        @JsonProperty("CanBeUsed")
        private Boolean canBeUsed;
    }

    /**
     * 使用语言信息
     */
    @Getter
    @Setter
    @ToString
    public static class UseLanguageInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 区域ID
         */
        @JsonProperty("LocaleId")
        private Integer localeId;

        /**
         * 区域名称
         */
        @JsonProperty("LocaleName")
        private String localeName;

        /**
         * 别名
         */
        @JsonProperty("Alias")
        private String alias;

        /**
         * 许可证类型
         */
        @JsonProperty("LicenseType")
        private Integer licenseType;
    }

    /**
     * 当前组织信息
     */
    @Getter
    @Setter
    @ToString
    public static class CurrentOrganizationInfo implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 组织ID
         */
        @JsonProperty("ID")
        private Integer id;

        /**
         * 账套组织类型
         */
        @JsonProperty("AcctOrgType")
        private String acctOrgType;

        /**
         * 名称
         */
        @JsonProperty("Name")
        private String name;

        /**
         * 功能ID列表
         */
        @JsonProperty("FunctionIds")
        private List<Integer> functionIds;
    }
}
