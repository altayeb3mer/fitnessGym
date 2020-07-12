package com.example.fitnessgym.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.fitnessgym.Fragment.FragmentTabChats;
import com.example.fitnessgym.Fragment.FragmentTabClass;
import com.example.fitnessgym.Fragment.FragmentTabMyProfile;
import com.example.fitnessgym.Fragment.FragmentTabSales;
import com.example.fitnessgym.Fragment.FragmentTabVideos;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.ToolbarClass;
import com.example.fitnessgym.Utils.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TabLayoutProfile extends ToolbarClass {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static ViewPagerAdapter adapter;

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_tab_layout_profile, "ملفي الشخصي");

        initTabLay();
    }

    private void initTabLay() {
        tabLayout = findViewById(R.id.main_tablayout);
        viewPager = findViewById(R.id.main_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //add to tab adapter
        adapter.addFragment(new FragmentTabClass(),"الكلاسات");
        adapter.addFragment(new FragmentTabVideos(),"الفيديوهات");
        adapter.addFragment(new FragmentTabChats(),"الدردشات");
        adapter.addFragment(new FragmentTabSales(),"المشتريات");
        adapter.addFragment(new FragmentTabMyProfile(),"البروفايل");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

}
