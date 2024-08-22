package com.rain.serial;

import lombok.extern.slf4j.Slf4j;
import org.nustaq.serialization.FSTConfiguration;

import java.io.ByteArrayInputStream;

@Slf4j
public class FSTSerialUtil {


    // 使用 JSON 配置，避免反射访问
    private static final FSTConfiguration fstConfiguration = FSTConfiguration.createJsonNoRefConfiguration();

    public static byte[] serialize(Object obj) {
        return fstConfiguration.asByteArray(obj);
    }

    public static <T> T deserialize(byte[] data) {
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Byte array to be deserialized cannot be null or empty.");
        }
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data)) {
            @SuppressWarnings(value = "unchecked") //无需检查是否类型匹配
            T t = (T) fstConfiguration.getObjectInput(byteArrayInputStream).readObject();
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Deserialization failed.", e);
        }
    }


}
