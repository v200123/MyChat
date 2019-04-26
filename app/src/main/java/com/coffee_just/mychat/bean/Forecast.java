package com.coffee_just.mychat.bean;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Forecast extends LitePalSupport {

    private String date;
    private Date ymd;
    private String week;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private int aqi;
    private String fx;
    private String fl;
    private String type;
    private String notice;


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void setYmd(Date ymd) {
        this.ymd = ymd;
    }
    public Date getYmd() {
        return ymd;
    }


    public void setWeek(String week) {
        this.week = week;
    }
    public String getWeek() {
        return week;
    }


    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }
    public String getSunrise() {
        return sunrise;
    }


    public void setHigh(String high) {
        this.high = high;
    }
    public String getHigh() {
        return high;
    }


    public void setLow(String low) {
        this.low = low;
    }
    public String getLow() {
        return low;
    }


    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
    public String getSunset() {
        return sunset;
    }


    public void setAqi(int aqi) {
        this.aqi = aqi;
    }
    public int getAqi() {
        return aqi;
    }


    public void setFx(String fx) {
        this.fx = fx;
    }
    public String getFx() {
        return fx;
    }


    public void setFl(String fl) {
        this.fl = fl;
    }
    public String getFl() {
        return fl;
    }


    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }


    public void setNotice(String notice) {
        this.notice = notice;
    }
    public String getNotice() {
        return notice;
    }

}
