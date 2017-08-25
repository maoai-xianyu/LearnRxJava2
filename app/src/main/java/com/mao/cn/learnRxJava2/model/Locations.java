package com.mao.cn.learnRxJava2.model;

/**
 * author:  zhangkun .
 * date:    on 2017/8/21.
 */

public class Locations {
    private String longitude; // 经度
    private String latitude;  // 纬度

    public Locations(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Locations{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
