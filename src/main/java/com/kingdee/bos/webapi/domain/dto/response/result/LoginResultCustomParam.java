package com.kingdee.bos.webapi.domain.dto.response.result;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录结果的自定义参数
 */
@Getter
@Setter
@ToString
public class LoginResultCustomParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字段禁用时是否显示边框
     */
    @JsonProperty("FFieldDisabledShowBorder")
    private Boolean FFieldDisabledShowBorder;

    /**
     * 图片文件是否压缩
     */
    @JsonProperty("FImgFileCompress")
    private Boolean FImgFileCompress;

    /**
     * 系统提示规则
     */
    @JsonProperty("FSystemTipsRule")
    private String FSystemTipsRule;

    /**
     * 全局水印配置字符串
     */
    @JsonProperty("GlobalWatermarkConfigStr")
    private String GlobalWatermarkConfigStr;

    /**
     * 是否禁用单元格区域
     */
    @JsonProperty("FIsDisabledCellSection")
    private Boolean FIsDisabledCellSection;

    /**
     * 是否不允许表格列自动隐藏
     */
    @JsonProperty("FUnAllowTableColumnAutoHidden")
    private Boolean FUnAllowTableColumnAutoHidden;

    /**
     * 是否检查启用序列请求
     */
    @JsonProperty("FChkEnabledSeqReq")
    private Boolean FChkEnabledSeqReq;

    /**
     * 列表快速过滤是否返回旧数据
     */
    @JsonProperty("FListQuickFilterBackOld")
    private Boolean FListQuickFilterBackOld;

    /**
     * 是否检查GUI旧主控制台
     */
    @JsonProperty("FChkGUIOldMainConsle")
    private Boolean FChkGUIOldMainConsle;

    /**
     * 是否检查工具栏字体大模式
     */
    @JsonProperty("FChkToolBarFontLargeMode")
    private Boolean FChkToolBarFontLargeMode;

    /**
     * 是否禁用网格行复制
     */
    @JsonProperty("FIsDisabledGridRowCopy")
    private Boolean FIsDisabledGridRowCopy;

    /**
     * 存储其他未明确定义的属性
     */
    private Map<String, Object> extraProperties = new HashMap<>();

    /**
     * 设置额外属性
     * @param name 属性名称
     * @param value 属性值
     */
    @JsonAnySetter
    public void set(String name, Object value) {
        extraProperties.put(name, value);
    }

    /**
     * 获取所有额外属性
     * @return 额外属性的Map
     */
    @JsonAnyGetter
    public Map<String, Object> getExtraProperties() {
        return extraProperties;
    }
}
