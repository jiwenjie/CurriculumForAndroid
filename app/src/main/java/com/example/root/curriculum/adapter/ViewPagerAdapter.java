package com.example.root.curriculum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_fragment;
    private List<String> list_title;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list_fragment = list;
//        this.list_title = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_fragment.size();
    }

    public void addFragment(Fragment fragment) {
        list_fragment.add(fragment);
    }

//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return list_title.get(position);
//    }
}
