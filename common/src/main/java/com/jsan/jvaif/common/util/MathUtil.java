package com.jsan.jvaif.common.util;

import java.util.Random;

/**
 * @description:
 * @author: jcwang
 * @create: 2019-08-03 21:41
 **/
public class MathUtil {
    /**
     * 生成指定长度的随机字符串
     *
     * @param length 生成字符串的长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789" ;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 生成10位以内指定长度的随机数
     *
     * @param length 指定的长度,小于10
     * @return 随机数字
     */
    public static String getRandomInt(int length) {
        final int maxNum = 10;
        int i;
        int count = 0;
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuilder pwd = new StringBuilder("");
        Random r = new Random();
        while (count < length) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();
    }
}
