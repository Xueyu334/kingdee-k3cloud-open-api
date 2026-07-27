# AGENTS.md

本文件适用于整个仓库，供在本项目中工作的自动化代理使用。

## 项目概况

- 本项目是金蝶云星空 K3 Cloud WebAPI 的 Java 封装及示例应用。
- 技术基线以 `pom.xml` 为准：Java 17、Spring Boot 3.5.9、Maven。
- `com.kingdee.bos.webapi` 是 WebAPI/SDK 适配层；`com.rain` 是应用、配置及通用扩展层。
- 金蝶 SDK 通过 `system` 作用域引用 `lib/k3cloud-webapi-sdk-java11-v8.2.0.jar`。不要随意移动、改名或删除该文件，也不要在未经确认时替换 SDK 版本。

## 修改原则

- 修改前先定位现有实现和调用方，优先做满足需求的最小改动。
- 保持现有包结构和职责边界；通用的金蝶接口封装放在 `com.kingdee.bos.webapi`，应用特定逻辑放在 `com.rain`。
- 不要仅依据 README 中可能过时的版本或示例修改依赖，依赖与插件版本以 `pom.xml` 为准。
- 项目同时使用 FastJSON2、Gson 和少量 Jackson 注解。扩展 JSON 转换时沿用目标代码所在链路，不要无理由混用或整体替换序列化方案。
- 保持公开 API、请求/响应 DTO 字段和金蝶接口字段的兼容性；若必须进行破坏性变更，应先说明影响。
- 遵循目标文件现有 Java 风格和 Lombok 用法，避免无关格式化、批量重命名和顺手重构。

## 配置与安全

- Maven 环境包括 `local`（默认）、`dev`、`test`、`prod`；`application.yml` 使用 `@profiles.active@` 和 `@logging.level@` 进行 Maven 资源过滤。
- 不要将资源过滤占位符改成 Spring `${...}`，除非任务明确要求调整构建方式。
- `src/main/resources/application-local.yml` 是本地配置且已被 Git 忽略。
- 不得提交真实的账套 ID、用户名、应用 ID、应用密钥、SessionId、内网地址或其他凭据；示例值应明确使用占位符。
- 调试日志和测试输出不得暴露认证信息或完整敏感请求内容。

## 构建与验证

在仓库根目录按修改范围执行尽量轻量的验证：

```bash
# 编译
mvn compile -P local

# 运行全部测试
mvn test -P local

# 运行指定测试类
mvn test -P local -Dtest=K3CloudHttpTest

# 打包指定环境
mvn clean package -P local
```

- `K3CloudHttpTest` 等测试会访问真实金蝶服务并依赖本地凭据，不能把它们当作默认的离线单元测试运行。
- 只涉及纯工具类或本地单元测试时，优先运行对应测试类；涉及配置、启动或打包行为时，再执行相应 profile 的编译或打包。
- Maven 构建依赖 `lib/` 下的本地 SDK JAR；缺失该文件时应明确报告，不要自动下载或替换。
- 只报告实际执行且成功的检查；无法运行在线集成测试时，应说明验证边界。

## 测试约定

- 新测试使用 JUnit 5，并放在与生产包结构对应的 `src/test/java` 路径下。
- 可离线验证的逻辑应写成不依赖外部金蝶服务的单元测试。
- 必须调用真实 K3 Cloud 的测试应清楚标明前置条件，避免写入或删除真实业务数据，并避免在异常处理后让测试无条件通过。
- 涉及并发、超时或缓存的修改，应使用有界等待和稳定断言，避免依赖任意时长的 `Thread.sleep`。

## 文档同步

- 新增或调整公开 API、配置项、环境 profile、启动方式时，同步更新 `README.md` 或相关文档。
- 文档中的版本、命令和路径应在当前源码及 `pom.xml` 中复核后再写入。
