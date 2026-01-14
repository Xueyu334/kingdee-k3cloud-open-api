package com.kingdee.bos.webapi.common.exception;

import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.Result;
import com.kingdee.bos.webapi.domain.dto.response.status.Errors;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebApiResponseValidationException 表示在验证 Web API 响应时发生的异常。
 * 该异常继承自 WebApiInvokeException，专门用于处理 API 响应内容不符合预期或验证失败的情况。
 * 异常封装了原始的 Web API 响应对象（WebApiResp），便于在异常处理中访问响应详情，例如错误码、错误消息等。
 * 通过提供多个构造方法，支持基于消息、原因、错误码以及响应对象的不同组合来创建异常实例。
 * 此类还提供了便捷方法，用于从响应中提取格式化的错误消息，辅助调试和错误报告。
 * 适用于需要严格验证 API 响应并统一处理验证失败逻辑的场景。
 *
 * @author xueyu
 */
@Getter
public class WebApiResponseValidationException extends WebApiInvokeException {

    @Serial
    private static final long serialVersionUID = -1993676425693244384L;

    /**
     * 响应结果
     */
    private final WebApiResp<? extends Result> webApiResp;

    public WebApiResponseValidationException(String message, Throwable cause, WebApiResp<? extends Result> webApiResp) {
        super(message, cause);
        this.webApiResp = webApiResp;
    }

    public WebApiResponseValidationException(int code, String message, WebApiResp<? extends Result> webApiResp) {
        super(code, message);
        this.webApiResp = webApiResp;
    }

    public WebApiResponseValidationException(int code, String message, Throwable cause, WebApiResp<? extends Result> webApiResp) {
        super(code, message, cause);
        this.webApiResp = webApiResp;
    }

    public WebApiResponseValidationException(String message, WebApiResp<? extends Result> webApiResp) {
        super(message);
        this.webApiResp = webApiResp;
    }


    /**
     * 获取操作响应中的错误消息
     * <pre>
     * {@code    {
     *     "Result": {
     *         "ResponseStatus": {
     *             "ErrorCode": 500,
     *             "IsSuccess": false,
     *             "Errors": [
     *                 {
     *                     "FieldName": null,
     *                     "Message": "编码值[\"FYBX20240718000001\"]不存在",
     *                     "DIndex": 0
     *                 }
     *             ],
     *             "SuccessEntitys": [
     *
     *             ],
     *             "SuccessMessages": [
     *
     *             ],
     *             "MsgCode": 9
     *         }
     *     }
     * } }
     * </pre>
     *
     * @return 操作响应中的错误消息 ["错误1",“错误2”]
     */
    public String getErrorMessage() {
        return Optional.ofNullable(webApiResp)
                .map(WebApiResp::getResult)
                .map(Result::getResponseStatus)
                .map(ResponseStatus::getErrors)
                .map(errors -> errors.stream()
                        .filter(Objects::nonNull)
                        .map(Errors::getMessage)
                        .filter(StringUtils::isNotBlank)
                        .collect(Collectors.joining(",", "[", "]")))
                .orElse("");
    }


    /**
     * 返回此异常的字符串表示形式。
     * 该表示形式包含异常的关键信息，包括关联的 Web API 响应对象（webApiResp）以及异常的错误码（code）。
     * 主要用于调试和日志记录，以提供异常状态的清晰描述。
     *
     * @return 表示此异常的字符串，包含 webApiResp 和 code 信息
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("webApiResp", webApiResp)
                .append("code", code)
                .toString();
    }
}
