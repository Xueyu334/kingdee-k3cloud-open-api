package com.kingdee.bos.webapi.common.enums;

import lombok.Getter;

/**
 * 枚举类，用于定义金蝶Web API的服务接口。
 * 该类封装了金蝶系统中常用的Web API服务端点，每个枚举实例代表一个具体的API服务。
 * 每个服务包含唯一的服务名称标识符和对应的中文描述信息，便于在代码中引用和识别。
 * 服务名称遵循金蝶系统的命名规范，通常包含完整的命名空间、服务类名和方法名。
 * 该枚举类主要用于在金蝶Web API调用过程中，指定目标服务端点，确保请求的正确性和一致性。
 * 通过使用此枚举，开发者可以避免硬编码服务名称，提高代码的可维护性和可读性。
 * 枚举实例为常量，一旦初始化后不可更改，保证了服务标识的稳定性和可靠性。
 *
 * @author xueyu
 */
@Getter
public enum WebApiService {

    /**
     * 登录服务接口名称
     */
    LOGIN_BY_SIGN("Kingdee.BOS.WebApi.ServicesStub.AuthService.LoginBySign", "登录"),

    /**
     * 保存服务接口名称
     */
    SAVE("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save", "保存"),

    /**
     * 单据查询服务接口名称
     */
    EXECUTE_BILL_QUERY("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery", "单据查询"),

    /**
     * 获取报表数据服务接口名称
     */
    GET_REPORT_DATA("Kingdee.BOS.KDS.ServiceFacade.ServicesStub.KDSReportAPIStub.GetReportData,Kingdee.BOS.KDS.ServiceFacade.ServicesStub", "获取报表数据");

    /**
     * 表示金蝶Web API服务接口的唯一标识符。
     * 该字段存储了服务接口的完整路径名称，用于在调用API时指定具体的服务端点。
     * 服务名称遵循金蝶系统的命名规范，通常包含命名空间、服务类名和方法名。
     * 此字段为常量，一旦在枚举实例中初始化后便不可更改。
     */
    private final String serviceName;

    /**
     * 服务接口的中文描述信息。
     */
    private final String description;

    WebApiService(String serviceName, String description) {
        this.serviceName = serviceName;
        this.description = description;
    }
}
