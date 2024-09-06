package com.kingdee.bos.webapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentUpLoadRequest implements Serializable {

    /**
     * 文件名
     * 例如 1016.txt
     */
    @JsonProperty(value = "FileName")
    private String fileName;

    /**
     * 表单id
     */
    @JsonProperty(value = "FormId")
    private String formId;

    /**
     * 是否最后一次上传
     */
    @JsonProperty(value = "IsLast")
    private Boolean isLast = true;

    /**
     * 单据内码
     */
    @JsonProperty(value = "InterId")
    private String interId;

    /**
     * 单据体标识
     * 上传单据体附件时候填写所述单据体的标识
     */
    @JsonProperty(value = "Entrykey")
    private String entryKey;

    /**
     * 分录内码
     * 如果是单据头附件 要么不填 要么填写-1
     */
    @JsonProperty(value = "EntryinterId")
    private String entryInterId;

    /**
     * 单据编码
     */
    @JsonProperty(value = "BillNo")
    private String billNo;

    /**
     * 附件别名
     * 例如 test
     */
    @JsonProperty(value = "AliasFileName")
    private String aliasFileName;

    /**
     * 文件id 如果分多次上传 首次之后必填
     */
    @JsonProperty(value = "FileId")
    private String fileId;

    /**
     * base64后的字节流
     */
    @JsonProperty(value = "SendByte")
    private String sendByte;

}
