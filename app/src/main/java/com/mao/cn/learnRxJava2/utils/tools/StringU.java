package com.mao.cn.learnRxJava2.utils.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringU extends StringUtils {
    public StringU() {
    }

    public static String stripEmoji(String text) {
        StringBuilder builder = new StringBuilder();
        long count = (long) Character.codePointCount(text, 0, text.length());

        for (int index = 0; (long) index < count; ++index) {
            int codePoint = Character.codePointAt(text, index);
            if (Character.charCount(codePoint) > 1) {
                builder.appendCodePoint(codePoint);
            }
        }

        return builder.toString();
    }

    public static int length(String value) {
        if (isEmpty(value)) {
            return 0;
        } else {
            int valueLength = 0;

            for (int i = 0; i < value.length(); ++i) {
                String temp = value.substring(i, i + 1);
                if (containChinese(temp)) {
                    valueLength += 2;
                } else {
                    ++valueLength;
                }
            }

            return valueLength;
        }
    }

    public static boolean containChinese(String text) {
        if (isEmpty(text)) {
            return false;
        } else {
            String str = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
            Pattern p = Pattern.compile(str);

            for (int i = 0; i < text.length(); ++i) {
                Matcher m = p.matcher(String.valueOf(text.charAt(i)));
                if (m.find()) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isRealName(String realName) {
        if (isEmpty(realName)) {
            return false;
        } else {
            String str = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2DA-Za-z ]+$";
            Pattern p = Pattern.compile(str);
            Matcher m = p.matcher(realName);
            return m.matches();
        }
    }

    public static boolean containEmoji(String text) {
        if (isEmpty(text)) {
            return false;
        } else {
            StringBuilder builder = new StringBuilder();
            long count = (long) Character.codePointCount(text, 0, text.length());

            for (int index = 0; (long) index < count; ++index) {
                int codePoint = Character.codePointAt(text, index);
                if (Character.charCount(codePoint) > 1) {
                    return true;
                }

                builder.appendCodePoint(codePoint);
            }

            return false;
        }
    }


    public static boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
        if (!isEmpty(string) && isEmpty(searchStrings)) {
            CharSequence[] var2 = searchStrings;
            int var3 = searchStrings.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                CharSequence searchString = var2[var4];
                if (endsWith(string, searchString)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

}
