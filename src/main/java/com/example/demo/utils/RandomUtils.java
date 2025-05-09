package com.example.demo.utils;

import java.util.Random;
import java.util.List;

public class RandomUtils {

    private static final Random random = new Random();

    // 获取 [0, max) 范围内的随机整数
    public static int nextInt(int max) {
        return random.nextInt(max);
    }

    // 获取 [min, max) 范围内的随机整数
    public static int nextInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int nextIntExclude(int min, int max, int exclude) {
        int result = random.nextInt(max - min) + min;
        while (result == exclude) {
            result = random.nextInt(max - min) + min;
        }
        return result;
    }

    public static int nextIntExclude(int min, int max, List<Integer> excludes) {
        int result = random.nextInt(max - min) + min;
        while (excludes.contains(result)) {
            result = random.nextInt(max - min) + min;
        }
        return result;
    }

    // 获取随机布尔值（true 或 false）
    public static boolean nextBoolean() {
        return random.nextBoolean();
    }

    // 从字符串中随机选取字符组成指定长度的字符串
    public static String nextString(String base, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(base.length());
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }

    // 从列表中随机选取一个元素
    public static <T> T randomFromList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }

}