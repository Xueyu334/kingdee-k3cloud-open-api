package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

/**
 * 批量保存的结果
 *
 * @author xueyu
 */
@Getter
@Setter
public class BatchSaveResult extends Result {

    /**
     * 返回的数据
     */
    @JsonProperty(value = "NeedReturnData")
    protected List<Map<String, Object>> needReturnData;

    public BatchSaveResult() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("needReturnData", needReturnData)
                .append("responseStatus", responseStatus)
                .toString();
    }
}
