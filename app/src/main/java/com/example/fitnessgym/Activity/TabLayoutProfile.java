package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import klogi.com.RtlViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.fitnessgym.Fragment.FragmentTabChats;
import com.example.fitnessgym.Fragment.FragmentTabClass;
import com.example.fitnessgym.Fragment.FragmentTabClassCoach;
import com.example.fitnessgym.Fragment.FragmentTabMyProfile;
import com.example.fitnessgym.Fragment.FragmentTabSales;
import com.example.fitnessgym.Fragment.FragmentTabVideos;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.example.fitnessgym.Utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class TabLayoutProfile extends ToolbarClass {

    public static TabLayout tabLayout;
    public static RtlViewPager viewPager;
    public static ViewPagerAdapter adapter;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_tab_layout_profile, "حسابي");

        initTabLay();
    }

    private void initTabLay() {
        tabLayout = findViewById(R.id.main_tablayout);
        viewPager = findViewById(R.id.main_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //add to tab adapter
//        tabLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        SharedPreferences sp = getSharedPreferences("data", 0);
        String userType = sp.getString("acc_type", "");
        Log.d("acc_type",userType);

        if (userType.equalsIgnoreCase("user")){


//            adapter.addFragment(new FragmentTabVideos(),"الفيديوهات");




            adapter.addFragment(new FragmentTabClass(),"الكلاسات");
            adapter.addFragment(new FragmentTabMyProfile(),"البروفايل");
            adapter.addFragment(new FragmentTabSales(),"المشتريات");
            adapter.addFragment(new FragmentTabChats(),"الدردشات");
        }else if (userType.equalsIgnoreCase("coach")){
            adapter.addFragment(new FragmentTabChats(),"الدردشات");
            adapter.addFragment(new FragmentTabSales(),"المشتريات");
            adapter.addFragment(new FragmentTabVideos(),"الفيديوهات");
            adapter.addFragment(new FragmentTabMyProfile(),"البروفايل");
            adapter.addFragment(new FragmentTabClass(),"الكلاسات");
        }else{
            adapter.addFragment(new FragmentTabChats(),"الدردشات");
            adapter.addFragment(new FragmentTabSales(),"المشتريات");
            adapter.addFragment(new FragmentTabMyProfile(),"البروفايل");
            adapter.addFragment(new FragmentTabClass(),"الكلاسات");
        }

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
