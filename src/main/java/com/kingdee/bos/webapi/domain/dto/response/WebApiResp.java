package com.kingdee.bos.webapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.response.result.Result;
import com.kingdee.bos.webapi.domain.dto.response.status.Errors;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import com.kingdee.bos.webapi.entity.RepoRet;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 金蝶云星空 webapi 响应数据结构
 *
 * @author xueyu
 * @see RepoRet
 */
@Getter
@Setter
public class WebApiResp<R extends Result> implements Serializable {

    @Serial
    private static final long serialVersionUID = 7767426709032030952L;

    /**
     * 结果  JSON响应参数描述
     */
    @JsonProperty(value = "Result")
    private R result;


    public WebApiResp() {
    }

    public WebApiResp(R result) {
        this.result = result;
    }

    /**
     * 判断操作是否成功
     *
     * @return true 成功 false 失败
     */
    public boolean isSuccessfully() {
        if (Objects.isNull(result)) {
            return false;
        }
        return this.result.getResponseStatus() != null && this.result.getResponseStatus().getIsSuccess();
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息 ["",""...]
     */
    public String getErrorMessage() {
        return Optional.ofNullable(result)
                .map(Result::getResponseStatus)
                .map(ResponseStatus::getErrors)
                .map(r -> r.stream().map(Errors::getMessage)
                        .collect(Collectors.joining(",", "[", "]")))
                .orElse("");
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .toString();
    }
}
