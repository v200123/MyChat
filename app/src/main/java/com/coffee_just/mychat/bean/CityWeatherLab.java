package com.coffee_just.mychat.bean;

import org.litepal.LitePal;

import java.util.ArrayList;

/**
 * 记录选择天气的城市的代码
 */
public class CityWeatherLab {
    private CityWeatherLab() {
    }

    public static CityWeatherLab getLab() {
        if (sLab == null)
            sLab = new CityWeatherLab();

        return sLab;
    }

    public ArrayList<CityWeather> getCityWeathers() {
        return CityWeathers;
    }

    private static CityWeatherLab sLab;
    private static ArrayList<CityWeather> CityWeathers = (ArrayList) LitePal.findAll(CityWeather.class);

    public static void addWeather(CityWeather cityWeather) {
        cityWeather.save();
        CityWeathers.add(cityWeather);
    }
}
