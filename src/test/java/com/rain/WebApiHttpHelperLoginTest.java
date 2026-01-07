package com.rain;

import com.alibaba.fastjson2.JSON;
import com.kingdee.bos.webapi.common.utils.WebApiHttpHelper;
import com.kingdee.bos.webapi.config.properties.WebApiProperties;
import com.kingdee.bos.webapi.domain.dto.response.result.LoginResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * WebApiHttpHelper 登录功能测试类
 *
 * @author xueyu
 */
@Slf4j
public class WebApiHttpHelperLoginTest {

    @Test
    public void testLoginBySign() {
        // 创建配置
        WebApiProperties properties = new WebApiProperties();
        properties.setServerUrl("http://your-k3cloud-server/k3cloud/");
        properties.setAcctId("your-acct-id");
        properties.setUserName("your-user-name");
        properties.setAppId("your-app-id");
        properties.setAppSec("your-app-sec");
        properties.setLcId(2052);

        // 创建 WebApiHttpHelper 实例
        WebApiHttpHelper helper = WebApiHttpHelper.of(properties);

        // 执行登录
        LoginResult result = helper.loginBySign();

        // 输出结果
        log.info("========== 登录结果 ==========");
        log.info("LoginResultType: {}", result.getLoginResultType());
        log.info("KDSVCSessionId: {}", result.getKdsvcSessionId());
        log.info("IsSuccessByAPI: {}", result.getIsSuccessByAPI());
        log.info("Message: {}", result.getMessage());
        log.info("完整结果: {}", JSON.toJSONString(result));
        log.info("==============================");

        // 验证登录成功
        assert result.isLoginSuccess() : "登录失败! LoginResultType=" + result.getLoginResultType();

        log.info("✅ 登录测试通过！SessionId: {}", result.getKdsvcSessionId());
    }
}
