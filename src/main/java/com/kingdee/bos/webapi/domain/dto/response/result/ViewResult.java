package com.kingdee.bos.webapi.domain.dto.response.result;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 查看的结果 返回的完整的单据的数据包
 *
 * @author xueyu
 */
@Getter
@Setter
public class ViewResult extends Result {

    /**
     * 单据数据包,单据完整数据内容
     */
    @JsonProperty(value = "Result")
    private JSONObject result;

    public ViewResult() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("result", result)
                .append("responseStatus", responseStatus)
                .toString();
    }
}
