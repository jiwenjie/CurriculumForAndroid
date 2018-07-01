package com.example.root.curriculum.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.ArticleAdapter;
import com.example.root.curriculum.adapter.NewsAdapter;
import com.example.root.curriculum.adapter.SearchAdapter;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.GankBeautyResult;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.NewsList;
import com.example.root.curriculum.model.Model;
import com.example.root.curriculum.util.NetWorkUtil;
import com.example.root.curriculum.util.ToastUtil;


import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchFragment extends BaseFragment<IBasePresenter>
        implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search_multipleStatusView) MultipleStatusView statusView;
    @BindView(R.id.search_RecyclerView) RecyclerView rv_search;
    @BindView(R.id.search_refresh)  SwipeRefreshLayout layout;

    private NewsAdapter adapter;

    private int page = 0; //一次加 20

    public static SearchFragment newInstance(String info) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViews() {

        setRecyclerView();

        mLayoutStatusView = statusView;
        //显示加载框
        mLayoutStatusView.showLoading();
        getHomeData();
    }

    private void getHomeData() {

        //调用释放资源，防止内存泄漏
        unsubscribe();
        disposable = Model.getNews(page)
                .subscribe(new Consumer<NewsList>() {
                    @Override
                    public void accept(NewsList newsList) throws Exception {
                        ToastUtil.showToast("加载成功");
                        mLayoutStatusView.showContent();
                        adapter.addData(newsList.getNewsList());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtil.showToast("加载失败");
                        _IfError();
                    }
                });
    }

    //对adapter和recyclerView进行一些设置
    private void setRecyclerView() {
        adapter = new NewsAdapter(null);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);

        //设置预加载
        adapter.setPreLoadNumber(3);
        adapter.setOnLoadMoreListener(this, rv_search);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        //设置有关refresh的部分
        layout.setOnRefreshListener(this);
        layout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);       //设置旋转的颜色

        rv_search.setLayoutManager(manager);
        rv_search.setAdapter(adapter);
    }

    //当获取数据出现错误的处理
    private void _IfError() {
        //首先判断是否有网络
        if (!NetWorkUtil.isNetWorkConnected(getActivity()) && !NetWorkUtil.isWifiConnected(getActivity())) {
            mLayoutStatusView.showNoNetwork();
        }
        mLayoutStatusView.showError();
    }

    @Override
    protected void onRetry() {
        page += 20;
        getHomeData();
    }

    @Override
    public void onRefresh() {
        try {
            Thread.sleep(1200);
            //不隐藏刷新图标
            layout.setRefreshing(false);
            //重新请求网络数据
            page += 20;
            getHomeData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page += 20;
        ToastUtil.showToast("当前页数为" + page);

        rv_search.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page * 20 >= 8000) {
                    //全部加载完成
                    adapter.loadMoreEnd();
                } else {
                    getHomeData();
                    adapter.loadMoreComplete();
                }
            }
        }, 1200);
    }
}
