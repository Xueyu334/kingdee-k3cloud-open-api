

# 金蝶云星空 WebAPI 开放平台 Java SDK

## 📖 项目简介

本项目是对金蝶云星空（Kingdee K3 Cloud）WebAPI 接口的高层封装，旨在为 Java 开发者提供更简洁、更易用的 API 调用方式。通过本 SDK，开发者可以快速集成金蝶云星空的各种业务能力，无需关注底层 HTTP 通信细节。

## ✨ 核心特性

| 特性 | 说明 |
|------|------|
| **接口封装** | 完整封装保存、查询、提交、审核、作废等核心业务接口 |
| **异常处理** | 统一的异常处理机制，清晰的错误信息反馈 |
| **日志记录** | 自动记录请求响应日志，便于问题排查 |
| **多环境支持** | 支持开发、测试、生产等多环境配置切换 |
| **高性能缓存** | 集成 Caffeine 缓存框架，提升访问效率 |
| **工具类库** | 提供丰富的工具类，简化开发工作 |

## 🛠 技术栈

- **核心框架**: Spring Boot 3.3.4
- **JDK 版本**: 17
- **构建工具**: Maven 3.6+
- **HTTP 客户端**: Apache HttpClient 5.3.1
- **JSON 处理**: FastJson2 2.0.52, Gson 2.8.9
- **工具库**: Hutool 5.8.29、Guava 33.2.1-jre
- **缓存**: Caffeine 3.1.8
- **集合框架**: Eclipse Collections 12.0.0.M3

## 📦 项目结构

```
kingdee-k3cloud-open-api
├── src/main/java/com/kingdee/bos/webapi/    # 金蝶 WebAPI 封装模块
│   ├── common/                               # 公共组件
│   │   ├── convert/                          # 响应转换器
│   │   ├── exception/                        # 异常定义
│   │   └── utils/                            # 工具类
│   ├── config/                               # 配置类
│   └── domain/                               # 领域对象
│       ├── dto/request/                      # 请求对象
│       ├── dto/response/                     # 响应对象
│       └── bo/                               # 业务对象
├── src/main/java/com/rain/                   # 业务应用模块
│   ├── common/                               # 通用组件
│   │   ├── exception/                        # 业务异常
│   │   └── utils/                            # 工具类
│   ├── config/                               # 配置类
│   ├── domain/                               # 领域对象
│   └── event/                                # 事件监听
└── src/main/resources/                       # 资源配置文件
    ├── application.yml                       # 主配置
    ├── application-dev.yml                   # 开发环境
    ├── application-test.yml                  # 测试环境
    └── application-prod.yml                  # 生产环境
```

## 🚀 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.6 或更高版本

### Maven 依赖

```xml
<dependency>
    <groupId>com.kingdee.bos</groupId>
    <artifactId>kingdee-k3cloud-open-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 配置文件

在 `application.yml` 或对应环境的配置文件中添加以下配置：

```yaml
kingdee:
  k3cloud:
    web-api:
      server-url: http://your-k3cloud-server/k3cloud/  # 金蝶服务器地址
      acct-id: your-acct-id                            # 账套ID
      user-name: your-username                         # 用户名
      app-id: your-app-id                              # 应用ID
      app-sec: your-app-secret                         # 应用密钥
      lc-id: 2052                                      # 语言ID（2052=简体中文）
      org-num: 100                                     # 组织编号
      connect-timeout: 360                             # 连接超时（秒）
      request-timeout: 360                             # 请求超时（秒）
      stock-timeout: 180                               # 套接字超时（秒）
      is-print-execute-url: true                       # 是否打印执行URL
```

### 基础使用

#### 1. 注入依赖

```java
@Autowired
private WebApiHelper webApiHelper;

@Autowired
private WebApiHttpHelper webApiHttpHelper;
```

#### 2. 登录认证

```java
// 使用签名方式登录
LoginResult loginResult = webApiHttpHelper.loginBySign();

if (loginResult != null && loginResult.isLoginSuccess()) {
    System.out.println("登录成功，SessionId: " + loginResult.getKdsvcSessionId());
} else {
    System.out.println("登录失败: " + loginResult.getMessage());
}
```

#### 3. 查询数据

```java
// 视图查询
ViewRequest request = new ViewRequest();
request.setFormId("BD_Material");
request.setFieldKeys("FNumber,FName");

WebApiResp<ViewResult> response = webApiHelper.viewResult(request);

if (response.isSuccessfully()) {
    ViewResult result = response.getResult();
    // 处理查询结果
}
```

#### 4. 保存数据

```java
// 保存单据
SaveRequest request = new SaveRequest();
request.setFormId("BD_Material");
request.setModel(model);

WebApiResp<SaveResult> response = webApiHelper.saveResult(request);

if (response.isSuccessfully()) {
    SaveResult result = response.getResult();
    System.out.println("保存成功，ID: " + result.getId());
}
```

#### 5. 批量操作

```java
// 批量保存
WebApiResp<BatchSaveResult> response = webApiHelper.batchSaveResult(formId, jsonData);

// 执行复杂查询
List<List<Object>> result = webApiHelper.executeBillQuery(jsonData);
```

## 📋 API 清单

### 核心操作接口

| 方法 | 说明 |
|------|------|
| `save/saveResult` | 保存数据 |
| `view/viewResult` | 视图查询 |
| `submit/submitResult` | 提交单据 |
| `audit/auditResult` | 审核单据 |
| `unAudit/unAuditResult` | 反审核 |
| `delete/deleteResult` | 删除数据 |
| `draft/draftResult` | 暂存 |
| `push/pushResult` | 推式生成 |
| `cancel/cancelResult` | 撤销 |
| `billClose/billCloseResult` | 结案 |
| `billUnClose/billUnCloseResult` | 反结案 |
| `cancelAssign/cancelAssignResult` | 取消分配 |
| `batchSave/batchSaveResult` | 批量保存 |
| `executeBillQuery` | 执行单据查询 |
| `execute` | 执行自定义服务 |
| `getReportData` | 获取报表数据 |

### 附件操作接口

| 方法 | 说明 |
|------|------|
| `attachmentUpload/attachmentUploadResult` | 上传附件 |
| `attachmentDownLoad/attachmentDownLoadResult` | 下载附件 |
| `attachmentSplitUpload` | 分片上传 |
| `attachmentSplitDownload` | 分片下载 |

## ⚙️ 配置说明

### 超时配置

| 参数 | 默认值 | 说明 |
|------|--------|------|
| `connect-timeout` | 360秒 | 连接超时时间 |
| `request-timeout` | 360秒 | 请求超时时间 |
| `stock-timeout` | 180秒 | 套接字读取超时时间 |

### 缓存配置

项目默认集成 Caffeine 缓存，可通过配置文件进行调整：

```yaml
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=600s
```

## 🧪 测试示例

项目提供了完整的测试用例，位于 `src/test/java/com/rain/` 目录：

```java
@SpringBootTest
public class K3CloudHttpTest {
    
    @Autowired
    private WebApiProperties webApiProperties;
    
    @Test
    void testLoginBySign() {
        // 测试登录
    }
    
    @Test
    void testSave() {
        // 测试保存操作
    }
    
    @Test
    void testExecuteBillQuery() {
        // 测试复杂查询
    }
}
```

## 📝 更新日志

### v1.0.0
- ✨ 初始版本发布
- ✨ 封装金蝶云星空 WebAPI 基础功能
- ✨ 支持多环境配置
- ✨ 集成常用工具库

## ⚠️ 注意事项

1. 确保金蝶云星空服务器网络可达
2. 请正确配置 `app-id` 和 `app-sec` 凭证信息
3. 根据实际业务需求调整超时时间配置
4. 生产环境建议将日志级别调整为 `info` 或 `warn`
5. 使用前请确保已开通对应的 API 权限

## 📄 许可证

本项目遵循 [LICENSE](LICENSE) 文件中的许可证协议。

## 🤝 贡献指南

如有任何问题或建议，欢迎提交 Issue 或 Pull Request。
