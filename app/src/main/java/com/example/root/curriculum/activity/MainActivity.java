package com.example.root.curriculum.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.ViewPagerAdapter;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.fragment.HomeFragment;
import com.example.root.curriculum.fragment.MineFragment;
import com.example.root.curriculum.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<IBasePresenter> {

    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private MenuItem preMenuItem;
    private List<Fragment> list_fragment = new ArrayList<>();

    private void setupViewPager(ViewPager viewPager) {

        list_fragment.add(HomeFragment.newInstance("首页"));
        list_fragment.add(SearchFragment.newInstance("信息"));
        list_fragment.add(MineFragment.newInstance("我的"));
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), list_fragment);

        viewPager.setAdapter(adapter);

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        //设置状态栏透明
        //initSystemBarTint();

        viewPager = findViewById(R.id.vp);
        bottomNavigationView = findViewById(R.id.bnv_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_explore:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_me:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (preMenuItem != null) {
                    preMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                preMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    @Override
    protected void onRetry() {

    }

}
