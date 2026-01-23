package com.rain.eclipse.coll;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Multimaps;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 该类提供了一系列针对 Eclipse Collections 库中核心数据结构的单元测试。
 * 测试涵盖了列表、集合、映射、包（Bag）以及多值映射（Multimap）的常用操作，
 * 包括但不限于过滤、转换、排序、集合运算、条件检索和元素计数。
 * 每个测试方法均独立验证特定功能点的正确性，确保集合操作符合预期行为。
 * 该类使用 JUnit 5 框架进行测试，并借助 Lombok 的 @Slf4j 注解记录日志。
 *
 * @author xueyu
 */
@Slf4j
public class EclipseCollectionsTest {

    /**
     * List: filter/select 常用操作。
     */
    @Test
    void testListOps() {
        // 创建一个可变列表
        MutableList<String> list = Lists.mutable.of("Apple", "Banana", "Orange");
        list.add("aaaaaaaaaaaaaa");
        // 选择长度大于 5 的元素
        MutableList<String> filtered = list.select(fruit -> fruit.length() > 5);
        assertTrue(filtered.contains("Banana"));
        assertTrue(filtered.contains("Orange"));
        assertTrue(filtered.contains("aaaaaaaaaaaaaa"));
        assertFalse(filtered.contains("Apple"));
    }

    /**
     * List: collect 转换并排序。
     */
    @Test
    void testListTransformAndSort() {
        MutableList<String> list = Lists.mutable.of("b", "a", "cc");
        MutableList<String> upper = list.collect(String::toUpperCase);
        MutableList<String> sorted = upper.sortThis(Comparator.naturalOrder());
        assertEquals(Lists.mutable.of("A", "B", "CC"), sorted);
    }

    /**
     * Set: union/intersect/difference 操作。
     */
    @Test
    void testSetOps() {
        MutableSet<Integer> left = Sets.mutable.of(1, 2, 3, 3);
        MutableSet<Integer> right = Sets.mutable.of(3, 4);
        assertEquals(Sets.mutable.of(1, 2, 3, 4), left.union(right));
        assertEquals(Sets.mutable.of(3), left.intersect(right));
        assertEquals(Sets.mutable.of(1, 2), left.difference(right));
    }

    /**
     * Map: getIfAbsentPut 与 select 过滤。
     */
    @Test
    void testMapOps() {
        MutableMap<String, Integer> map = Maps.mutable.of("a", 1, "b", 2);
        int value = map.getIfAbsentPut("c", () -> 3);
        assertEquals(3, value);
        assertEquals(3, map.size());

        MutableMap<String, Integer> filtered = map.select((k, v) -> v >= 2);
        assertEquals(Maps.mutable.of("b", 2, "c", 3), filtered);
    }


    /**
     * Bag: 计数与 size。
     */
    @Test
    void testBagOps() {
        MutableBag<String> bag = Bags.mutable.of("a", "a", "b");
        assertEquals(2, bag.occurrencesOf("a"));
        assertEquals(1, bag.occurrencesOf("b"));
        assertEquals(3, bag.size());
    }

    /**
     * Multimap: 多值映射的 put/get。
     */
    @Test
    void testMultimapOps() {
        MutableListMultimap<String, String> multimap = Multimaps.mutable.list.empty();
        multimap.put("k1", "v1");
        multimap.put("k1", "v2");
        multimap.put("k2", "v3");
        assertEquals(2, multimap.get("k1").size());
        assertEquals(1, multimap.get("k2").size());
    }
}
