package com.kingdee.bos.webapi.common.convert.fastjson;

import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.convert.ConvertApiResponse;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;
import lombok.extern.slf4j.Slf4j;

/**
 * FastJsonConvertApiResponse 是 ConvertApiResponse 的具体实现类，用于将 JSON 格式的 API 响应字符串解析为特定类型的 {@link WebApiResp} 对象。
 * 该类利用 FastJSON 提供的解析能力，结合预定义的类型引用（{@link WebApiRespTypeReference}），将响应字符串转换为包含特定业务结果的泛型对象。
 * 每个方法都接收一个 JSON 格式的响应字符串，并返回对应的 {@link WebApiResp} 实例，适用于不同的业务场景。
 * <p>
 * 该类的主要功能是为金蝶云星空 WebAPI 的响应数据提供统一的解析接口，确保解析过程的高效性和类型安全性。
 * 所有解析方法均依赖于 FastJSON,确保反序列化时保留泛型信息。
 * <p>
 * 注意：该类的具体实现依赖于 {@link WebApiRespTypeReference} 中定义的类型引用，以支持运行时的泛型反序列化。
 *
 * @apiNote
 */
@Slf4j
public class FastJsonConvertApiResponse extends ConvertApiResponse {


    /**
     * 转换结果为 {@link  WebApiResp < OperatorResult >}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<OperatorResult> parseOperatorWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.OPERATOR_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<SaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<SaveResult> parseSaveWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ViewResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<ViewResult> parseViewWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.VIEW_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ConvertResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<ConvertResult> parseConvertWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.CONVERT_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<BatchSaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<BatchSaveResult> parseBatchSaveWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.BATCH_SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentUploadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<AttachmentUploadResult> parseAttachmentUploadWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.ATTACHMENT_UPLOAD_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentDownLoadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public WebApiResp<AttachmentDownLoadResult> parseAttachmentDownLoadWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.ATTACHMENT_DOWNLOAD_TYPE_REFERENCE);
    }


}
