package com.study.studyproject.global.config.redis;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilsTest {

    final String KEY = "key";
    final String VALUE = "value";
    final Duration DURATION = Duration.ofMillis(5000);

    @Autowired
    private RedisUtils redisUtils;

    @BeforeEach
    void shutDown() {
        redisUtils.setValues(KEY, VALUE, DURATION);
    }

    @AfterEach
    void tearDown() {
        redisUtils.deleteValues(KEY);
    }


    @Test
    @DisplayName("Redis에 데이터를 저장하면 조회된다")
    void saveAndFindTest() throws Exception {
        //given
        String findValue = redisUtils.getValues(KEY);

        //then
        assertThat(VALUE).isEqualTo(findValue);


    }

    @Test
    @DisplayName("Redis에 저장된 데이터를 수정한다.")
    void updateTest() throws Exception {
        //given
        String updateValue = "updateValue";
        redisUtils.setValues(KEY, updateValue, DURATION);

        //when
        String findValue = redisUtils.getValues(KEY);

        //then
        assertThat(updateValue).isEqualTo(findValue);
        assertThat(VALUE).isNotEqualTo(findValue);
    }




}