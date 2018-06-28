package com.example.root.curriculum.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.ArticleAdapter;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.ArticleData;
import com.example.root.curriculum.model.Model;
import com.example.root.curriculum.util.ToastUtil;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment<IBasePresenter> {

    @BindView(R.id.hm_multipleStatusView) MultipleStatusView statusView;
    @BindView(R.id.mRecyclerView) RecyclerView rv_home;
    @BindView(R.id.hm_swipeRe) SwipeRefreshLayout layout;
    private ArticleAdapter adapter;


    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    public static HomeFragment newInstance(String info) {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews() {
        adapter = new ArticleAdapter(null);
        mLayoutStatusView = statusView;
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv_home.setAdapter(adapter);
        rv_home.setLayoutManager(manager);

        //显示加载框
        showLoading();
        mLayoutStatusView.showContent();
        unsubscribe();
        disposable = Model.getArtData()
                .subscribe(new Consumer<ArticleData>() {
                    @Override
                    public void accept(ArticleData datasBean) throws Exception {
                        ToastUtil.showToast("首页加载成功");
                        //隐藏加载框
                        hideLoading();
                        adapter.addData(datasBean.getData().getDatas());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.showToast("加载失败");
                        _IfError();
                    }
                });
    }

    //当获取数据出现错误的处理
    private void _IfError() {

    }

    @Override
    protected void onRetry() {

    }
}
