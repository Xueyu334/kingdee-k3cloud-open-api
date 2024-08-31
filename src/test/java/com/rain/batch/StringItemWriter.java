package com.rain.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class StringItemWriter implements ItemWriter<String> {

    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        List<? extends String> items = chunk.getItems();
        items.forEach(System.out::println);
    }
}
