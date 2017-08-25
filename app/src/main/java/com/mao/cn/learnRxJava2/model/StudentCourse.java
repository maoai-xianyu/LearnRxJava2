package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/11.
 */

public class StudentCourse implements Serializable {

    private String course_name;
    private String course_desc;
    private long course_price;

    public StudentCourse() {
    }

    public StudentCourse(String course_name, long course_price) {
        this.course_name = course_name;
        this.course_price = course_price;
    }

    public StudentCourse(String course_name, String course_desc, long course_price) {
        this.course_name = course_name;
        this.course_desc = course_desc;
        this.course_price = course_price;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public long getCourse_price() {
        return course_price;
    }

    public void setCourse_price(long course_price) {
        this.course_price = course_price;
    }

    public String getCourse_desc() {
        return course_desc;
    }

    public void setCourse_desc(String course_desc) {
        this.course_desc = course_desc;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "course_name='" + course_name + '\'' +
                ", course_desc='" + course_desc + '\'' +
                ", course_price=" + course_price +
                '}';
    }
}
