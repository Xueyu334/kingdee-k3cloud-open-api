package com.kingdee.bos.webapi.common.convert.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.util.List;

/**
 * Jackson 解析金蝶云星空 Web API 响应时使用的泛型类型引用。
 */
public final class WebApiRespTypeReference {

    /**
     * 保存操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<SaveResult>> SAVE_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 批量保存操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<BatchSaveResult>> BATCH_SAVE_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 转换操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<ConvertResult>> CONVERT_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 操作结果响应的类型引用。
     */
    public static final TypeReference<WebApiResp<OperatorResult>> OPERATOR_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 查看操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<ViewResult>> VIEW_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 附件上传操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<AttachmentUploadResult>> ATTACHMENT_UPLOAD_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 附件下载操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<AttachmentDownLoadResult>> ATTACHMENT_DOWNLOAD_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 二维查询结果的类型引用。
     */
    public static final TypeReference<List<List<Object>>> LIST_LIST_OBJECT_TYPE_REFERENCE = new TypeReference<>() {
    };

    /**
     * 禁止实例化工具类。
     */
    private WebApiRespTypeReference() {
    }
}
