package org.redis;

import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class RedisMap implements Map<String, String> {

    private final Jedis jedis;

    public RedisMap() {
        this.jedis = new Jedis("localhost", 6379);
    }

    public RedisMap(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public int size() {
        return (int) jedis.dbSize();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof String && jedis.exists((String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        if (!(value instanceof String)) return false;
        for (String val : values()) {
            if (val.equals(value)) return true;
        }
        return false;
    }

    @Override
    public String get(Object key) {
        if (!(key instanceof String)) return null;
        return jedis.get((String) key);
    }

    @Override
    public String put(String key, String value) {
        System.out.println("Connected to: " + jedis.getClient());
        String previous = get(key);
        jedis.set(key, value);
        return previous;
    }

    @Override
    public String remove(Object key) {
        if (!(key instanceof String)) return null;
        String prev = get(key);
        jedis.del((String) key);
        return prev;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        for (Entry<? extends String, ? extends String> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        jedis.flushDB();
    }

    @Override
    public Set<String> keySet() {
        return jedis.keys("*");
    }

    @Override
    public Collection<String> values() {
        Set<String> keys = keySet();
        List<String> result = new ArrayList<>();
        for (String key : keys) {
            result.add(jedis.get(key));
        }
        return result;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        Set<Entry<String, String>> entries = new HashSet<>();
        for (String key : keySet()) {
            entries.add(new AbstractMap.SimpleEntry<>(key, jedis.get(key)));
        }
        return entries;
    }

    public void close() {
        jedis.close();
    }
}