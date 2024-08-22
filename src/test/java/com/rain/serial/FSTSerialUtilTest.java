package com.rain.serial;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
class FSTSerialUtilTest {

    /**
     * --add-opens=java.base/java.time=ALL-UNNAMED
     * --add-opens=java.base/java.lang=ALL-UNNAMED
     * --add-opens=java.base/java.math=ALL-UNNAMED
     * --add-opens=java.base/java.util=ALL-UNNAMED
     * --add-opens=java.base/java.util.concurrent=ALL-UNNAMED
     * --add-opens=java.base/java.net=ALL-UNNAMED
     * --add-opens=java.base/java.text=ALL-UNNAMED
     * --add-opens=java.sql/java.sql=ALL-UNNAMED
     */
    @Test
    void serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("1", "1");
        map.put("2", 2);
        byte[] serialize = FSTSerialUtil.serialize(map);
        Map<String, Object> deserialize = FSTSerialUtil.deserialize(serialize);
        log.info(deserialize.toString());
    }


}
