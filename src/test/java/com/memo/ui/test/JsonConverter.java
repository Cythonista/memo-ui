package com.memo.ui.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.memo.ui.dao.SystemErrorException;

public class JsonConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonConverter() {
    }

    /**
     * 文字列に変換
     * @param object オブジェクト
     * @return 文字列
     */
    public static String toString(final Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new SystemErrorException(exception.getMessage());
        }
    }
}
