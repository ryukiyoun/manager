package com.temple.manager.converter;

import com.temple.manager.util.AesUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AesConverterTest {
    @Mock
    AesUtil aesUtil;

    @InjectMocks
    AesConverter aesConverter;

    @Test
    @DisplayName("데이터 암호화 Converter 테스트")
    void convertToDatabaseColumn() throws Exception{
        //given
        String encodeString = "test_encode_string";
        given(aesUtil.encrypt(anyString())).willReturn(encodeString);

        //when
        String result = aesConverter.convertToDatabaseColumn("attribute_string");

        //then
        assertThat(result, is(encodeString));
    }

    @Test
    @DisplayName("데이터 암호화 Converter Exception 테스트")
    void convertToDatabaseColumnError() throws Exception{
        //given
        String encodeString = "test_encode_string";
        given(aesUtil.encrypt(anyString())).willThrow(new Exception());

        //when, then
        assertThrows(RuntimeException.class, () -> aesConverter.convertToDatabaseColumn(encodeString));
    }

    @Test
    @DisplayName("데이터 복호화 Converter 테스트")
    void convertToEntityAttribute() throws Exception {
        //given
        String decodeString = "test_decode_string";
        given(aesUtil.decrypt(anyString())).willReturn(decodeString);

        //when
        String result = aesConverter.convertToEntityAttribute("db_data_string");

        //then
        assertThat(result, is(decodeString));
    }

    @Test
    @DisplayName("데이터 복호화 Converter Exception 테스트")
    void convertToEntityAttributeError() throws Exception {
        //given
        String decodeString = "test_decode_string";
        given(aesUtil.decrypt(anyString())).willThrow(new Exception());

        //when, then
        assertThrows(RuntimeException.class, () -> aesConverter.convertToEntityAttribute(decodeString));
    }
}