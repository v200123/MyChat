package com.coffee_just.mychat.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 设定为保存城市的ID，以方便下次直接调用
 */
public class CityWeather extends LitePalSupport {
    public CityWeather(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    private String cityCode;

}
