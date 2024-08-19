package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kingdee.bos.webapi.domain.dto.response.status.ConvertResponseStatus;
import com.kingdee.bos.webapi.domain.dto.response.status.ResponseStatus;
import com.kingdee.bos.webapi.domain.dto.response.status.SuccessEntitys;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 下推结果输出
 *
 * @author xueyu
 */
@Getter
@Setter
public class ConvertResult extends Result {

    /**
     * 下推转换结果
     */
    @JsonProperty(value = "ConvertResponseStatus")
    private ConvertResponseStatus convertResponseStatus;

    /**
     * 保存结果
     */
    @JsonProperty(value = "ResponseStatus")
    private ResponseStatus responseStatus;

    public ConvertResult() {
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("responseStatus", responseStatus)
                .append("responseStatus", responseStatus)
                .append("convertResponseStatus", convertResponseStatus)
                .toString();
    }

    /**
     * 获取下推成功之后的单据id集合
     *
     * @return 单据id集合
     */
    public List<String> getBillIdList() {
        List<SuccessEntitys> successEntitys = responseStatus.getSuccessEntitys();
        return successEntitys.stream()
                .map(SuccessEntitys::getId)
                .distinct()
                .collect(Collectors.toList());
    }

}
