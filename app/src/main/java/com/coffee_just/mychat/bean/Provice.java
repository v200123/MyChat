package com.coffee_just.mychat.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

public class Provice extends LitePalSupport {

    @SerializedName("_id")
    private int Id;
    private int id;
    private int pid;
    @SerializedName("city_code")
    private String cityCode;
    @SerializedName("city_name")
    private String cityName;


    public void set_Id(int Id) {
        this.Id = Id;
    }
    public int get_Id() {
        return Id;
    }


    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }


    public void setPid(int pid) {
        this.pid = pid;
    }
    public int getPid() {
        return pid;
    }


    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
