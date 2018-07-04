package com.example.root.curriculum.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.ArticleAdapter;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.ArticleData;
import com.example.root.curriculum.model.Model;
import com.example.root.curriculum.util.NetWorkUtil;
import com.example.root.curriculum.util.ToastUtil;

import org.reactivestreams.Subscriber;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment<IBasePresenter>
        implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.hm_multipleStatusView) MultipleStatusView statusView;
    @BindView(R.id.mRecyclerView) RecyclerView rv_home;
    @BindView(R.id.hm_swipeRe) SwipeRefreshLayout layout;
    private ArticleAdapter adapter;

    private int page = 0;   //表示当前访问的页码

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

        setRecyclerView();

        mLayoutStatusView = statusView;
        //显示加载框
        mLayoutStatusView.showLoading();
        getHomeData();
    }

    //对adapter和recyclerView进行一些设置
    private void setRecyclerView() {
        adapter = new ArticleAdapter(null);

        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);

        //设置预加载
        adapter.setPreLoadNumber(3);
        adapter.setOnLoadMoreListener(this, rv_home);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, 2);

        //设置有关refresh的部分
        layout.setOnRefreshListener(this);
        layout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);       //设置旋转的颜色

        rv_home.setLayoutManager(manager);
        rv_home.setAdapter(adapter);

    }

    //获取数据部分
    private void getHomeData() {
        unsubscribe();
        disposable = Model.getArtData(page)
                .subscribe(new Consumer<ArticleData>() {
                    @Override
                    public void accept(ArticleData datasBean) throws Exception {
                        ToastUtil.showToast("数据加载成功");
                        //隐藏加载框
                        mLayoutStatusView.showContent();
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
        //首先判断是否有网络
        if (!NetWorkUtil.isNetWorkConnected(getActivity()) && !NetWorkUtil.isWifiConnected(getActivity())) {
            mLayoutStatusView.showNoNetwork();
        }
        mLayoutStatusView.showError();
    }

    //点击重试按钮
    @Override
    protected void onRetry() {
        ToastUtil.showToast("正在尝试重新获取数据");
        getHomeData();
    }

    //下拉刷新部分
    @Override
    public void onRefresh() {
        try {
            Thread.sleep(1200);
            //不隐藏刷新图标
            layout.setRefreshing(false);
            //重新请求网络数据
            page++;
            getHomeData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        ToastUtil.showToast("当前页数为" + page);

        rv_home.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (page * 20 >= 600) {
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
