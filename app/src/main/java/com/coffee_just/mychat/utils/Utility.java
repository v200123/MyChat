package com.coffee_just.mychat.utils;

import android.text.TextUtils;
import android.util.Log;

import com.coffee_just.mychat.bean.Provice;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class Utility {
    private static String TAG = "com.coffee_just.Utiles";

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

    //获取全国省份的信息
    public static void getProvience(){
        HttpUtil.sendOkHttpRequest("http://cdn.sojson.com/_city.json", new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + "省份信息请求下载失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String Response = response.body().string();
                Utility.handleProvinceResponse(Response);
            }
        });
    }
}
