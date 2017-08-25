package com.mao.cn.learnRxJava2.utils.tools;

import java.util.List;

public class ListU {
    public ListU() {
    }

    public static <V> boolean isEmpty(List<V> sourceList) {
        return sourceList == null || sourceList.isEmpty();
    }

    public static <V> boolean notEmpty(List<V> sourceList) {
        return !isEmpty(sourceList);
    }
}