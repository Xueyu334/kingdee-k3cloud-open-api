package com.kingdee.bos.webapi.common.convert.fastjson;


import com.alibaba.fastjson2.TypeReference;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.util.List;

/**
 * 支持运行时 反序列化保存泛型
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
