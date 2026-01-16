package com.kingdee.bos.webapi.common.convert;

import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;

import java.util.List;

/**
 * 抽象类，用于将API响应字符串解析为特定类型的 {@link WebApiResp} 对象。
 * 提供了一系列抽象方法,用于处理不同业务场景下的API响应解析。
 * 每个方法接收一个JSON格式的响应字符串，并将其转换为对应的泛型 {@link WebApiResp} 实例。
 * <p>
 * 该类的设计目的是为不同的API响应类型提供统一的解析接口，具体的解析逻辑由子类实现。
 * 所有解析方法均以字符串形式接收API响应，并返回包含特定业务结果的 {@link WebApiResp} 对象。
 *
 * @author xueyu
 */
public abstract class WebApiResponseConverter {


    /**
     * 转换结果为 {@link WebApiResp<OperatorResult> }
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<OperatorResult> parseOperatorWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<SaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<SaveResult> parseSaveWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<ViewResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<ViewResult> parseViewWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<ConvertResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<ConvertResult> parseConvertWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<BatchSaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<BatchSaveResult> parseBatchSaveWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentUploadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<AttachmentUploadResult> parseAttachmentUploadWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentDownLoadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    public abstract WebApiResp<AttachmentDownLoadResult> parseAttachmentDownLoadWebApiResponse(String respStr);

    /**
     * 转换结果为 {@link LoginResult}
     *
     * @param respStr json结果
     * @return LoginResult
     */
    public abstract LoginResult parseLoginResponse(String respStr);

    /**
     * 转换结果为 {@link java.util.List} 的 {@link java.util.List<Object>}
     *
     * @param respStr json结果
     * @return List<List<Object>>
     */
    public abstract List<List<Object>> parseListListObjectApiResponse(String respStr);
}
