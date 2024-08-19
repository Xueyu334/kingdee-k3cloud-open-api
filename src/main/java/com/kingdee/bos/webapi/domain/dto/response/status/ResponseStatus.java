package com.kingdee.bos.webapi.domain.dto.response.status;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 返回结果信息
 *
 * @author xueyu
 */
@Getter
@Setter
public class ResponseStatus {

    /**
     * 错误代码，不成功则为500
     */
    @JsonProperty(value = "ErrorCode")
    private String errorCode;

    /**
     * 错误列表
     */
    @JsonProperty(value = "Errors")
    private List<Errors> errors;

    /**
     * 操作是否成功
     */
    @JsonProperty(value = "IsSuccess")
    private Boolean isSuccess;

    /**
     * 错误代码：
     * <p>
     * <ol>
     * <li>0：默认</li><li>1：上下文丢失</li><li>2：没有权限</li><li>3：操作标识为空</li><li>4：异常</li><li>5：单据标识为空</li>
     * <li>6：数据库操作失败</li><li>7：许可错误</li><li>8：参数错误</li><li>9：指定字段/值不存在</li><li>10：未找到对应数据</li>
     * <li>11：验证失败</li><li>12：不可操作</li><li>13：网控冲突</li><li>14：调用限制</li><li>15：禁止管理员登录</li>
     * </ol>
     * </p>
     */
    @JsonProperty(value = "MsgCode")
    private Integer msgCode;

    /**
     * 成功列表
     */
    @JsonProperty(value = "SuccessEntitys")
    private List<SuccessEntitys> successEntitys;

    /**
     * 提示信息
     */
    @JsonProperty(value = "SuccessMessages")
    private List<SuccessMessages> successMessages;

    public ResponseStatus() {
    }

    public ResponseStatus(String errorCode, List<Errors> errors, Boolean isSuccess, Integer msgCode, List<SuccessEntitys> successEntitys, List<SuccessMessages> successMessages) {
        this.errorCode = errorCode;
        this.errors = errors;
        this.isSuccess = isSuccess;
        this.msgCode = msgCode;
        this.successEntitys = successEntitys;
        this.successMessages = successMessages;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("errorCode", errorCode)
                .append("errors", errors)
                .append("isSuccess", isSuccess)
                .append("msgCode", msgCode)
                .append("successEntitys", successEntitys)
                .append("successMessages", successMessages)
                .toString();
    }

}
