package com.rain.batch;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class StringItemProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(@Nonnull String item) throws Exception {
        return "processed item: " + item;
    }
}
