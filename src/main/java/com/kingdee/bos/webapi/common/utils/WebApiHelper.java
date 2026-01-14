package com.kingdee.bos.webapi.common.utils;


import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.convert.ConvertApiResponse;
import com.kingdee.bos.webapi.common.convert.fastjson.FastJsonConvertApiResponse;
import com.kingdee.bos.webapi.common.enums.WebApiService;
import com.kingdee.bos.webapi.common.exception.WebApiInvokeException;
import com.kingdee.bos.webapi.common.exception.WebApiResponseValidationException;
import com.kingdee.bos.webapi.domain.dto.request.*;
import com.kingdee.bos.webapi.domain.dto.request.save.SaveRequest;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.*;
import com.kingdee.bos.webapi.entity.RepoError;
import com.kingdee.bos.webapi.entity.RepoResult;
import com.kingdee.bos.webapi.entity.RepoStatus;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * WebApiHelper 是一个用于简化与金蝶云星空WebApi交互的辅助类。
 * 该类封装了常见的单据操作，如保存、删除、暂存、查看、提交、审核、反审核、撤销、下推、作废、整单关闭、单据反关闭、批量保存和单据查询等。
 * 通过提供两种类型的调用方式：直接返回原始字符串响应和返回封装后的WebApiResp对象，以满足不同场景下的需求。
 * 使用该类可以降低直接调用底层API的复杂度，并提供统一的错误处理和响应数据转换机制。
 *
 * @author xueyu
 */
@Slf4j
public class WebApiHelper {
    /**
     * 客户端
     */
    private final K3CloudApi k3CloudApi;
    /**
     * 用于存储转换后的API响应数据的私有最终变量。
     * 该变量持有将API原始响应经过处理或格式化后的结果，
     * 确保在程序运行期间不会被修改，从而保证数据的一致性和安全性。
     * 其具体类型和结构由 ConvertApiResponse 定义，通常包含解析后的内容或状态信息。
     */
    private final ConvertApiResponse convertApiResponse;

    /**
     * WebApiHelper类的私有构造函数，用于初始化WebApiHelper实例。
     * 该构造函数通过传入的K3CloudApi和ConvertApiResponse对象来配置当前实例。
     *
     * @param k3CloudApi         与K3Cloud进行交互的API接口实现，用于执行具体的Web API调用操作
     * @param convertApiResponse 负责将API响应数据转换为指定格式的处理类，用于解析和处理返回结果
     */
    private WebApiHelper(K3CloudApi k3CloudApi, ConvertApiResponse convertApiResponse) {
        this.k3CloudApi = k3CloudApi;
        this.convertApiResponse = convertApiResponse;
    }

    /**
     * 根据指定的云星空WebApi客户端和默认的响应转换器创建WebApiHelper实例。
     *
     * @param k3CloudApi 云星空WebApi客户端，不能为空
     * @return 返回一个WebApiHelper实例，该实例包含云星空WebApi客户端和默认的响应转换器
     */
    public static WebApiHelper of(final K3CloudApi k3CloudApi) {
        if (Objects.isNull(k3CloudApi)) {
            throw new NullPointerException("云星空WebApi客户端不能为空!");
        }
        return of(k3CloudApi, FastJsonConvertApiResponse.INSTANCE);
    }

    /**
     * 创建并返回一个 WebApiHelper 实例。
     *
     * @param k3CloudApi         云星空WebApi客户端，不能为空。如果为空，则抛出 NullPointerException 异常。
     * @param convertApiResponse 响应消息转换插件，不能为空。如果为空，则抛出 NullPointerException 异常。
     * @return 返回一个包含指定云星空WebApi客户端和响应消息转换插件的 WebApiHelper 实例。
     */
    public static WebApiHelper of(final K3CloudApi k3CloudApi, final ConvertApiResponse convertApiResponse) {
        if (Objects.isNull(k3CloudApi)) {
            throw new NullPointerException("云星空WebApi客户端不能为空!");
        }
        if (Objects.isNull(convertApiResponse)) {
            throw new NullPointerException("响应消息转换插件不能为空!");
        }
        return new WebApiHelper(k3CloudApi, convertApiResponse);
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
        return convertApiResponse.parseSaveWebApiResponse(saveRespStr);
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
        return convertApiResponse.parseOperatorWebApiResponse(delete);
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
        return convertApiResponse.parseSaveWebApiResponse(draft);
    }

    /**
     * 暂存操作
     *
     * @param saveRequest 请求参数
     * @return 暂存结果
     */
    public WebApiResp<SaveResult> draftResult(SaveRequest saveRequest) {
        String draft = draft(saveRequest.getFormId(), JSON.toJSONString(saveRequest));
        return convertApiResponse.parseSaveWebApiResponse(draft);
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
        return convertApiResponse.parseViewWebApiResponse(view);
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
        return convertApiResponse.parseOperatorWebApiResponse(submit);
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
        return convertApiResponse.parseOperatorWebApiResponse(audit);
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
        return convertApiResponse.parseOperatorWebApiResponse(unAudit);
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
        return convertApiResponse.parseOperatorWebApiResponse(cancelAssign);
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
        return convertApiResponse.parseConvertWebApiResponse(push);
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
        return convertApiResponse.parseOperatorWebApiResponse(cancel);
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
        return convertApiResponse.parseOperatorWebApiResponse(billClose);
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
        return convertApiResponse.parseOperatorWebApiResponse(billUnClose);
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
        return convertApiResponse.parseBatchSaveWebApiResponse(batchSave);
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
                        .filter(es -> !es.isEmpty())
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
        return convertApiResponse.parseAttachmentUploadWebApiResponse(respStr);
    }

    /**
     * 分块上传文件附件 针对大文件上传
     *
     * @param request     附件上传
     * @param inputStream 输入流
     * @param blockSize   分块的大小
     * @return 附件响应信息
     * @throws IOException io异常
     */
    public WebApiResp<AttachmentUploadResult> attachmentSplitUpload(AttachmentUpLoadRequest request,
                                                                    InputStream inputStream, int blockSize) throws IOException {
        if (blockSize <= 0) {
            throw new IllegalArgumentException("blockSize must be a value greater than 0");
        }
        if (Objects.isNull(inputStream)) {
            throw new IOException("inputStream can not be null!");
        }
        byte[] content = new byte[blockSize];
        WebApiResp<AttachmentUploadResult> webApiResp = null;
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, blockSize)) {
            // 处理流为空的情况
            bufferedInputStream.mark(1);
            if (bufferedInputStream.read() == -1) {
                throw new IOException("inputStream can not be empty!");
            }
            bufferedInputStream.reset();
            // 提前实例化Base64编码器
            Base64.Encoder encoder = Base64.getEncoder();
            //循环分块读取和上传
            while (true) {
                int size = bufferedInputStream.read(content);
                if (size == -1) {
                    // 读取完毕，退出循环
                    break;
                }
                //是否最后一串
                boolean isLast = (size != blockSize);
                byte[] uploadBytes = Arrays.copyOf(content, size);
                String fileBase64String = encoder.encodeToString(uploadBytes);
                // 设置上传请求数据
                if (Objects.nonNull(webApiResp)) {
                    request.setFileId(webApiResp.getResult().getFileId());
                }
                request.setIsLast(isLast);
                request.setSendByte(fileBase64String);
                // 调用接口上传
                webApiResp = attachmentUploadResult(request);
                if (!webApiResp.isSuccessfully()) {
                    throw new WebApiResponseValidationException("上传文件出现异常!", webApiResp);
                }
                if (isLast) {
                    // 如果是最后一块，则退出循环
                    break;
                }
            }
            return webApiResp;
        }
    }

    /**
     * 附件下载
     *
     * @param data 请求参数
     * @return 响应数据
     */
    public String attachmentDownLoad(String data) {
        try {
            return k3CloudApi.attachmentDownLoad(data);
        } catch (Exception e) {
            throw new WebApiInvokeException("附件下载出现异常!", e);
        }
    }

    /**
     * 附件下载
     *
     * @param request 请求参数
     * @return 响应数据
     */
    public String attachmentDownLoad(AttachmentDownLoadRequest request) {
        return attachmentDownLoad(JSON.toJSONString(request));
    }

    /**
     * 附件下载
     *
     * @param request 请求参数
     * @return 响应数据 {@link WebApiResp<AttachmentDownLoadRequest>}
     */
    public WebApiResp<AttachmentDownLoadResult> attachmentDownLoadResult(AttachmentDownLoadRequest request) {
        String resp = attachmentDownLoad(request);
        return convertApiResponse.parseAttachmentDownLoadWebApiResponse(resp);
    }

    /**
     * 文件分块下载 针对大文件下载
     *
     * @param fileId  附件id
     * @param dirPath 文件夹路径 存放文件的文件夹路径
     * @return 文件
     */
    public File attachmentSplitDownload(String fileId, String dirPath) throws IOException {
        if (StringUtils.isEmpty(dirPath)) {
            throw new IllegalArgumentException("文件夹路径必填!");
        }
        if (StringUtils.isEmpty(fileId)) {
            throw new IllegalArgumentException("附件id必填!");
        }
        // 提前判断路径是否存在并创建目录
        Path directories = Paths.get(dirPath);
        if (!Files.exists(directories)) {
            Files.createDirectories(directories);
            log.info("Directory created: {}", dirPath);
        }
        AttachmentDownLoadRequest request = new AttachmentDownLoadRequest(fileId, 0L);
        boolean isLast = false;
        Path filePath = null;
        Base64.Decoder decoder = Base64.getDecoder();
        while (!isLast) {
            WebApiResp<AttachmentDownLoadResult> webApiRespResult = attachmentDownLoadResult(request);
            if (!webApiRespResult.isSuccessfully()) {
                throw new WebApiResponseValidationException("附件下载失败!", webApiRespResult);
            }
            AttachmentDownLoadResult result = webApiRespResult.getResult();
            //是否最后一块
            isLast = result.getIsLast();
            if (!isLast) {
                //设置下次下载的位置
                Long startIndex = result.getStartIndex();
                request.setStartIndex(startIndex);
            }
            //设置文件
            String fileName = result.getFileName();
            filePath = directories.resolve(fileName);
            // 确保文件存在
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
            //获取base64的文件编码字符串
            String filePart = result.getFilePart();
            try (OutputStream out = Files.newOutputStream(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                 BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(out)) {
                byte[] bytes = decoder.decode(filePart);
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
            } catch (IOException e) {
                log.error("Error writing file chunk: fileName={}, error={}", fileName, e.getMessage(), e);
                throw e;
            }
            log.info("Downloaded file chunk: fileName={}, startIndex={}", fileName, request.getStartIndex());
        }
        return filePath.toFile();
    }

    /**
     * 万能API获取报表数据
     * 打开金蝶云星空客户端 搜索报表API工具 可以在线调试获取入参
     *
     * @param reqJson 请求参数 打开金蝶云星空客户端 搜索报表API工具 可以在线调试获取入参
     * @return 响应结果
     */
    public String getReportData(String reqJson) {
        return execute(WebApiService.GET_REPORT_DATA.getServiceName(), reqJson);
    }
}
