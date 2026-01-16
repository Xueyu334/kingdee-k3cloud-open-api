package com.kingdee.bos.webapi.common.convert.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kingdee.bos.webapi.common.convert.ConvertApiResponse;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * GsonConvertApiResponse 是 ConvertApiResponse 的具体实现类，用于将 JSON 格式的 API 响应字符串解析为特定类型的 {@link WebApiResp} 对象。
 * 该类利用 Gson 提供的解析能力，结合预定义的类型引用（{@link WebApiRespTypeReference}），将响应字符串转换为包含特定业务结果的泛型对象。
 * 每个方法都接收一个 JSON 格式的响应字符串，并返回对应的 {@link WebApiResp} 实例，适用于不同的业务场景。
 * <p>
 * 该类的主要功能是为金蝶云星空 WebAPI 的响应数据提供统一的解析接口，确保解析过程的高效性和类型安全性。
 * 所有解析方法均依赖于 Gson,确保反序列化时保留泛型信息。
 * <p>
 * 注意：该类的具体实现依赖于 {@link WebApiRespTypeReference} 中定义的类型引用，以支持运行时的泛型反序列化。
 *
 * @apiNote
 */
@Slf4j
public class GsonConvertApiResponse extends ConvertApiResponse {

    /**
     * GsonConvertApiResponse 的单例实例。
     */
    public static final GsonConvertApiResponse INSTANCE = new GsonConvertApiResponse();

    /**
     * Gson 实例，用于 JSON 解析。
     * 使用默认配置，简单高效。
     */
    private static final Gson GSON = new GsonBuilder().create();

    /**
     * 转换结果为 {@link  WebApiResp<OperatorResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<OperatorResult> parseOperatorWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.OPERATOR_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<SaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<SaveResult> parseSaveWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ViewResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<ViewResult> parseViewWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.VIEW_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ConvertResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<ConvertResult> parseConvertWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.CONVERT_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<BatchSaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<BatchSaveResult> parseBatchSaveWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.BATCH_SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentUploadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<AttachmentUploadResult> parseAttachmentUploadWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.ATTACHMENT_UPLOAD_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentDownLoadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<AttachmentDownLoadResult> parseAttachmentDownLoadWebApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.ATTACHMENT_DOWNLOAD_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link LoginResult}
     *
     * @param respStr json结果
     * @return LoginResult
     */
    @Override
    public LoginResult parseLoginResponse(String respStr) {
        return GSON.fromJson(respStr, LoginResult.class);
    }

    /**
     * 转换结果为 {@link java.util.List} 的 {@link java.util.List<Object>}
     *
     * @param respStr json结果
     * @return List<List<Object>>
     */
    @Override
    public List<List<Object>> parseListListObjectApiResponse(String respStr) {
        return GSON.fromJson(respStr, WebApiRespTypeReference.LIST_LIST_OBJECT_TYPE_REFERENCE);
    }

}
