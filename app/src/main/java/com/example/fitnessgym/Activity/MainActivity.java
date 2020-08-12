package com.example.fitnessgym.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fitnessgym.Fragment.FragmentFood;
import com.example.fitnessgym.Fragment.FragmentClasses;
import com.example.fitnessgym.Fragment.FragmentFree;
import com.example.fitnessgym.Fragment.FragmentProduct;
import com.example.fitnessgym.Fragment.MainFragment;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Utils.CustomViewPager;
import com.example.fitnessgym.Utils.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  BottomNavigationView.OnNavigationItemSelectedListener,NavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView bottomNavigationView;
    View nHeader;
    TextView textViewPhone, textViewName;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    //viewPager
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);

        init();
        initNavHeader();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras!=null) {
            String page = extras.getString("page");


            if (page.equals("classes")) {
                switchToFragment(4);


            }
            else  if (page.equals("product")) {
                switchToFragment(3);


            }
            else  if (page.equals("food")) {
                switchToFragment(2);


            }
        }

//        viewPager.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                return true;
//            }
//        });

    }

    private void init() {
        viewPager =  findViewById(R.id.viewpager);
        bottomNavigationView = findViewById(R.id.btn_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        imageButton_search = findViewById(R.id.image_btn_search);

        toolbar = findViewById(R.id.public_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.n_drawer);
        navigationView = findViewById(R.id.n_view);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

//        imageButton_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this,SearchActivity.class));
//            }
//        });
        initViewPager();

    }

    MenuItem prevMenuItem;
    private void initViewPager() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page",""+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        mainFragment = new MainFragment();
        foodFragment = new FragmentFood();
        fragmentProduct = new FragmentProduct();
        fragmentClasses = new FragmentClasses();
        fragmentFree = new FragmentFree();

        adapter.addFragment(mainFragment,"الرئيسية");
        adapter.addFragment(foodFragment,"تغزية ورجيم");
        adapter.addFragment(fragmentProduct,"منتجات");
        adapter.addFragment(fragmentClasses,"كلاسات");
        adapter.addFragment(fragmentFree,"الصف المجاني");
        viewPager.setAdapter(adapter);
    }


    FragmentFree fragmentFree;
    FragmentClasses fragmentClasses;
    FragmentProduct fragmentProduct;
    MainFragment mainFragment;
    FragmentFood foodFragment;

    private void SetNavigationItemSelected(int id){
        bottomNavigationView.getMenu().findItem(id).setChecked(true);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //nav_btn

            case R.id.btn_nav_main_ac: {
                switchToFragment(1);
                break;
            }
            case R.id.btn_nav_food: {
                switchToFragment(2);
                break;
            }
            case R.id.btn_nav_products: {
                switchToFragment(3);
                break;
            }
            case R.id.btn_nav_classes: {
                switchToFragment(4);
                break;
            }
            case R.id.btn_nav_free: {
                switchToFragment(5);
                break;
            }
            //navigation menu
            case R.id.nav_menu_login: {
                startActivity(new Intent(getApplicationContext(), Login.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_menu_my_profile: {
                startActivity(new Intent(getApplicationContext(), TabLayoutProfile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }

            case R.id.nav_menu_about_us: {
                openUrl("");
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_facebook: {
                startActivity(new Intent(getApplicationContext(), TabLayoutProfile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_twitter: {
                startActivity(new Intent(getApplicationContext(), TabLayoutProfile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case R.id.nav_website: {
                startActivity(new Intent(getApplicationContext(), TabLayoutProfile.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
        }

//
//

//            case R.id.nav_menu_news_paper: {
//                startActivity(new Intent(this,NewsPapers.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_my_sub: {
//                switchToFragment(2);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_notification: {
//                switchToFragment(3);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_saved: {
//                switchToFragment(4);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_menu_setting: {
//                switchToFragment(5);
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_facebook: {
//                startActivity(new Intent(MainActivity.this,Login.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_twitter: {
//                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_website: {
//                startActivity(new Intent(MainActivity.this,ConfirmActivity.class));
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//            case R.id.nav_notification: {
//                PushNotification("اختبار","يعمل الاختبار بنجاح");
//                drawerLayout.closeDrawer(GravityCompat.START);
//                break;
//            }
//
//        }
        return true;
    }
    public void switchToFragment(int f_no) {
//        FragmentManager manager = getSupportFragmentManager();
        switch (f_no) {
            case 1: {
                viewPager.setCurrentItem(0);
                toolbar.setTitle("الرئيسية");
                SetNavigationItemSelected(R.id.btn_nav_main_ac);
                break;
            }
            case 2: {
                viewPager.setCurrentItem(1);
                toolbar.setTitle("تغذية ورجيم");
                SetNavigationItemSelected(R.id.btn_nav_food);
                break;
            }
            case 3: {
                viewPager.setCurrentItem(2);
                toolbar.setTitle("منتجات");
                SetNavigationItemSelected(R.id.btn_nav_products);
                break;
            }
            case 4: {
                viewPager.setCurrentItem(3);
                toolbar.setTitle("كلاسات");
                SetNavigationItemSelected(R.id.btn_nav_classes);
                break;
            }
            case 5: {
                viewPager.setCurrentItem(4);
                toolbar.setTitle("الصف المجاني");
                SetNavigationItemSelected(R.id.btn_nav_free);
                break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (viewPager.getCurrentItem()==0){
                super.onBackPressed();
            }else{
                viewPager.setCurrentItem(0);
            }
        }
    }

    private void initNavHeader() {
        SharedPreferences sp = getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");

        nHeader = navigationView.getHeaderView(0);
        textViewName = nHeader.findViewById(R.id.name);
        textViewPhone = nHeader.findViewById(R.id.phone);

        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mem_id.equals("")){
                    startActivity(new Intent(MainActivity.this,Login.class));
                }
            }
        });

        if (!mem_id.equals("")){
            String name = sp.getString("us_name", "");
            String phone =  sp.getString("mem_phone", "");
            textViewName.setText(name);
            textViewPhone.setText(phone);


        }else{
//            nHeader.setVisibility(View.GONE);
            textViewPhone.setVisibility(View.GONE);
            textViewName.setText("اضغط لتسجيل الدخول");
            Toast.makeText(this, "انت تتصفح التطبيق كزائر", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSomeNavItem();
    }

    Menu navMenu;
    private void hideSomeNavItem(){
        navMenu = navigationView.getMenu();
        MenuItem login = navMenu.findItem(R.id.nav_menu_login);
        MenuItem myProfile = navMenu.findItem(R.id.nav_menu_my_profile);

        SharedPreferences sp = getSharedPreferences("data", 0);
        String mem_id = sp.getString("id", "");
        if (mem_id.equals("")){
            myProfile.setVisible(false);
        }else{
            login.setVisible(false);
        }
    }

    private void openUrl(String url){
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
