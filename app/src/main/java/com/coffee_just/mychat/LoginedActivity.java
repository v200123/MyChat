package com.coffee_just.mychat;

import android.os.Bundle;


import com.chaychan.library.BottomBarLayout;
import com.coffee_just.mychat.fragment.MyFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.MissingFormatArgumentException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

public class LoginedActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined1);
        mViewPager = findViewById(R.id.ViewPager);
        mTabLayout = findViewById(R.id.tabLayout);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
    }
}
