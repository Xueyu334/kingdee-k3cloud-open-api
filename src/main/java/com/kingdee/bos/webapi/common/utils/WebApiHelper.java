package com.kingdee.bos.webapi.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.exception.WebApiInvokeException;
import com.kingdee.bos.webapi.common.fastjson.WebApiRespTypeReference;
import com.kingdee.bos.webapi.domain.dto.request.*;
import com.kingdee.bos.webapi.domain.dto.request.save.SaveRequest;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;
import com.kingdee.bos.webapi.entity.RepoError;
import com.kingdee.bos.webapi.entity.RepoResult;
import com.kingdee.bos.webapi.entity.RepoStatus;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 云星空api调用-包装类
 *
 * @author xueyu
 */
@Slf4j
public class WebApiHelper {

    /**
     * 客户端
     */
    private final K3CloudApi k3CloudApi;


    private WebApiHelper(K3CloudApi k3CloudApi) {
        this.k3CloudApi = k3CloudApi;
    }

    public static WebApiHelper of(final K3CloudApi k3CloudApi) {
        Assert.notNull(k3CloudApi, () -> new NullPointerException("云星空WebApi客户端不能为空!"));
        return new WebApiHelper(k3CloudApi);
    }

    /**
     * 转换结果为 {@link  WebApiResp<OperatorResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<OperatorResult> parseOperatorWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.OPERATOR_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<SaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<SaveResult> parseSaveWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ViewResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<ViewResult> parseViewWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.VIEW_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<ConvertResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<ConvertResult> parseConvertWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.CONVERT_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<BatchSaveResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<BatchSaveResult> parseBatchSaveWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.BATCH_SAVE_TYPE_REFERENCE);
    }

    /**
     * 转换结果为 {@link  WebApiResp<AttachmentUploadResult>}
     *
     * @param respStr json结果
     * @return WebApiResp
     */
    private static WebApiResp<AttachmentUploadResult> parseAttachmentUploadWebApiResponse(String respStr) {
        return JSON.parseObject(respStr, WebApiRespTypeReference.ATTACHMENT_UPLOAD_TYPE_REFERENCE);
    }

    /**
     * 保存操作
     *
     * @param formId 单据的formId
     * @param data   保存的请求数据
     * @return 响应数据
     */
    public String save(String formId, String data) {
        try {
            return k3CloudApi.save(formId, data);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空保存操作出现异常!", e);
        }
    }

    /**
     * 保存并响应结果
     *
     * @param formId 单据id
     * @param data   数据
     * @return {@link WebApiResp}
     */
    public WebApiResp<SaveResult> saveResult(String formId, String data) {
        String saveRespStr = save(formId, data);
        return parseSaveWebApiResponse(saveRespStr);
    }

    /**
     * 保存并响应结果
     *
     * @param saveRequest 保存操作请求参数
     * @return {@link WebApiResp}
     */
    public WebApiResp<SaveResult> saveResult(SaveRequest saveRequest) {
        return saveResult(saveRequest.getFormId(), JSON.toJSONString(saveRequest));
    }

    /**
     * 删除操作
     *
     * @param formId   单据id
     * @param jsonData 参数
     * @return 删除结果字符串
     */
    public String delete(String formId, String jsonData) {
        try {
            return k3CloudApi.delete(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空删除操作出现异常!", e);
        }
    }

    /**
     * 删除操作
     *
     * @param formId   单据id
     * @param jsonData 参数
     * @return 删除结果字符集
     */
    public WebApiResp<OperatorResult> deleteResult(String formId, String jsonData) {
        String delete = delete(formId, jsonData);
        return parseOperatorWebApiResponse(delete);
    }

    /**
     * 暂存操作
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 暂存结果字符串
     */
    public String draft(String formId, String jsonData) {
        try {
            return k3CloudApi.draft(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空暂存数据出现异常!", e);
        }
    }

    /**
     * 暂存操作
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 暂存结果
     */
    public WebApiResp<SaveResult> draftResult(String formId, String jsonData) {
        String draft = draft(formId, jsonData);
        return parseSaveWebApiResponse(draft);
    }

    /**
     * 暂存操作
     *
     * @param saveRequest 请求参数
     * @return 暂存结果
     */
    public WebApiResp<SaveResult> draftResult(SaveRequest saveRequest) {
        String draft = draft(saveRequest.getFormId(), JSON.toJSONString(saveRequest));
        return parseSaveWebApiResponse(draft);
    }

    /**
     * 查看单据
     *
     * @param formId   单据id
     * @param jsonData json请求参数
     * @return 单据数据包字符串
     */
    public String view(String formId, String jsonData) {
        try {
            return k3CloudApi.view(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空查看单据出现异常!", e);
        }
    }

    /**
     * 查看
     *
     * @param formId   单据id
     * @param jsonData 请求参数
     * @return 查看结果
     */
    public WebApiResp<ViewResult> viewResult(String formId, String jsonData) {
        String view = view(formId, jsonData);
        return parseViewWebApiResponse(view);
    }

    /**
     * 查看
     *
     * @param viewRequest 请求参数
     * @return 查看结果
     */
    public WebApiResp<ViewResult> viewResult(ViewRequest viewRequest) {
        return viewResult(viewRequest.getFormId(), JSON.toJSONString(viewRequest));
    }

    /**
     * 提交
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public String submit(String formId, String jsonData) {
        try {
            return k3CloudApi.submit(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空提交操作出现异常!", e);
        }
    }

    /**
     * 提交
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> submitResult(String formId, String jsonData) {
        String submit = submit(formId, jsonData);
        return parseOperatorWebApiResponse(submit);
    }


    /**
     * 提交
     *
     * @param submitRequest 请求参数
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> submitResult(SubmitRequest submitRequest) {
        return submitResult(submitRequest.getFormId(), JSON.toJSONString(submitRequest));
    }


    /**
     * 审核
     *
     * @param formId   单据id
     * @param jsonData 请求参数
     * @return 结果响应
     */
    public String audit(String formId, String jsonData) {
        try {
            return k3CloudApi.audit(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空审核操作出现异常!", e);
        }
    }

    /**
     * 审核
     *
     * @param formId   单据id
     * @param jsonData 请求参数
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> auditResult(String formId, String jsonData) {
        String audit = audit(formId, jsonData);
        return parseOperatorWebApiResponse(audit);
    }

    /**
     * 审核
     *
     * @param auditRequest 请求参数
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> auditResult(AuditRequest auditRequest) {
        return auditResult(auditRequest.getFormId(), JSON.toJSONString(auditRequest));
    }

    /**
     * 反审核
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public String unAudit(String formId, String jsonData) {
        try {
            return k3CloudApi.unAudit(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空反审核操作出现异常!", e);
        }
    }

    /**
     * 反审核
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> unAuditResult(String formId, String jsonData) {
        String unAudit = unAudit(formId, jsonData);
        return parseOperatorWebApiResponse(unAudit);
    }

    /**
     * 反审核
     *
     * @param unAuditRequest 操作请求参数
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> unAuditResult(UnAuditRequest unAuditRequest) {
        return unAuditResult(unAuditRequest.getFormId(), JSON.toJSONString(unAuditRequest));
    }

    /**
     * 撤销
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public String cancelAssign(String formId, String jsonData) {
        try {
            return k3CloudApi.cancelAssign(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空撤销操作出现异常!", e);
        }
    }

    /**
     * 撤销
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> cancelAssignResult(String formId, String jsonData) {
        String cancelAssign = cancelAssign(formId, jsonData);
        return parseOperatorWebApiResponse(cancelAssign);
    }

    /**
     * 撤销
     *
     * @param cancelAssignRequest 请求参数
     * @return 结果响应
     */
    public WebApiResp<OperatorResult> cancelAssignResult(CancelAssignRequest cancelAssignRequest) {
        return cancelAssignResult(cancelAssignRequest.getFormId(), JSON.toJSONString(cancelAssignRequest));
    }

    /**
     * 下推
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public String push(String formId, String jsonData) {
        try {
            return k3CloudApi.push(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空下推操作出现异常!", e);
        }
    }

    /**
     * 下推
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 结果响应
     */
    public WebApiResp<ConvertResult> pushResult(String formId, String jsonData) {
        String push = push(formId, jsonData);
        return parseConvertWebApiResponse(push);
    }

    /**
     * 下推
     *
     * @param pushRequest 下推请求
     * @return 结果响应
     */
    public WebApiResp<ConvertResult> pushResult(PushRequest pushRequest) {
        return pushResult(pushRequest.getFormId(), JSON.toJSONString(pushRequest));
    }

    /**
     * 作废
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public String cancel(String formId, String jsonData) {
        try {
            return k3CloudApi.excuteOperation(formId, "Cancel", jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空作废操作出现异常!", e);
        }
    }

    /**
     * 作废
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> cancelResult(String formId, String jsonData) {
        String cancel = cancel(formId, jsonData);
        return parseOperatorWebApiResponse(cancel);
    }

    /**
     * 作废
     *
     * @param cancelRequest 请求参数
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> cancelResult(CancelRequest cancelRequest) {
        return cancelResult(cancelRequest.getFormId(), JSON.toJSONString(cancelRequest));
    }

    /**
     * 整单关闭
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public String billClose(String formId, String jsonData) {
        try {
            return k3CloudApi.excuteOperation(formId, "BillClose", jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空整单关闭操作出现异常!", e);
        }
    }

    /**
     * 整单关闭
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> billCloseResult(String formId, String jsonData) {
        String billClose = billClose(formId, jsonData);
        return parseOperatorWebApiResponse(billClose);
    }

    /**
     * 整单关闭
     *
     * @param billCloseRequest 请求参数
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> billCloseResult(BillCloseRequest billCloseRequest) {
        return billCloseResult(billCloseRequest.getFormId(), JSON.toJSONString(billCloseRequest));
    }

    /**
     * 单据反关闭
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public String billUnClose(String formId, String jsonData) {
        try {
            return k3CloudApi.excuteOperation(formId, "BillUnClose", jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空单据反关闭操作出现异常!", e);
        }
    }

    /**
     * 单据反关闭
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> billUnCloseResult(String formId, String jsonData) {
        String billUnClose = billUnClose(formId, jsonData);
        return parseOperatorWebApiResponse(billUnClose);
    }

    /**
     * 单据反关闭
     *
     * @param billUnCloseRequest 请求参数
     * @return 响应数据
     */
    public WebApiResp<OperatorResult> billUnCloseResult(BillUnCloseRequest billUnCloseRequest) {
        return billUnCloseResult(billUnCloseRequest.getFormId(), JSON.toJSONString(billUnCloseRequest));
    }

    /**
     * 单据批量保存
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public String batchSave(String formId, String jsonData) {
        try {
            return k3CloudApi.batchSave(formId, jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空单据批量保存操作出现异常!", e);
        }
    }

    /**
     * 单据批量保存
     *
     * @param formId   单据id
     * @param jsonData json数据
     * @return 响应数据
     */
    public WebApiResp<BatchSaveResult> batchSaveResult(String formId, String jsonData) {
        String batchSave = batchSave(formId, jsonData);
        return parseBatchSaveWebApiResponse(batchSave);
    }

    /**
     * 单据查询
     *
     * @param jsonData json数据
     * @return 二维数组
     */
    public List<List<Object>> executeBillQuery(String jsonData) {
        try {
            return k3CloudApi.executeBillQuery(jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空单据查询出现异常!", e);
        }
    }

    /**
     * 单据查询
     *
     * @param jsonData json数据
     * @return 二维数据 json字符串
     */
    public String executeBillQueryJson(String jsonData) {
        try {
            return k3CloudApi.executeBillQueryJson(jsonData);
        } catch (Exception e) {
            throw new WebApiInvokeException("调用云星空单据查询出现异常!", e);
        }
    }

    /**
     * 指定自定义OpenApi接口
     *
     * @param serviceName openApi接口名称  <pre>{@code yh.dev.application.cloud.CustomerWebApi.ExecuteService,yh.dev.application}</pre>
     * @param params      请求参数
     * @return 响应结果 String
     */
    public String execute(String serviceName, String params) {
        try {
            RepoResult<?> repoResult = k3CloudApi.CheckAuthInfo();
            RepoStatus responseStatus = repoResult.getResponseStatus();
            if (responseStatus.isIsSuccess()) {
                Object[] paramInfo = JSON.parseObject(JSON.toJSONBytes(new Object[]{params}), Object[].class);
                return k3CloudApi.execute(serviceName, paramInfo);
            } else {
                String errorMessage = Optional.ofNullable(responseStatus.getErrors())
                        .filter(CollectionUtil::isNotEmpty)
                        .map(errors -> errors.stream()
                                .map(RepoError::getMessage)
                                .collect(Collectors.joining(",", "[", "]")))
                        .orElse("CheckAuthInfo验证失败!");
                throw new WebApiInvokeException(errorMessage);
            }
        } catch (Exception e) {
            throw new WebApiInvokeException("自定义OpenApi执行异常!", e);
        }
    }

    /**
     * 通过K3CloudApi直接执行操作
     *
     * @param function 操作
     * @return 操作结果
     */
    public <V> V executeByK3CloudApi(Function<K3CloudApi, V> function) {
        return function.apply(k3CloudApi);
    }

    /**
     * 附件上传接口
     *
     * @param data 数据
     * @return 附件上传响应结果
     */
    public String attachmentUpload(String data) {
        try {
            return k3CloudApi.attachmentUpload(data);
        } catch (Exception e) {
            throw new WebApiInvokeException("附件上传异常!", e);
        }
    }

    /**
     * 附件上传接口
     *
     * @param attachmentUpLoadRequest 数据 {@link  AttachmentUpLoadRequest}
     * @return 附件上传响应结果
     */
    public String attachmentUpload(AttachmentUpLoadRequest attachmentUpLoadRequest) {
        return attachmentUpload(JSON.toJSONString(attachmentUpLoadRequest));
    }

    /**
     * 附件上传接口
     *
     * @param attachmentUpLoadRequest 数据 {@link  AttachmentUpLoadRequest}
     * @return 附件上传响应结果
     */
    public WebApiResp<AttachmentUploadResult> attachmentUploadResult(AttachmentUpLoadRequest attachmentUpLoadRequest) {
        String respStr = attachmentUpload(attachmentUpLoadRequest);
        return parseAttachmentUploadWebApiResponse(respStr);
    }

}
