package com.dream.home.common;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Java测试类
 *
 * @author hhz
 * @date 2020-07-08 15:29:00
 */
@Slf4j
public class JavaTest {

    @Test
    public void testSum() {
        AtomicInteger sum = new AtomicInteger();
        for (int i = 0; i < 100; i++) {
            new Thread(sum::incrementAndGet).start();
        }
        log.info("sum:{}", sum);
    }

}
