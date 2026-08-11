package com.kingdee.bos.webapi.common.convert.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingdee.bos.webapi.domain.dto.response.WebApiResp;
import com.kingdee.bos.webapi.domain.dto.response.result.SaveResult;
import com.kingdee.bos.webapi.domain.dto.response.result.ViewResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JacksonConvertApiResponseTest {

    @Test
    void shouldParseSaveResponseWithGenericResultType() {
        String response = """
                {"Result":{"Id":"1001","Number":"BILL-001","ResponseStatus":{"ErrorCode":"0","IsSuccess":true}}}
                """;

        WebApiResp<SaveResult> result = JacksonConvertApiResponse.INSTANCE.parseSaveWebApiResponse(response);

        assertEquals("1001", result.getResult().getId());
        assertEquals("BILL-001", result.getResult().getNumber());
        assertTrue(result.getResult().getResponseStatus().getIsSuccess());
    }

    @Test
    void shouldParseNestedListResponse() {
        List<List<Object>> result = JacksonConvertApiResponse.INSTANCE
                .parseListListObjectApiResponse("[[1,\"first\"],[2,\"second\"]]");

        assertEquals(2, result.size());
        assertEquals("first", result.get(0).get(1));
    }

    @Test
    void shouldParseViewResponseWithFastJsonObject() {
        String response = """
                {"Result":{"Result":{"FID":1001}}}
                """;

        WebApiResp<ViewResult> result = JacksonConvertApiResponse.INSTANCE.parseViewWebApiResponse(response);

        assertEquals(1001, result.getResult().getResult().getIntValue("FID"));
    }

    @Test
    void shouldUseProvidedObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonConvertApiResponse converter = new JacksonConvertApiResponse(objectMapper);

        WebApiResp<SaveResult> result = converter.parseSaveWebApiResponse("""
                {"Result":{"Id":"1001","UnknownField":"ignored"}}
                """);

        assertEquals("1001", result.getResult().getId());
    }
}
