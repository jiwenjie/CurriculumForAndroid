package com.example.root.curriculum.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.curriculum.Constants;
import com.example.root.curriculum.R;
import com.example.root.curriculum.activity.LoginActivity;
import com.example.root.curriculum.base.BaseFragment;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.Users;
import com.example.root.curriculum.util.JsonHandler;
import com.example.root.curriculum.util.JsonThread;
import com.example.root.curriculum.util.ToastUtil;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class MineFragment extends BaseFragment<IBasePresenter> {

    @BindView(R.id.iv_avatar) CircleImageView iv_avatar;
    @BindView(R.id.tv_nickname) TextView tv_nickName;   //用户名
    @BindView(R.id.tv_view_homepage) TextView tv_view_homepage;
    @BindView(R.id.tv_collection) TextView tv_collection;
    @BindView(R.id.tv_comment) TextView tv_comment;
    @BindView(R.id.tv_mine_message) TextView tv_mine_message;
    @BindView(R.id.tv_mine_attention) TextView tv_mine_attention;
    @BindView(R.id.tv_mine_cache) TextView tv_mine_cache;
    @BindView(R.id.tv_watch_history) TextView tv_watch_history;
    @BindView(R.id.tv_feedback) TextView tv_feedback;

    //用户登陆部分相关
    private Users users;
    private JsonHandler jsonHandler;
    private JsonThread jsonThread;
    private Menu menu;

    public static MineFragment newInstance(String info) {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        args.putString("info", info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViews() {
        initUserPart();
    }

    /**
     * 初始化用户有关部分
     */
    private void initUserPart() {
        Resources resources = getResources();

        jsonHandler = new JsonHandler(this);
        jsonThread = new JsonThread(resources, jsonHandler);
        Thread thread = new Thread(jsonThread);
        thread.start();

        // 初始化用户类
        SharedPreferences share = getActivity().getSharedPreferences(getResources().getString(R.string.config_file_path), MODE_PRIVATE);
        users = new Users(resources, share, jsonHandler, jsonThread);

        //点击用户名的时候跳转登陆界面（先检查当前是否登陆）
        tv_nickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!Constants.IS_LOGIN) {
                    //没有登陆
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 1);
//                } else {
//                    ToastUtil.showToast("您已登陆");
//                }
            }
        });

    }

    public void onLoginSuccess() {
        SharedPreferences share = getActivity().getSharedPreferences(getResources().getString(R.string.config_file_path), MODE_PRIVATE);
        String username = share.getString("username", "");
        tv_nickName.setText(username);
        ToastUtil.showToast(getString(R.string.message_login_success));
    }

    public void onFailed(String event) {
        SharedPreferences share = getActivity().getSharedPreferences(getResources().getString(R.string.config_file_path), MODE_PRIVATE);
        String username = share.getString("username", "");
        if (event.equals("login")) {
            ToastUtil.showToast(username + " " + getResources().getString(R.string.message_login_fail));
        } else if (event.equals("register")){
            ToastUtil.showToast(getString(R.string.message_register_failed));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ToastUtil.showToast("进入onActivityResult");
        Log.d("MineFragment", "进入onActivity=======================================================");
        if (data == null) {
            return;
        }
        if (resultCode == 1) {
            Log.d("MineFragment", "进入onActivity     1111111111111=======================================================");
            users.login(data.getStringExtra("username"), data.getStringExtra("password"));
        }
        else if (resultCode == 3) {
            Log.d("MineFragment", "进入onActivity   33333333333=======================================================");
            users.register(data.getStringExtra("username"), data.getStringExtra("password"));
        }
    }

    @Override
    protected void onRetry() {

    }
}
