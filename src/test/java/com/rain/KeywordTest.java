package com.rain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
public class KeywordTest {

    @Test
    void test() {
        var list = new ArrayList<String>();
        list.add("1");
        var day = "1";
        //switch表达式可以返回结果
        int result = switch (day) {
            case "1" -> 1;
            case "2" -> 2;
            default -> {
                //在 switch 表达式中返回一个值，使其成为 switch 表达式的结果。
                yield 1;
            }
        };
        Point point = new Point(1, 1);
        int x1 = point.x();
        Object a = "1";
        if (a instanceof String s) {
            // 在这里可以直接使用 String 类型的变量 s
            System.out.println(s.toUpperCase());
        }
    }

    /**
     * 纪录类进行测试
     *
     * @param x x
     * @param y y
     */
    public record Point(int x, int y) {
    }

    /**
     * 关键字  sealed    permits    non-sealed  组成的密封类进行测试
     */
    public sealed class Shape permits Circle, Square {
        // 只有 Circle 和 Square 可以继承 Shape
    }

    public non-sealed class Circle extends Shape {
        // Circle 可以进一步被继承
    }

    public class Circle1 extends Circle {
        // 进一步继承Circle
    }

    public final class Square extends Shape {
        // Square 不能被进一步继承
    }

}
