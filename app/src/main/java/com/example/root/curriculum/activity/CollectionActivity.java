package com.example.root.curriculum.activity;

import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.App;
import com.example.root.curriculum.Constants;
import com.example.root.curriculum.R;
import com.example.root.curriculum.adapter.CollectionAdapter;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.UserMessage;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.ToastUtil;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

import butterknife.BindView;
import io.reactivex.schedulers.Schedulers;

/**
 * 点击收藏部分查看的信息
 */
public class CollectionActivity extends BaseActivity<IBasePresenter> {

    @BindView(R.id.collection_return)
    IconFontTextView ic_return;
    @BindView(R.id.collection_multipleStatusView)
    MultipleStatusView statusView;
    @BindView(R.id.cRecyclerView)
    RecyclerView rv_listCollection;

    private CollectionAdapter adapter;

    // 初始化用户类
    private SharedPreferences share;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initViews() {
        share = getSharedPreferences(getResources().getString(R.string.config_file_path), 0);
        setRecyclerAdapter();
        statusView = mLayoutStatusView;
        statusView.showLoading();
        getClooectionData();
        doOthers();
    }

    private void doOthers() {
        //返回的按钮监听器
        ic_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //退出当前活动
            }
        });
    }

    //获取收藏的数据
    private void getClooectionData() {
        if (Constants.IS_LOGIN) {
            //已经登陆
            ToastUtil.showToast("正在加载");
            Schedulers.io()
                    .createWorker()
                    .schedule(new Runnable() {
                @Override
                public void run() {
                    String username = share.getString("username", "Test");
                    //ToastUtil.showToast("正在加载");
//                    String username = "jack";
                    List<UserMessage> message =
                            LitePal.findAll(UserMessage.class);
                    for (UserMessage mess : message) {
                        if (mess.getUserName().equals(username)) {
                            //说明存在该用户
                            //statusView.showEmpty();
                            ToastUtil.showToast(mess.getUserName() + "您收藏了" + mess.getList().size() + "篇文章");
//                            if (mess.getList().size() <= 0) {
//                                //说明没有收藏文章
//                                statusView.showEmpty();
//                                ToastUtil.showToast(mess.getUserName() + "您还没有收藏文章，先去收藏几篇吧！");
//                            } else {
//                                //说明收藏了文章
//                                statusView.showContent();
//                                adapter.addData(mess.getList());
//                                ToastUtil.showToast("数据显示成功");
//                            }
                        }
                    }
                    //其他的错误情况
                    statusView.showError();
                }
            });

        } else {
            //没有登陆
            ToastUtil.showToast("对不起，您还有没有登陆呦！");
            statusView.showEmpty();
        }
    }

    private void setRecyclerAdapter() {
        adapter = new CollectionAdapter(null);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);

        rv_listCollection.setLayoutManager(manager);
        rv_listCollection.setAdapter(adapter);
    }

    @Override
    protected void onRetry() {

    }

}
