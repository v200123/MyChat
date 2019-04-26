package com.coffee_just.mychat.utils;

import android.text.TextUtils;

import com.coffee_just.mychat.bean.Provice;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.litepal.LitePal;

import java.util.ArrayList;

public class Utility {
    /**
     * 查询各地的省份的
     * @param response  传入的Json数据
     * @return 是否成功
     */
    public static boolean handleProvinceResponse(String response) {

        if (!TextUtils.isEmpty(response)) {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(response).getAsJsonArray();
            for (JsonElement element:array
                 ) {
                Provice provice = gson.fromJson(element,Provice.class);
                provice.save();//保存省份的信息
            }
        }
        return false;
    }

    public static ArrayList<Provice> handleCityResponse(String id){
        if(LitePal.where("pid =?",id).count(Provice.class)==0)
        {
            return null;
        }
        return (ArrayList<Provice>) LitePal.where("pid = ?",id).find(Provice.class);
    }

}
