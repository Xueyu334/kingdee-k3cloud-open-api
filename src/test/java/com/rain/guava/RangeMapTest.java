package com.rain.guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RangeMapTest {


    @Test
    void test1() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.range(1, BoundType.CLOSED, 200, BoundType.CLOSED), "1-2");
        rangeMap.put(Range.range(198, BoundType.CLOSED, 1222, BoundType.CLOSED), "2-3");
        String s = rangeMap.get(199);
        log.info("{}", s);
    }
}
