package com.rain.eclipse.coll;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.junit.jupiter.api.Test;

@Slf4j
public class EclipseCollectionsTest {

    @Test
    void test() {
        // 创建一个可变列表
        MutableList<String> list = Lists.mutable.of("Apple", "Banana", "Orange");
        list.add("aaaaaaaaaaaaaa");
        // 选择长度大于 5 的元素
        MutableList<String> filtered = list.select(fruit -> fruit.length() > 5);
        System.out.println("Filtered List: " + filtered);
    }
}
