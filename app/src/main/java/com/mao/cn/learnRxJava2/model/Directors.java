package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class Directors implements Serializable{

    /**
     * alt : https://movie.douban.com/celebrity/1047973/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"}
     * name : 弗兰克·德拉邦特
     * id : 1047973
     */

    private String alt;
    private CastsAvators avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public CastsAvators getAvatars() {
        return avatars;
    }

    public void setAvatars(CastsAvators avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Directors{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
