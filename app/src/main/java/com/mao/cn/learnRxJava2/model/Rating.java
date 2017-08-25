package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class Rating implements Serializable {
    /**
     * max : 10
     * average : 9.6
     * stars : 50
     * min : 0
     */

    private int max;
    private double average;
    private String stars;
    private int min;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
