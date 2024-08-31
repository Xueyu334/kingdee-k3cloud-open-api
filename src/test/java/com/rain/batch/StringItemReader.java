package com.rain.batch;

import lombok.Setter;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.batch.item.ItemReader;

import java.util.Optional;

@Setter
public class StringItemReader implements ItemReader<String> {

    private static final int MAX_COUNT = 5;
    private int count = 0;
    private String parameter;

    @Override
    public String read() {
        if (count < MAX_COUNT) {
            count++;
            return Optional
                    .ofNullable(parameter).filter(StringUtils::isNotBlank)
                    .orElse("a ") + count;
        } else {
            // 返回 null 表示读取完毕
            return null;
        }
    }
}
