package com.coffee_just.mychat.fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.coffee_just.mychat.R;
import com.coffee_just.mychat.bean.CityWeather;
import com.coffee_just.mychat.bean.CityWeatherLab;
import com.coffee_just.mychat.bean.Cityinfo;
import com.coffee_just.mychat.bean.Data;
import com.coffee_just.mychat.bean.Provice;
import com.coffee_just.mychat.bean.Weather;
import com.coffee_just.mychat.utils.HttpUtil;
import com.coffee_just.mychat.utils.Utility;
import com.google.gson.Gson;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class YourInformationFragment extends Fragment {
    private TextView weatherInformation;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private String address = "http://t.weather.sojson.com/api/weather/city/";
    private final String TAG = "com.weather";
    private ListView listView;
    private TextView cityNameAndWeather;
    private ArrayList<String> Names = new ArrayList<>();
    private ArrayAdapter adapter;
    private int currentLevel;//当前所在的位置
    private SharedPreferences.Editor sharedPreferences;


    /**
     * 包含对地址初始化获取，如果获取了就直接跳转到天气查询
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.your_infomation_fragment, container, false);
        listView = v.findViewById(R.id.choiceCity);
        cityNameAndWeather = v.findViewById(R.id.city_name_weather);
        cityNameAndWeather.setVisibility(View.INVISIBLE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        int hour = calendar.get(Calendar.HOUR_OF_DAY);
//        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        if (LitePal.count(CityWeather.class) != 0) {
            cityNameAndWeather.setVisibility(View.VISIBLE);
            String cityCode = LitePal.findFirst(CityWeather.class).getCityCode();
//            int updateTime = Integer.parseInt(LitePal.findFirst(Cityinfo.class).getUpdatetime());
//            String updateDate = LitePal.findFirst(Weather.class).getDate();

            //todo 这里添加缓存，已防止频繁更新
//            if (updateDate.equals(day)) {
//                if(hour<3&&hour>19)
//                {
//                    if(updateTime>3&&updateTime<8 || updateTime>8&&updateTime>13 || updateTime>13&&updateTime<19)
//                    {
//
//                    }
//                }
//            }
            if (LitePal.where("cityid = ?", cityCode).find(Cityinfo.class) == null) {
                HttpUtil.sendOkHttpRequest(address + cityCode, new okhttp3.Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i(TAG, "onFailure: " + "请求失败了");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String Response = response.body().string();
                        Log.d(TAG, "onResponse: " + Response + "请求的链接为" + address + cityCode);
                        parseJSONWithGson(Response);
                        Log.e(TAG, "onResponse: " + "所有的下载已经完成了");
                    }
                });
            } else {
                Weather weather = LitePal.findFirst(Weather.class);
                weather.setCityinfo(LitePal.findFirst(Cityinfo.class));
                weather.setData(LitePal.findFirst(Data.class));
                cityNameAndWeather.setText("当前选择城市为：" + weather.getCityinfo().getCity() + "\n当前气温为：" + weather.getData().getWendu() + "\n更新时间为："
                        + weather.getCityinfo().getUpdatetime());
            }
            return v;
        }

        //这段时为了查询城市的代码
        queryProvince();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, Names);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        currentLevel = LEVEL_CITY;
        return v;
    }

    private void queryProvince() {

        ArrayList<Provice> provices = (ArrayList<Provice>) LitePal.where("pid = ?", "0").find(Provice.class);
        if (provices.size() == 0) {
            try {
                Thread.sleep(10000);
                provices = (ArrayList<Provice>) LitePal.where("pid = ?", "0").find(Provice.class);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Provice provice : provices
        ) {
            Names.add(provice.getCityName());

        }

    }

    private void queryCity(int position) {
        ArrayList<Provice> provices = Utility.handleCityResponse(LitePal.find(Provice.class, position).getId() + "");
        for (Provice provice : provices
        ) {
            Names.add(provice.getCityName());
        }
    }


    private void parseJSONWithGson(String json) {
        Gson gson = new Gson();
        Weather weather = gson.fromJson(json, Weather.class);
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                cityNameAndWeather.setVisibility(View.VISIBLE);
                cityNameAndWeather.setText("当前选择城市为：" + weather.getCityinfo().getCity() + "\n当前气温为：" + weather.getData().getWendu() + "\n更新时间为："
                        + weather.getCityinfo().getUpdatetime());
            }
        });
//        if (LitePal.where("city = ?", weather.getCityinfo().getCity()).findFirst(Cityinfo.class) == null)
        weather.save();
        weather.getData().save();
        weather.getCityinfo().save();

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (currentLevel) {
                    case 1:
                        Names.clear();
                        currentLevel = LEVEL_COUNTY;
                        queryCity(position + 1);
                        adapter.notifyDataSetChanged();
                        listView.setSelection(0);
                        break;
                    case 2:
                        String cityName = Names.get(position);
                        CityWeatherLab.getLab().addWeather(new CityWeather(LitePal.select("citycode").where("cityName = ?", cityName).findFirst(Provice.class).getCityCode()));
                        Names.clear();
                        adapter.notifyDataSetChanged();
                        String cityCode = LitePal.findFirst(CityWeather.class).getCityCode();
                        HttpUtil.sendOkHttpRequest(address + cityCode, new okhttp3.Callback() {

                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i(TAG, "onFailure: " + "请求失败了");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String Response = response.body().string();
                                Log.d(TAG, "onResponse: " + Response + "请求的链接为" + address + cityCode);
                                parseJSONWithGson(Response);

                            }
                        });
                }
            }
        });
    }
}
