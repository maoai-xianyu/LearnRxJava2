package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class Casts implements Serializable {
    /**
     * alt : https://movie.douban.com/celebrity/1054521/
     * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"}
     * name : 蒂姆·罗宾斯
     * id : 1054521
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

}
