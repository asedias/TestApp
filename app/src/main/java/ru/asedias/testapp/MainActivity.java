package ru.asedias.testapp;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import android.widget.Toast;

import java.util.ArrayList;

import ru.asedias.testapp.api.Callback;
import ru.asedias.testapp.api.GetEvents;
import ru.asedias.testapp.fragments.EventsFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.football);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        ColorStateList colors;
        if (Build.VERSION.SDK_INT >= 23) {
            colors = getResources().getColorStateList(R.color.icon_tint_list, getTheme());
        }
        else {
            colors = getResources().getColorStateList(R.color.icon_tint_list);
        }

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            Drawable icon = tab.getIcon();
            if (icon != null) {
                icon = DrawableCompat.wrap(icon);
                DrawableCompat.setTintList(icon, colors);
            }
        }

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setTitle(getTitle(position));
            }

            @Override public void onPageScrollStateChanged(int state) { }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }
    private int getTitle(int position) {
        switch (position) {
            case 1: return R.string.hockey;
            case 2: return R.string.tennis;
            case 3: return R.string.basketball;
            case 4: return R.string.volleyball;
            case 5: return R.string.cybersport;
            default: return R.string.football;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        EventsFragment footballFragment = EventsFragment.newInstance("football");
        EventsFragment hockeyFragment = EventsFragment.newInstance("hockey");
        EventsFragment tennisFragment = EventsFragment.newInstance("tennis");
        EventsFragment basketballFragment = EventsFragment.newInstance("basketball");
        EventsFragment volleyballFragment = EventsFragment.newInstance("volleyball");
        EventsFragment cybersportFragment = EventsFragment.newInstance("cybersport");

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default: return EventsFragment.newInstance("");
                case 0: {
                    return footballFragment;
                }
                case 1: {
                    return hockeyFragment;
                }
                case 2: {
                    return tennisFragment;
                }
                case 3: {
                    return basketballFragment;
                }
                case 4: {
                    return volleyballFragment;
                }
                case 5: {
                    return cybersportFragment;
                }
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    }
}
