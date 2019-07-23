package com.example.moviecataloguefinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.moviecataloguefinal.R;
import com.example.moviecataloguefinal.activity.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_change_settings :
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                return true;
            case R.id.action_reminder :
                Intent intent = new Intent(this, ReminderSettingActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_movie_resolver :
                Intent movieResolverIntent = new Intent(this, MovieResolverActivity.class);
                startActivity(movieResolverIntent);
                return true;
            case R.id.action_tv_show_resolver :
                Intent tvShowResolverIntent = new Intent(this, TvShowResolverActivity.class);
                startActivity(tvShowResolverIntent);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

}
