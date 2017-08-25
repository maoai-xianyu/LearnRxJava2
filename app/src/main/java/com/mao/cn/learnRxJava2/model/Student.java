package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;
import java.util.List;

/**
 * author:  zhangkun .
 * date:    on 2017/8/11.
 */

public class Student implements Serializable {

    private String stu_name;
    private String stu_age;
    private List<StudentCourse> studentCourses;

    public Student() {
    }

    public Student(String stu_name, String stu_age, List<StudentCourse> studentCourses) {
        this.stu_name = stu_name;
        this.stu_age = stu_age;
        this.studentCourses = studentCourses;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_age() {
        return stu_age;
    }

    public void setStu_age(String stu_age) {
        this.stu_age = stu_age;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }
}
