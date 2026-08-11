

# 金蝶云星空 WebAPI 开放平台 Java SDK

## 📖 项目简介

本项目是对金蝶云星空（Kingdee K3 Cloud）WebAPI 接口的高层封装，旨在为 Java 开发者提供更简洁、更易用的 API 调用方式。通过本 SDK，开发者可以快速集成金蝶云星空的各种业务能力，无需关注底层 HTTP 通信细节。

## ✨ 核心特性

| 特性 | 说明 |
|------|------|
| **接口封装** | 完整封装保存、查询、提交、审核、作废等核心业务接口 |
| **异常处理** | 统一的异常处理机制，清晰的错误信息反馈 |
| **多 JSON 转换器** | 内置 FastJSON2、Gson 和 Jackson 响应转换器，Jackson 支持传入自定义 `ObjectMapper` |
| **日志记录** | 自动记录请求响应日志，便于问题排查 |
| **多环境支持** | 支持开发、测试、生产等多环境配置切换 |
| **高性能缓存** | 集成 Caffeine 缓存框架，提升访问效率 |
| **工具类库** | 提供丰富的工具类，简化开发工作 |

## 🛠 技术栈

- **核心框架**: Spring Boot 3.5.16
- **JDK 版本**: 17
- **构建工具**: Maven 3.6+
- **HTTP 客户端**: Apache HttpClient 5.3.1
- **JSON 处理**: FastJson2 2.0.52、Gson 2.8.9、Jackson（由 Spring Boot BOM 管理版本）
- **工具库**: Apache Commons Lang 3.20.0、Guava 33.2.1-jre
- **缓存**: Caffeine 3.1.8
- **集合框架**: Eclipse Collections 12.0.0.M3

## 📦 项目结构

```
kingdee-k3cloud-open-api
├── src/main/java/com/kingdee/bos/webapi/    # 金蝶 WebAPI 封装模块
│   ├── common/                               # 公共组件
│   │   ├── convert/                          # 响应转换器（FastJSON2、Gson、Jackson）
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
    <groupId>com.rain</groupId>
    <artifactId>kingdee-k3cloud-open-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

### 构建产物

在仓库根目录执行：

```bash
mvn clean package -P local -DskipTests
```

构建会生成可执行 JAR 及对应的 `-sources.jar` 源码包；可将 `local` 替换为 `dev`、`test` 或 `prod` profile。项目部分测试依赖真实金蝶服务和本地凭据，默认构建示例使用 `-DskipTests`。

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
      print-execute-url: true                          # 是否打印执行 URL
```

`WebApiConfig` 会显式创建并绑定 `WebApiProperties` Bean；使用上述前缀配置即可，无需在业务代码中额外注册属性类。

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

### JSON 响应转换

`WebApiHelper` 默认使用 FastJSON2 转换响应。若需要使用 Jackson 的模块、日期格式或未知字段策略，可在创建 `WebApiHelper` 时传入自定义转换器：

```java
ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
WebApiHelper webApiHelper = WebApiHelper.of(
        k3CloudApi,
        new JacksonConvertApiResponse(objectMapper)
);
```

`Model` 同时声明了 FastJSON2 与 Jackson 的字段顺序注解；自定义模型字段仍应使用与金蝶接口一致的 JSON 属性名。

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
| `connect-timeout` | 120秒 | 连接超时时间 |
| `request-timeout` | 120秒 | 请求超时时间 |
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

项目测试用例位于 `src/test/java/` 目录：

- `JacksonConvertApiResponseTest`：离线验证 Jackson 对泛型响应、二维列表、`ViewResult` 及自定义 `ObjectMapper` 的转换。

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

### Eclipse Collections 测试

测试用例位置：`src/test/java/com/rain/eclipse/coll/EclipseCollectionsTest.java`

该用例覆盖 Eclipse Collections 的常用集合操作，包括 List 的筛选与转换、Set 的并交差、Map 的缺省插入与过滤、Bag 的计数统计，以及 Multimap 的多值读写。

示例代码：

```java
// List: filter/select 常用操作
MutableList<String> list = Lists.mutable.of("Apple", "Banana", "Orange");
MutableList<String> filtered = list.select(fruit -> fruit.length() > 5);

// Set: union/intersect/difference 操作
MutableSet<Integer> left = Sets.mutable.of(1, 2, 3, 3);
MutableSet<Integer> right = Sets.mutable.of(3, 4);
left.union(right);
left.intersect(right);
left.difference(right);

// Map: getIfAbsentPut 与 select 过滤
MutableMap<String, Integer> map = Maps.mutable.of("a", 1, "b", 2);
map.getIfAbsentPut("c", () -> 3);
map.select((k, v) -> v >= 2);

// Bag: 计数统计
MutableBag<String> bag = Bags.mutable.of("a", "a", "b");
bag.occurrencesOf("a");

// Multimap: 多值映射的 put/get
MutableListMultimap<String, String> multimap = Multimaps.mutable.list.empty();
multimap.put("k1", "v1");
multimap.get("k1");
```

### RangeMap 测试

测试用例位置：`src/test/java/com/rain/guava/RangeMapTest.java`

该用例基于 Guava 的 `TreeRangeMap`，覆盖范围重叠的覆盖规则、删除范围产生间隙、子范围视图以及相同范围的替换行为。

示例代码：

```java
RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
rangeMap.put(Range.closed(1, 10), "A");
rangeMap.put(Range.closed(5, 15), "B");

rangeMap.remove(Range.closed(3, 7));

RangeMap<Integer, String> view = rangeMap.subRangeMap(Range.closed(5, 15));
view.get(6);
```

### TimeoutLockFreeSpinStackLock 测试

锁实现位置：`src/main/java/com/rain/common/utils/lock/TimeoutLockFreeSpinStackLock.java`

测试用例位置：`src/test/java/com/rain/lock/TimeoutLockFreeSpinStackLockTest.java`

该锁是一个支持超时与中断的无锁自旋栈锁，采用 LIFO 顺序管理等待线程，适用于锁持有时间较短的高并发场景。测试覆盖非持有线程的 `tryLock` 失败、超时获取失败、可中断获取与非法解锁异常。

示例代码：

```java
TimeoutLockFreeSpinStackLock lock = new TimeoutLockFreeSpinStackLock();
lock.lock();
try {
    boolean acquired = lock.tryLock(50, TimeUnit.MILLISECONDS);
    System.out.println(acquired);
} finally {
    lock.unlock();
}
```

### Caffeine 缓存测试

测试用例位置：`src/test/java/com/rain/caffeine/CaffeineTest.java`

```java
@Test
void test() throws InterruptedException {
    Cache<Object, Object> cache = Caffeine.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();
    cache.put("as1", "as");
    Thread.sleep(1000);
    Object as1 = cache.get("as1", k -> null);
    log.info(as1.toString());
}
```

## 📝 更新日志

### 未发布变更

- ✨ 新增 Jackson 响应转换器及可注入的 `ObjectMapper` 配置。
- ♻️ `WebApiProperties` 改为由 `WebApiConfig` 显式绑定，属性对象不再依赖 Spring 注解。
- ♻️ `WebApiInvokeException` 独立继承 `RuntimeException`，保留错误码访问能力。
- ⬆️ Spring Boot 升级至 3.5.16，编译目标统一为 Java 17。
- 📦 Maven `package` 阶段额外生成源码 JAR。

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
