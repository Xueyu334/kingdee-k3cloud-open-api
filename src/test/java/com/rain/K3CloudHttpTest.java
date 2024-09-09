package com.rain;

import com.alibaba.fastjson2.JSONObject;
import com.kingdee.bos.webapi.utils.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.CookieStore;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class K3CloudHttpTest {

    private static String bytesToHex(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte hashInByte : hashInBytes) {
            String hex = Integer.toHexString(hashInByte & 255);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String hashMAC(String data, String secret) {
        try {
            Mac kdmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            kdmac.init(secret_key);
            byte[] rawHmac = kdmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64Utils.encodingToBase64(bytesToHex(rawHmac).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    @Test
    void test() throws IOException, URISyntaxException, ParseException {

        CookieStore cookieStore = new BasicCookieStore();

        String formId = "BD_MATERIAL";
        JSONObject params = new JSONObject();
        params.put("CreateOrgId", 0);
        params.put("Number", "1001");
        params.put("IsSortBySeq", false);
        String urlStr = "http://XXX.X.XXX.XXX:XXXX/k3cloud/Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View.common.kdsvc";
        String appId = "1231234_XXXXXXXXXXXXXXXXXXX";
        String appSec = "XXXXXXXXXXXXXXXXXXX";
        String dcId = "XXXXXXXXXXXX";
        String userName = "XX";
        String lcId = "2052";
        String orgNum = "100";
        try (CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build()) {
            URI url = new URI(urlStr);
            String path = url.getPath();
            HttpPost httpPost = new HttpPost(url);
            String[] appArr = appId.split("_");
            String clientId = appArr[0];
            String apigwSec = appArr[1];
            httpPost.setHeader("X-Api-ClientID", clientId);
            httpPost.setHeader("X-Api-Auth-Version", "2.0");
            long currentTimeMillis = System.currentTimeMillis();
            String timestampMillis = String.valueOf(currentTimeMillis);
            httpPost.setHeader("x-api-timestamp", timestampMillis);
            httpPost.setHeader("x-api-nonce", timestampMillis);
            httpPost.setHeader("x-api-signheaders", "X-Api-TimeStamp,X-Api-Nonce");
            String urlPath = URLEncoder.encode(path, StandardCharsets.UTF_8);
            String context = String.format("POST\n%s\n\nx-api-nonce:%s\nx-api-timestamp:%s\n", urlPath, timestampMillis, timestampMillis);
            // httpPost.setHeader("X-Api-Signature", hashMAC(context, apigwSec));
            httpPost.setHeader("X-Kd-Appkey", appId);
            String data = String.format("%s,%s,%s,%s", dcId, userName, lcId, orgNum);
            httpPost.setHeader("X-Kd-Appdata", Base64Utils.encodingToBase64(data.getBytes(StandardCharsets.UTF_8)));
            httpPost.setHeader("X-Kd-Signature", hashMAC(appId + data, appSec));
            httpPost.setHeader("User-Agent", "Kingdee/Java WebApi SDK 8.0.6 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            Object[] objects = new Object[]{formId, params.toJSONString()};
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("parameters", objects);
            String jsonString = jsonObject.toJSONString();
            httpPost.setEntity(new StringEntity(jsonString, ContentType.APPLICATION_JSON, StandardCharsets.UTF_8.name(), false));
            try (ClassicHttpResponse classicHttpResponse = closeableHttpClient.executeOpen(null, httpPost, null);) {
                HttpEntity entity = classicHttpResponse.getEntity();
                String string = EntityUtils.toString(entity, StandardCharsets.UTF_8);
                log.info("{}", string);
                Header[] headers = classicHttpResponse.getHeaders();
                log.info("{}", headers);
            }
            params.put("Number", "");
            params.put("Id", "1001");
            Object[] objects1 = new Object[]{formId, params.toJSONString()};
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("parameters", objects1);
            httpPost.setEntity(new StringEntity(jsonObject1.toJSONString(), ContentType.APPLICATION_JSON, StandardCharsets.UTF_8.name(), false));
            try (ClassicHttpResponse execute = closeableHttpClient.executeOpen(null, httpPost, null);) {
                String string = EntityUtils.toString(execute.getEntity(), StandardCharsets.UTF_8);
                log.info("{}", string);
            }
        }
    }


}
