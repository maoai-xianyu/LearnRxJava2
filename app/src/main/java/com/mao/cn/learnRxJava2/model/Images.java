package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;

/**
 * author:  zhangkun .
 * date:    on 2017/8/3.
 */

public class Images implements Serializable {

    /**
     * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg
     * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg
     * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg
     */

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}

