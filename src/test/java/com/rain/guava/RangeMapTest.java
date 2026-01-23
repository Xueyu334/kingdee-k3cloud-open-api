package com.rain.guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * RangeMapTest 是一个用于测试 RangeMap 功能的测试类。
 * 该类包含多个测试方法，用于验证 RangeMap 在不同场景下的行为，包括范围映射的添加、重叠处理、删除、子范围视图以及范围替换等操作。
 * 测试使用 TreeRangeMap 实现，确保范围映射的排序和查询功能正常工作。
 * 所有测试方法均使用 JUnit 框架进行断言验证，确保代码的可靠性和正确性。
 * 测试覆盖了 RangeMap 的核心功能，如范围重叠时的分割与覆盖、删除范围后产生的间隙、子范围视图的创建以及相同范围的替换等。
 * 这些测试有助于确保 RangeMap 在实际使用中的稳定性和预期行为。
 */
@Slf4j
public class RangeMapTest {


    /**
     * 测试范围映射在重叠范围情况下的行为。
     * 本方法验证当向 RangeMap 添加两个存在重叠部分的范围时，后添加的范围会覆盖重叠区域。
     * 具体操作为：首先添加范围 [1, 200] 并映射到值 "1-2"，然后添加范围 [198, 1222] 并映射到值 "2-3"。
     * 最后查询键 199 对应的值，预期结果为 "2-3"，因为该键位于重叠区域，被后添加的范围覆盖。
     * 测试通过日志记录查询结果，用于验证 RangeMap 在范围重叠时的正确覆盖行为。
     */
    @Test
    void test1() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.range(1, BoundType.CLOSED, 200, BoundType.CLOSED), "1-2");
        rangeMap.put(Range.range(198, BoundType.CLOSED, 1222, BoundType.CLOSED), "2-3");
        String s = rangeMap.get(199);
        log.info("{}", s);
    }

    /**
     * 测试当向范围映射中添加重叠范围时的行为。
     * 本方法验证了在已有范围映射上添加一个与现有范围重叠的新范围时，
     * 范围映射会正确地将重叠部分分割，并用新值覆盖该部分。
     * 具体操作是：首先添加范围 [1, 10] 并映射到值 "A"，
     * 然后添加范围 [5, 15] 并映射到值 "B"。
     * 预期结果是：键 3 位于第一个范围的非重叠部分，因此返回 "A"；
     * 键 6 位于两个范围的重叠部分，由于后添加的范围覆盖了该部分，因此返回 "B"；
     * 键 12 位于第二个范围的非重叠部分，因此返回 "B"；
     * 键 16 不在任何范围内，因此返回 null。
     * 该测试确保了范围映射在重叠情况下能够正确处理分割和覆盖逻辑。
     */
    @Test
    void overlapShouldSplitAndOverride() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "A");
        rangeMap.put(Range.closed(5, 15), "B");
        Assertions.assertEquals("A", rangeMap.get(3));
        Assertions.assertEquals("B", rangeMap.get(6));
        Assertions.assertEquals("B", rangeMap.get(12));
        Assertions.assertNull(rangeMap.get(16));
    }

    /**
     * 测试从范围映射中移除一个范围时，是否会在原范围内创建一个间隙。
     * 该方法验证当从一个已存在的范围映射中移除一个子范围后，剩余部分是否被正确分割为两个独立的范围。
     * 具体操作为：首先向范围映射中添加一个闭区间 [1, 10] 并映射到值 "A"，
     * 然后移除该区间内的子闭区间 [3, 7]。
     * 预期结果是移除操作后，原范围被分割为两个部分：[1, 2] 和 [8, 10]，
     * 这两个部分仍映射到值 "A"，而被移除的子区间 [3, 7] 不再映射到任何值。
     * 通过断言验证键 2 和键 8 对应的值为 "A"，而键 4 对应的值为 null，
     * 以确保间隙被正确创建且剩余范围保持原有的映射关系。
     */
    @Test
    void removeShouldCreateGap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "A");
        rangeMap.remove(Range.closed(3, 7));
        Assertions.assertEquals("A", rangeMap.get(2));
        Assertions.assertNull(rangeMap.get(4));
        Assertions.assertEquals("A", rangeMap.get(8));
    }

    /**
     * 验证子范围映射是否为原始范围映射的视图。
     * 该方法测试通过 {@code subRangeMap} 方法获取的子范围映射是否与原始范围映射保持动态关联。
     * 具体操作为：首先创建一个范围映射，并向其中添加两个不重叠的范围及其对应的值。
     * 然后，基于指定的子范围获取一个子范围映射视图。
     * 预期结果是该视图能够正确反映原始范围映射在子范围内的状态，包括值的查询。
     * 测试通过断言验证视图在子范围内返回正确的映射值，以及在子范围外返回 null。
     * 这确保了子范围映射是原始映射的实时视图，而非独立副本，任何对原始映射的修改都会在视图中体现。
     */
    @Test
    void subRangeMapShouldBeView() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 10), "A");
        rangeMap.put(Range.closed(11, 20), "B");
        RangeMap<Integer, String> view = rangeMap.subRangeMap(Range.closed(5, 15));
        Assertions.assertEquals("A", view.get(6));
        Assertions.assertEquals("B", view.get(12));
        Assertions.assertNull(view.get(16));
    }

    /**
     * 测试当向范围映射中添加完全相同的范围时，新值会替换旧值。
     * 该方法验证了如果使用相同的范围键重复调用 {@code put} 方法，后一次调用将覆盖前一次映射的值。
     * 具体操作为：首先添加闭区间 [1, 5] 并映射到值 "A"，然后再次添加相同的闭区间 [1, 5] 但映射到值 "B"。
     * 预期结果是查询该区间内的任意键（例如 3）将返回新值 "B"，表明旧映射已被完全替换。
     * 此测试确保范围映射在遇到相同范围时，能够正确执行替换操作，而不会保留旧值或产生意外的分割行为。
     */
    @Test
    void putSameRangeShouldReplace() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.closed(1, 5), "A");
        rangeMap.put(Range.closed(1, 5), "B");
        Assertions.assertEquals("B", rangeMap.get(3));
    }
}
