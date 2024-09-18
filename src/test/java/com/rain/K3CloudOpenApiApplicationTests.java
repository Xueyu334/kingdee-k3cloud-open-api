package com.rain;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@SpringBootTest
class K3CloudOpenApiApplicationTests {

    @Test
    void contextLoads() {
    }

    public void saveFiles() throws Exception {
        String fileid = "c7be8e41c0d64ee1b1f56f32f1ec6e8c";
        K3CloudApi api = new K3CloudApi();
        String json = " {\"FileId\": " + "\"" + fileid + "\"" + ", \"StartIndex\": 0}";
        String result = api.attachmentDownLoad(json);
        JsonObject resultObject = JsonParser.parseString(result).getAsJsonObject();
        JsonObject data = resultObject.get("Result").getAsJsonObject();
        String fileName = data.get("FileName").getAsString();
        boolean isLast = data.get("IsLast").getAsBoolean();
        if (isLast) {
            System.out.println("小文件");
            String filePart = data.get("FilePart").getAsString();
            File file = new File("D:\\" + fileName);
            try {
                byte[] b = Base64.getDecoder().decode(filePart);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {//调整异常数据
                        b[i] += (byte) 256;
                    }
                }
                //生成文件
                OutputStream out = new FileOutputStream(file.getPath());
                out.write(b);
                out.flush();
                out.close();
            } catch (Exception e) {
                System.out.println("文件保存异常:" + e.getMessage());
            }
        } else {
            System.out.println("大文件");
            List<String> list = new ArrayList<>();
            String filePart = data.get("FilePart").getAsString();
            list.add(filePart);
            while (!data.get("IsLast").getAsBoolean()) {
                int startIndex = data.get("StartIndex").getAsInt();
                String request = "{\"FileId\": " + "\"" + fileid + "\"" + ", \"StartIndex\": " + startIndex + "}";
                result = api.attachmentDownLoad(request);
                resultObject = new JsonParser().parse(result).getAsJsonObject();
                data = resultObject.get("Result").getAsJsonObject();
                filePart = data.get("FilePart").getAsString();
                list.add(filePart);
            }
            File file = new File("D:\\" + fileName);
            try {
                for (String s : list) {
                    byte[] b = Base64.getDecoder().decode(s);
                    for (int i = 0; i < b.length; ++i) {
                        if (b[i] < 0) {
                            //调整异常数据
                            b[i] += (byte) 256;
                        }
                    }
                    //生成文件
                    OutputStream out = new FileOutputStream(file.getPath(), true);
                    out.write(b);
                    out.flush();
                    out.close();
                }
            } catch (Exception e) {
                System.out.println("文件保存异常:" + e.getMessage());
            }
        }
    }

}
