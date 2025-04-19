package org.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RedisMapTest {

    private RedisMap redisMap;

    @BeforeEach
    void setUp() {
        redisMap = new RedisMap();
        redisMap.clear();
    }

    @AfterEach
    void tearDown() {
        redisMap.clear();
        redisMap.close();
    }

    // ========== Позитивные тесты ==========

    @Test
    void putAndGetTest() {
        redisMap.put("language", "Java");
        String result = redisMap.get("language");
        assertEquals("Java", result);
    }

    @Test
    void containsKeyAndValueTest() {
        redisMap.put("framework", "Spring");
        assertTrue(redisMap.containsKey("framework"));
        assertTrue(redisMap.containsValue("Spring"));
    }

    @Test
    void removeTest() {
        redisMap.put("name", "Sasha");
        String removed = redisMap.remove("name");
        assertEquals("Sasha", removed);
        assertFalse(redisMap.containsKey("name"));
    }

    @Test
    void putAllTest() {
        Map<String, String> data = Map.of("a", "1", "b", "2", "c", "3");
        redisMap.putAll(data);
        assertEquals(3, redisMap.size());
        assertEquals("2", redisMap.get("b"));
    }

    @Test
    void entrySetTest() {
        redisMap.put("k1", "v1");
        redisMap.put("k2", "v2");

        Set<Map.Entry<String, String>> entries = redisMap.entrySet();

        assertEquals(2, entries.size());
        assertTrue(entries.stream().anyMatch(e -> e.getKey().equals("k1") && e.getValue().equals("v1")));
        assertTrue(entries.stream().anyMatch(e -> e.getKey().equals("k2") && e.getValue().equals("v2")));
    }

    @Test
    void keySetTest() {
        redisMap.put("alpha", "A");
        redisMap.put("beta", "B");

        Set<String> keys = redisMap.keySet();
        assertTrue(keys.contains("alpha"));
        assertTrue(keys.contains("beta"));
    }

    @Test
    void valuesTest() {
        redisMap.put("k1", "one");
        redisMap.put("k2", "two");

        Collection<String> values = redisMap.values();
        assertTrue(values.contains("one"));
        assertTrue(values.contains("two"));
    }

    @Test
    void isEmptyAndClearTest() {
        assertTrue(redisMap.isEmpty());

        redisMap.put("temp", "123");
        assertFalse(redisMap.isEmpty());

        redisMap.clear();
        assertTrue(redisMap.isEmpty());
    }

    // ========== Негативные тесты ==========

    @Test
    void getNullKeyTest() {
        assertNull(redisMap.get(null));
    }

    @Test
    void containsKeyWithNullTest() {
        assertFalse(redisMap.containsKey(null));
    }

    @Test
    void removeWithNullTest() {
        assertNull(redisMap.remove(null));
    }
}