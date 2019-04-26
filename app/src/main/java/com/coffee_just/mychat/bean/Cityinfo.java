package com.coffee_just.mychat.bean;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.LitePalSupport;

public class Cityinfo  extends LitePalSupport {
    private String city;
    @SerializedName("cityId")
    private String cityid;
    private String parent;
    @SerializedName("updateTime")
    private String updatetime;


    public void setCity(String city) {
        this.city = city;
    }
    public String getCity() {
        return city;
    }


    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
    public String getCityid() {
        return cityid;
    }


    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getParent() {
        return parent;
    }


    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
    public String getUpdatetime() {
        return updatetime;
    }

}
