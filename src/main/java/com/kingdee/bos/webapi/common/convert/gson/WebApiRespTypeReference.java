package com.kingdee.bos.webapi.common.convert.gson;


import com.google.gson.reflect.TypeToken;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 提供用于反序列化金蝶云星空WebAPI响应数据的类型引用常量。
 * 该类包含一系列预定义的TypeToken实例，用于处理不同操作类型的响应。
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
    public static final Type SAVE_TYPE_REFERENCE = new TypeToken<WebApiResp<SaveResult>>() {
    }.getType();

    /**
     * 批量保存操作响应的类型引用。
     */
    public static final Type BATCH_SAVE_TYPE_REFERENCE = new TypeToken<WebApiResp<BatchSaveResult>>() {
    }.getType();

    /**
     * 转换操作响应的类型引用。
     */
    public static final Type CONVERT_TYPE_REFERENCE = new TypeToken<WebApiResp<ConvertResult>>() {
    }.getType();

    /**
     * 操作结果响应的类型引用。
     */
    public static final Type OPERATOR_TYPE_REFERENCE = new TypeToken<WebApiResp<OperatorResult>>() {
    }.getType();

    /**
     * 查看操作响应的类型引用。
     */
    public static final Type VIEW_TYPE_REFERENCE = new TypeToken<WebApiResp<ViewResult>>() {
    }.getType();

    /**
     * 附件上传操作响应的类型引用。
     */
    public static final Type ATTACHMENT_UPLOAD_TYPE_REFERENCE = new TypeToken<WebApiResp<AttachmentUploadResult>>() {
    }.getType();

    /**
     * 附件下载操作响应的类型引用。
     */
    public static final Type ATTACHMENT_DOWNLOAD_TYPE_REFERENCE = new TypeToken<WebApiResp<AttachmentDownLoadResult>>() {
    }.getType();

    /**
     * 用于解析 List<List<Object>> 的 TypeToken。
     */
    public static final Type LIST_LIST_OBJECT_TYPE_REFERENCE = new TypeToken<List<List<Object>>>() {
    }.getType();
}
