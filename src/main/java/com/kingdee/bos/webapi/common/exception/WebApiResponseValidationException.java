package com.kingdee.bos.webapi.common.exception;

import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.Result;
import com.kingdee.bos.webapi.domain.dto.response.status.Errors;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * WebAPi操作响应校验异常操作
 *
 * @author xueyu
 * @see WebApiResp
 * @see Result
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
                        .collect(Collectors.joining(",", "[", "]")))
                .orElse("");
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("webApiResp", webApiResp)
                .append("code", code)
                .toString();
    }
}
