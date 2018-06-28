package com.example.root.curriculum.fragment;

import android.os.Bundle;

import com.example.root.curriculum.R;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;

public class MineFragment extends BaseFragment<IBasePresenter> {

    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected void onRetry() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {

    }
}
