package com.kingdee.bos.webapi.common.fastjson;


import com.alibaba.fastjson2.TypeReference;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

/**
 * 支持运行时 反序列化保存泛型
 *
 * @author xueyu
 */
public class WebApiRespTypeReference {

    public static final TypeReference<WebApiResp<SaveResult>> SAVE_TYPE_REFERENCE = new TypeReference<WebApiResp<SaveResult>>() {
    };

    public static final TypeReference<WebApiResp<BatchSaveResult>> BATCH_SAVE_TYPE_REFERENCE = new TypeReference<WebApiResp<BatchSaveResult>>() {
    };

    public static final TypeReference<WebApiResp<ConvertResult>> CONVERT_TYPE_REFERENCE = new TypeReference<WebApiResp<ConvertResult>>() {
    };

    public static final TypeReference<WebApiResp<OperatorResult>> OPERATOR_TYPE_REFERENCE = new TypeReference<WebApiResp<OperatorResult>>() {
    };

    public static final TypeReference<WebApiResp<ViewResult>> VIEW_TYPE_REFERENCE = new TypeReference<WebApiResp<ViewResult>>() {
    };

    public static final TypeReference<WebApiResp<AttachmentUploadResult>> ATTACHMENT_UPLOAD_TYPE_REFERENCE = new TypeReference<WebApiResp<AttachmentUploadResult>>() {
    };


}
