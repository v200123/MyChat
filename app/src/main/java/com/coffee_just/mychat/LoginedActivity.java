package com.coffee_just.mychat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.coffee_just.mychat.fragment.MyFragmentPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class LoginedActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private NavigationView navigationView;
    private ImageView mImageView;
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
        navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_logined1);
        mImageView = headerLayout.findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load("http://www.wanandroid.com/resources/image/pc/logo.png").into(mImageView);

    }
}
