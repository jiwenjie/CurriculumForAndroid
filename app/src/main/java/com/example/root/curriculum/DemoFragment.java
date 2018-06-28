package com.example.root.curriculum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.base.IBaseView;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class DemoFragment extends BaseFragment<IBasePresenter> {

   // @BindView(R.id.tvInfo) TextView tv_info;

    @BindView(R.id.multipleStatusView)
    MultipleStatusView statusView;

    public static DemoFragment newInstance(String info) {
        Bundle args = new Bundle();
        DemoFragment fragment = new DemoFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_demo;
    }

    @Override
    protected void initViews() {

        statusView.showNoNetwork();

    }

    @Override
    protected void onRetry() {

    }
}
