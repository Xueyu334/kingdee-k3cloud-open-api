package com.kingdee.bos.webapi.common.convert.fastjson;


import com.alibaba.fastjson2.TypeReference;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.util.List;

/**
 * 提供用于反序列化金蝶云星空WebAPI响应数据的类型引用常量。
 * 该类包含一系列预定义的TypeReference实例，用于处理不同操作类型的响应。
 * 每个常量对应一种特定的WebAPI操作响应类型，确保在JSON反序列化过程中能够正确保留泛型信息。
 * 使用这些常量可以避免在代码中直接创建匿名内部类，提高代码的可读性和维护性。
 * 主要应用于HTTP客户端或反序列化工具中，用于指定目标反序列化类型。
 * <p>
 * 注意：该类仅包含静态常量，不可实例化。
 *
 * @author xueyu
 */
public class WebApiRespTypeReference {

    /**
     * 保存操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<SaveResult>> SAVE_TYPE_REFERENCE = new TypeReference<WebApiResp<SaveResult>>() {
    };

    /**
     * 批量保存操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<BatchSaveResult>> BATCH_SAVE_TYPE_REFERENCE = new TypeReference<WebApiResp<BatchSaveResult>>() {
    };

    /**
     * 转换操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<ConvertResult>> CONVERT_TYPE_REFERENCE = new TypeReference<WebApiResp<ConvertResult>>() {
    };

    /**
     * 操作结果响应的类型引用。
     */
    public static final TypeReference<WebApiResp<OperatorResult>> OPERATOR_TYPE_REFERENCE = new TypeReference<WebApiResp<OperatorResult>>() {
    };

    /**
     * 查看操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<ViewResult>> VIEW_TYPE_REFERENCE = new TypeReference<WebApiResp<ViewResult>>() {
    };

    /**
     * 附件上传操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<AttachmentUploadResult>> ATTACHMENT_UPLOAD_TYPE_REFERENCE = new TypeReference<WebApiResp<AttachmentUploadResult>>() {
    };

    /**
     * 附件下载操作响应的类型引用。
     */
    public static final TypeReference<WebApiResp<AttachmentDownLoadResult>> ATTACHMENT_DOWNLOAD_TYPE_REFERENCE = new TypeReference<WebApiResp<AttachmentDownLoadResult>>() {
    };

    /**
     * 用于解析 List<List<Object>> 的 TypeReference。
     */
    public static final TypeReference<List<List<Object>>> LIST_LIST_OBJECT_TYPE_REFERENCE = new TypeReference<java.util.List<java.util.List<Object>>>() {
    };
}
