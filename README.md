# kingdee-k3cloud-open-api

## 介绍

封装金蝶云星空的 WebApi 接口，提供更简洁易用的 API 调用方式。

## 技术栈

- **Spring Boot**: 3.3.4
- **JDK**: 17
- **Kingdee WebApi SDK**: 8.0.6
- **构建工具**: Maven

## 主要依赖

| 依赖 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.3.4 | 核心框架 |
| FastJson2 | 2.0.52 | JSON 处理 |
| Hutool | 5.8.29 | Java 工具库 |
| Guava | 33.2.1-jre | Google 工具库 |
| Caffeine | 3.1.8 | 高性能缓存 |
| Eclipse Collections | 12.0.0.M3 | 高性能集合框架 |
| HttpClient5 | 5.3.1 | HTTP 客户端 |

## 功能特性

- ✅ 封装金蝶云星空 WebApi 接口调用
- ✅ 支持统一的异常处理
- ✅ 支持请求响应日志记录
- ✅ 支持多环境配置（dev/test/prod）
- ✅ 集成高性能缓存
- ✅ 提供便捷的工具类

## 项目结构

```
kingdee-k3cloud-open-api
├── src/main/java/com/kingdee/bos/webapi/    # 金蝶 WebApi 封装
│   ├── common/                               # 公共工具类
│   ├── config/                               # 配置类
│   └── domain/                               # 领域对象
├── src/main/java/com/rain/                   # 业务代码
│   ├── common/                               # 通用组件
│   ├── config/                               # 配置类
│   ├── controller/                           # 控制器
│   └── domain/                               # 领域对象
├── src/main/resources/                       # 资源文件
│   ├── application.yml                       # 主配置文件
│   ├── application-dev.yml                   # 开发环境配置
│   ├── application-test.yml                  # 测试环境配置
│   ├── application-prod.yml                  # 生产环境配置
│   └── logback-spring.xml                    # 日志配置
└── lib/                                      # 本地依赖库
    └── k3cloud-webapi-sdk8.0.6.jar          # 金蝶 SDK
```

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+

### 2. 配置文件

修改 `src/main/resources/application-dev.yml` 中的金蝶云星空配置：

```yaml
kingdee:
  k3cloud:
    web-api:
      server-url: http://your-k3cloud-server/k3cloud/
      acct-id: your-account-id
      user-name: your-username
      app-id: your-app-id
      app-sec: your-app-secret
      lc-id: 2052
      org-num: 100
      connect-timeout: 360
      request-timeout: 360
      stock-timeout: 180
      print-execute-url: on
```

### 3. 运行项目

```bash
# 开发环境
mvn spring-boot:run -P dev

# 测试环境
mvn spring-boot:run -P test

# 生产环境
mvn spring-boot:run -P prod
```

### 4. 打包部署

```bash
# 打包开发环境
mvn clean package -P dev

# 打包测试环境
mvn clean package -P test

# 打包生产环境
mvn clean package -P prod
```

## 使用示例

### WebApi 调用示例

```java
@Autowired
private WebApiHelper webApiHelper;

// 查询数据
public void queryData() {
    ViewRequest request = new ViewRequest();
    request.setFormId("BD_Material");
    // ... 设置其他参数
    
    WebApiResp<ViewResult> response = webApiHelper.viewResult(request);
    if (response.getResult().getResponseStatus().isIsSuccess()) {
        // 处理成功结果
        ViewResult result = response.getResult();
    }
}

// 保存数据
public void saveData() {
    SaveRequest request = new SaveRequest();
    request.setFormId("BD_Material");
    // ... 设置数据
    
    WebApiResp<SaveResult> response = webApiHelper.saveResult(request);
    if (response.getResult().getResponseStatus().isIsSuccess()) {
        // 处理成功结果
    }
}
```

## 配置说明

### 超时时间配置

- `connect-timeout`: 连接超时时间（秒），默认 360
- `request-timeout`: 请求超时时间（秒），默认 360
- `stock-timeout`: 库存相关操作超时时间（秒），默认 180

### 日志配置

可在 `logback-spring.xml` 中配置日志级别和输出格式。

## 注意事项

1. 确保金蝶云星空服务器网络可达
2. 正确配置 `app-id` 和 `app-sec`
3. 根据实际业务调整超时时间
4. 生产环境建议调整日志级别为 `info` 或 `warn`

## 更新日志

### v1.0.0
- 初始版本发布
- 封装金蝶云星空 WebApi 基础功能
- 支持多环境配置
- 集成常用工具库

## 许可证

根据项目 LICENSE 文件确定

## 联系方式

如有问题或建议，请提交 Issue。
