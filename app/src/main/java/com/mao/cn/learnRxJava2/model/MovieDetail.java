package com.mao.cn.learnRxJava2.model;

import java.io.Serializable;
import java.util.List;

/**
 * author:  zhangkun .
 * date:    on 2017/8/1.
 */

public class MovieDetail implements Serializable {

    /**
     * rating : {"max":10,"average":9.6,"stars":"50","min":0}
     * genres : ["犯罪","剧情"]
     * title : 肖申克的救赎
     * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/17525.jpg","large":"https://img3.doubanio.com/img/celebrity/large/17525.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/34642.jpg","large":"https://img3.doubanio.com/img/celebrity/large/34642.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/img/celebrity/small/5837.jpg","large":"https://img1.doubanio.com/img/celebrity/large/5837.jpg","medium":"https://img1.doubanio.com/img/celebrity/medium/5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
     * collect_count : 1096266
     * original_title : The Shawshank Redemption
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/230.jpg","large":"https://img3.doubanio.com/img/celebrity/large/230.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
     * year : 1994
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p480747492.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p480747492.jpg"}
     * alt : https://movie.douban.com/subject/1292052/
     * id : 1292052
     */

    private Rating rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private Images images;
    private String alt;
    private String id;
    private List<String> genres;
    private List<Casts> casts;
    private List<Directors> directors;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Casts> getCasts() {
        return casts;
    }

    public void setCasts(List<Casts> casts) {
        this.casts = casts;
    }

    public List<Directors> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Directors> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "MovieDetail{" +
                "rating=" + rating +
                ", title='" + title + '\'' +
                ", collect_count=" + collect_count +
                ", original_title='" + original_title + '\'' +
                ", subtype='" + subtype + '\'' +
                ", year='" + year + '\'' +
                ", images=" + images +
                ", alt='" + alt + '\'' +
                ", id='" + id + '\'' +
                ", genres=" + genres +
                ", casts=" + casts +
                ", directors=" + directors +
                '}';
    }
}
