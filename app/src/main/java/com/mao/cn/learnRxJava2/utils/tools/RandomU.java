package com.mao.cn.learnRxJava2.utils.tools;

import java.util.Random;

public class RandomU {
    static Random r = new Random();
    static String wordStr = "0123456789ABCDEF";
    static char[] wordChars = wordStr.toCharArray();
    static String numStr = "0123456789";
    static char[] numChars = numStr.toCharArray();
    static String charStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static char[] charChars = charStr.toCharArray();

    // 产生随机字符串
    public static String randWord(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % wordChars.length;

            buf[i] = wordChars[rnd];
        }
        return new String(buf);
    }

    // 产生随机字符串
    public static String randChar(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % charChars.length;

            buf[i] = charChars[rnd];
        }
        return new String(buf);
    }

    // 产生随机字符串
    public static String randNum(int length) {
        char[] buf = new char[length];
        int rnd;
        for (int i = 0; i < length; i++) {
            rnd = Math.abs(r.nextInt()) % numChars.length;

            buf[i] = numChars[rnd];
        }
        return new String(buf);
    }

    public static int randomNum(int max) {
        return new Random().nextInt(max);
    }

    public static int randomNum(int min, int max) {
        return new Random().nextInt(max) + min;
    }

    public static String UUID() {
        return String.valueOf(java.util.UUID.randomUUID());
    }

    public static int[] getRandomIntWithoutReduplicate(int min, int max, int size) {
        int[] result = new int[size]; // 数组容器
        int arraySize = max - min; // 数组范围
        int[] intArray = new int[arraySize];
        // 初始化数组
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = i + min;
        }
        // 得到不重复的数组
        for (int i = 0; i < size; i++) {
            int c = getRandomInt(min, max - i);
            int index = c - min;
            swap(intArray, index, arraySize - 1 - i);
            result[i] = intArray[arraySize - 1 - i];
        }
        return result;
    }

    /**
     * 交换数组中两个数字位置
     * array
     * x
     * y
     */
    private static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    /**
     * 得到一个随机数int [min, max)
     * 最小随机数值
     * 最大随机数值
     *
     * @return 获得的随机数int
     */
    public static int getRandomInt(int min, int max) {
        return min + Double.valueOf(Math.random() * (max - min)).intValue();
    }

    /**
     * 得到一个随机数int [min, max)
     * 最小随机数值
     * 最大随机数值
     *
     * @return 获得的随机数int
     */
    public static Double getRandomDouble(double min, double max) {
        return min + Math.random() * (max - min);
    }
}
