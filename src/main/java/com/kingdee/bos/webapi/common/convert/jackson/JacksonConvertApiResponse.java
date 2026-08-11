package com.kingdee.bos.webapi.common.convert.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.bos.webapi.common.convert.WebApiResponseConverter;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.util.List;
import java.util.Objects;

/**
 * 使用 Jackson 将金蝶云星空 Web API 的 JSON 响应转换为对应的结果对象。
 *
 * @author xueyu
 */
public class JacksonConvertApiResponse extends WebApiResponseConverter {

    /**
     * 使用默认 {@link ObjectMapper} 的共享转换器实例。
     */
    public static final JacksonConvertApiResponse INSTANCE = new JacksonConvertApiResponse();

    /**
     * 用于反序列化 JSON 响应的 Jackson 映射器。
     */
    private final ObjectMapper objectMapper;

    /**
     * 使用默认 {@link ObjectMapper} 创建转换器。
     */
    public JacksonConvertApiResponse() {
        this(new ObjectMapper());
    }

    /**
     * 使用指定的 {@link ObjectMapper} 创建转换器。
     *
     * @param objectMapper 用于 JSON 反序列化的映射器
     * @throws NullPointerException 当 {@code objectMapper} 为 {@code null} 时抛出
     */
    public JacksonConvertApiResponse(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper, "objectMapper must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<OperatorResult> parseOperatorWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.OPERATOR_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<SaveResult> parseSaveWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.SAVE_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<ViewResult> parseViewWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.VIEW_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<ConvertResult> parseConvertWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.CONVERT_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<BatchSaveResult> parseBatchSaveWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.BATCH_SAVE_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<AttachmentUploadResult> parseAttachmentUploadWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.ATTACHMENT_UPLOAD_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebApiResp<AttachmentDownLoadResult> parseAttachmentDownLoadWebApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.ATTACHMENT_DOWNLOAD_TYPE_REFERENCE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoginResult parseLoginResponse(String respStr) {
        return readValue(respStr, LoginResult.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Object>> parseListListObjectApiResponse(String respStr) {
        return readValue(respStr, WebApiRespTypeReference.LIST_LIST_OBJECT_TYPE_REFERENCE);
    }

    /**
     * 使用携带泛型信息的类型引用解析 JSON 响应。
     *
     * @param respStr       待解析的 JSON 响应
     * @param typeReference 目标类型引用
     * @param <T>           目标对象类型
     * @return 解析后的目标对象
     * @throws IllegalArgumentException 当 JSON 无法解析为目标类型时抛出
     */
    private <T> T readValue(String respStr, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(respStr, typeReference);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("解析金蝶云星空 Web API 响应失败", e);
        }
    }

    /**
     * 使用非泛型目标类型解析 JSON 响应。
     *
     * @param respStr 待解析的 JSON 响应
     * @param type    目标对象类型
     * @param <T>     目标对象类型
     * @return 解析后的目标对象
     * @throws IllegalArgumentException 当 JSON 无法解析为目标类型时抛出
     */
    private <T> T readValue(String respStr, Class<T> type) {
        try {
            return objectMapper.readValue(respStr, type);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("解析金蝶云星空 Web API 响应失败", e);
        }
    }
}
