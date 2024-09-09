package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.musicapp.Fragment.Fragment_Account;
import com.example.musicapp.Fragment.Fragment_Farvote;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;

import com.example.musicapp.Adapter.MainViewAdapter;
import com.example.musicapp.Fragment.Fragment_TimKiem;
import com.example.musicapp.Fragment.Fragment_TrangChu;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        init();
    }
    private void init(){
        MainViewAdapter mainViewAdapter = new MainViewAdapter(getSupportFragmentManager());
        mainViewAdapter.addFragment(new Fragment_TrangChu(), "Home");
        mainViewAdapter.addFragment(new Fragment_TimKiem(), "Search");
        mainViewAdapter.addFragment(new Fragment_Farvote(),"Yeu thich");
        mainViewAdapter.addFragment(new Fragment_Account(),"Account");
        viewPaper.setAdapter(mainViewAdapter);
        tabLayout.setupWithViewPager(viewPaper);
        tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);
        tabLayout.getTabAt(2).setIcon(R.drawable.favorite);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);
    }
    private void anhxa(){
        tabLayout = findViewById(R.id.myTabLayout);
        viewPaper = findViewById(R.id.viewPager);
    }


}