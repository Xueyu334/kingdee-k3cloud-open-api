package com.rain;

import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.utils.WebApiHttpHelper;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.domain.dto.request.save.ModelMap;
import com.kingdee.bos.webapi.domain.dto.request.save.SaveRequest;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.LoginResult;
import com.kingdee.bos.webapi.domain.dto.response.result.SaveResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * 云星空 Http 请求测试
 *
 * @author xueyu
 */
@Slf4j
@SpringBootTest
public class K3CloudHttpTest {

    @Autowired
    private WebApiProperties webApiProperties;

    @Test
    void testLoginBySign() {
        WebApiHttpHelper webApiHttpHelper = WebApiHttpHelper.of(webApiProperties);
        LoginResult loginResult = webApiHttpHelper.loginBySign();
        assertNotNull(loginResult);
        assertNotNull(loginResult.getKdsvcSessionId());
        log.info("登录成功，SessionId: {}", loginResult.getKdsvcSessionId());
    }

    @Test
    void testSave() {
        WebApiHttpHelper webApiHttpHelper = WebApiHttpHelper.of(webApiProperties);
        String formId = "BD_MATERIAL"; // 实际的表单ID
        // 构建 SaveRequest 数据
        SaveRequest saveRequest = new SaveRequest(formId);
        ModelMap<String, Object> modelMap = new ModelMap<>();
        modelMap.put("FNumber", "test001"); // 示例编码
        modelMap.put("FName", "测试物料001"); // 示例名称
        saveRequest.setModel(modelMap);
        // 执行保存操作
        WebApiResp<SaveResult> saveResult = webApiHttpHelper.save(formId, saveRequest);
        // 验证保存结果
        assertNotNull(saveResult, "保存结果不应为空");
        // 这里需要根据实际的业务响应来判断保存是否成功，例如检查 saveResult.getResult().getResponseStatus().isIsSuccess()
        // 为了演示，我们只断言结果不为 null
        log.info("保存结果: {}", JSON.toJSONString(saveResult));
        // 可以添加更详细的断言，例如：
        // assertTrue(saveResult.getResult().getResponseStatus().isIsSuccess(), "保存操作失败");
    }

    @Test
    void testExecuteBillQuery() {
        WebApiHttpHelper webApiHttpHelper = WebApiHttpHelper.of(webApiProperties);
        for (var i = 0; i < 10; i++) {
            String data = "{\"FormId\":\"BD_Currency\",\"FieldKeys\":\"FCODE\",\"OrderString\":\"\",\"FilterString\":\" FNUMBER='PRE001' \",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
            List<List<Object>> lists = webApiHttpHelper.executeBillQuery(data);
            log.info("单据查询结果: {}", JSON.toJSONString(lists));
        }
    }
}
