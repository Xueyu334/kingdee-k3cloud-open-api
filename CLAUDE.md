# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Test Commands

### Maven Build

```bash
# Build with specific environment profile (default: local)
mvn clean package -P local

# Available profiles: local, dev, test, prod
mvn clean package -P dev
mvn clean package -P test
mvn clean package -P prod

# Build skips tests
mvn clean package -DskipTests
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=K3CloudHttpTest

# Run specific test method
mvn test -Dtest=K3CloudHttpTest#testLoginBySign
```

### Running the Application

```bash
# Run with Spring Boot Maven plugin
mvn spring-boot:run -P local

# Run the JAR directly after build
java -jar target/kingdee-k3cloud-open-api-local.jar
```

## Architecture Overview

This is a **Java SDK wrapper** for Kingdee K3 Cloud WebAPI that provides a simplified interface to Kingdee ERP operations. The project follows a **layered architecture** with two main module layers:

### Module Structure

**1. `com.kingdee.bos.webapi` - K3 Cloud SDK Integration Layer**
   - Core wrapper around the proprietary K3 Cloud SDK (`lib/k3cloud-webapi-sdk-java11-v8.2.0.jar`)
   - Contains HTTP communication, authentication, and API endpoint abstractions
   - Key classes:
     - `WebApiHelper` - Primary facade for all K3 Cloud operations (save, view, submit, audit, delete, etc.)
     - `WebApiHttpHelper` - HTTP client wrapper with connection pooling and timeout management
     - `WebApiResponseConverter` - Converts raw SDK responses to structured `WebApiResp<T>` objects

**2. `com.rain` - Application Layer**
   - Business-specific extensions and custom logic
   - Application configuration and event handling
   - Custom utilities and exceptions

### Design Patterns

- **Adapter Pattern**: `WebApiResponseConverter` adapts raw K3 SDK responses to standardized objects with support for FastJSON2 and Gson
- **Builder Pattern**: Request objects (e.g., `SaveRequest`) use `ModelMap` for fluent data construction
- **Factory Pattern**: `WebApiHttpHelper.of(properties)` creates configured HTTP helper instances
- **Strategy Pattern**: Pluggable JSON conversion strategies via response converters

### API Operation Types

**CRUD Operations**: Save, View, Delete
**Workflow Operations**: Submit, Audit, UnAudit
**Batch Operations**: BatchSave, ExecuteBillQuery
**Document Operations**: Attachment upload/download with chunking support
**Advanced**: Push, Cancel, BillClose

## Configuration

### Multi-Environment Setup

The project uses Maven profiles for environment-specific builds. Each profile has its own configuration file:

- `application-local.yml` (default)
- `application-dev.yml`
- `application-test.yml`
- `application-prod.yml`

Configuration is loaded via `@ConfigurationProperties` in `WebApiProperties.java`.

### Key Configuration Properties

Located in `application.yml` files under `kingdee.k3cloud.web-api`:

```yaml
kingdee:
  k3cloud:
    web-api:
      server-url: http://your-k3cloud-server/k3cloud/
      acct-id: your-acct-id
      user-name: your-username
      app-id: your-app-id
      app-sec: your-app-secret
      lc-id: 2052              # Language ID (2052=Simplified Chinese)
      org-num: 100             # Organization number
      connect-timeout: 360     # Connection timeout (seconds)
      request-timeout: 360     # Request timeout (seconds)
      stock-timeout: 180       # Socket timeout (seconds)
```

### System Dependency

The K3 Cloud SDK is bundled as a system dependency in `lib/k3cloud-webapi-sdk-java11-v8.2.0.jar`. This JAR must be present in the project directory.

## Exception Handling

The project uses a hierarchical exception structure:

```
BizException (base)
└── WebApiInvokeException
    └── WebApiResponseValidationException
```

All API operations return `WebApiResp<T>` which encapsulates:
- Success/failure status
- Response data
- Error codes and messages
- Automatic request/response logging

## Caching

The project uses **Caffeine** for high-performance caching. Configuration in `application.yml`:

```yaml
spring:
  cache:
    type: caffeine
    caffeine:
      spec: maximumSize=1000,expireAfterWrite=600s
```

Example usage in tests: `src/test/java/com/rain/caffeine/CaffeineTest.java`

## HTTP Client Configuration

Uses Apache HttpClient 5.3.1 with:
- Connection pooling
- Configurable timeouts (connect, request, socket)
- Support for proxy settings
- Chunked upload/download for large file attachments

## JSON Processing

The project supports **two JSON libraries**:
- **FastJSON2** (default) - Primary JSON processor
- **Gson** - Alternative JSON processor

Response converters are located in `com.kingdee.bos.webapi.common.convert`.

## Testing Guidelines

### Test Structure

- Main integration test: `K3CloudHttpTest.java`
- Performance tests demonstrate parallel execution and bulk operations
- Caffeine cache tests demonstrate caching patterns

### Test Configuration

Tests use `@SpringBootTest` and require valid K3 Cloud credentials configured in `application-local.yml` to run integration tests.

## Important Notes

1. **System Dependency**: The K3 Cloud SDK JAR in `lib/` is a system-scoped dependency and must be manually present
2. **Authentication**: K3 Cloud operations require prior login via `webApiHttpHelper.loginBySign()`
3. **Timeouts**: Default timeouts are generous (360s) for ERP operations - adjust based on your requirements
4. **Logging**: Set `is-print-execute-url: true` in config for debugging API calls
5. **Language**: The default `lc-id: 2052` is for Simplified Chinese. Adjust for other locales.
