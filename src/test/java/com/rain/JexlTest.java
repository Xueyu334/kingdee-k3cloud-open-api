package com.rain;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.jexl3.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JexlTest {

    @Test
    void test() {
        JexlEngine jexl = new JexlBuilder().create();
        String expression = "bananas * bananaPrice + apples * applePrice";
        JexlExpression jexlExpression = jexl.createExpression(expression);

        Map<String, Object> values = new HashMap<>();
        values.put("bananas", 3);
        values.put("bananaPrice", 1.5);
        values.put("apples", 2);
        values.put("applePrice", 2.0);

        JexlContext jexlContext = new MapContext(values);
        jexlContext.set("", "");
        Object result = jexlExpression.evaluate(jexlContext);
        log.info("{}", result);
    }

    @Test
    void test1() {
        JexlEngine jexlEngine = new JexlBuilder().create();
        JexlContext jexlContext = new MapContext();
        jexlContext.set("name", "a");
        String expression = "name";
        JexlExpression jexlExpression = jexlEngine.createExpression(expression);
        Object evaluate = jexlExpression.evaluate(jexlContext);
        log.info("{}", evaluate);
    }

    @Test
    void test2() {
        // 创建JEXL引擎
        JexlBuilder jexlBuilder = new JexlBuilder();
        JexlEngine jexlEngine = jexlBuilder.create();

    }


}
