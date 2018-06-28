package com.example.root.curriculum.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.ArticleAdapter;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;


import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends BaseFragment<IBasePresenter> {

    @BindView(R.id.hm_multipleStatusView)
    MultipleStatusView statusView;
    private ArticleAdapter adapter;
    @BindView(R.id.mRecyclerView)
    RecyclerView rv_home;


    public static SearchFragment newInstance(String info) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onRetry() {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {

        rv_home.setLayoutManager(new LinearLayoutManager(getContext()));
       // rv_home.setAdapter(adapters);


    }
}
