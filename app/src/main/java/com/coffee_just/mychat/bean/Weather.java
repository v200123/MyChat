package com.coffee_just.mychat.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

/**
 * 定义接受天气的类
 */
public class Weather  extends LitePalSupport {
    private String time;
    @SerializedName("cityInfo")
    private Cityinfo cityinfo;
    private String date;
    private String message;
    private int status;
    private Data data;


    public void setTime(String time) {
        this.time = time;
    }
    public String getTime() {
        return time;
    }


    public void setCityinfo(Cityinfo cityinfo) {
        this.cityinfo = cityinfo;
    }
    public Cityinfo getCityinfo() {
        return cityinfo;
    }


    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }


    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }


    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

}
