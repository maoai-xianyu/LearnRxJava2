package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * @author zhangkun
 * @time 2020/11/18 8:36 PM
 * @Description
 */
public class User implements Serializable {

    private String login;
    private long id;
    private String url;
    private String name;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", id=" + id +
            ", url='" + url + '\'' +
            ", name='" + name + '\'' +
            ", avatar_url='" + avatar_url + '\'' +
            '}';
    }
}
