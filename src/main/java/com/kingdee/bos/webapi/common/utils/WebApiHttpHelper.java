package com.kingdee.bos.webapi.common.utils;

import cn.hutool.core.lang.Assert;
import com.kingdee.bos.webapi.common.convert.ConvertApiResponse;
import com.kingdee.bos.webapi.common.convert.fastjson.FastJsonConvertApiResponse;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import lombok.extern.slf4j.Slf4j;

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
public class WebApiHttpHelper {


    /**
     * WebApiProperties 是一个封装了金蝶 K3Cloud Web API 配置信息的类实例。
     * 该变量存储了与 Web API 交互所需的全部配置参数，包括服务地址、账套信息、用户凭据、超时设置等。
     * 这些配置信息通过依赖注入的方式初始化，确保了配置的灵活性和安全性。
     * <p>
     * 主要用途是为 WebApiHttpHelper 提供必要的配置支持，以便在与金蝶 K3Cloud Web API 交互时使用。
     * 配置内容可通过 application.yml 或其他配置文件进行外部化管理，便于动态调整。
     * <p>
     * 注意：该变量为私有且不可变，确保其在整个生命周期内的安全性和一致性。
     */
    private final WebApiProperties webApiProperties;


    /**
     * 用于将API响应字符串解析为特定业务对象的转换器实例。
     * 该变量持有 {@link ConvertApiResponse} 的实现，提供对不同业务场景下API响应的解析能力。
     * 通过该转换器，可以将JSON格式的API响应字符串解析为对应的 {@link WebApiResp} 波形对象，
     * 支持包括操作结果、保存结果、查看结果等多种业务类型的解析。
     * <p>
     * 该字段在类初始化时通过构造函数注入，确保了依赖的明确性和不可变性。
     * 具体的解析逻辑由 {@link ConvertApiResponse} 的实现类提供，调用方可以通过该字段访问解析方法。
     */
    private final ConvertApiResponse convertApiResponse;

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


}
