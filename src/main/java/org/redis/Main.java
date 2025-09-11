package org.redis;

public class Main {
    public static void main(String[] args) {
        RedisMap map = new RedisMap();

        map.put("language", "Java");
        map.put("framework", "Spring");
        map.put("name", "Sasha");
        map.put("Sasha", "name");
        map.put("name", "name");
        map.put("Misha", "name");

        System.out.println("Размер RedisMap: " + map.size());
        System.out.println("Ключи: " + map.keySet());
    }
}