package com.example.root.curriculum.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.curriculum.R;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.Users;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.PreferencesUtils;
import com.example.root.curriculum.util.ToastUtil;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity<IBasePresenter> {

    @BindView(R.id.register_returnOne) IconFontTextView ift_return;
    @BindView(R.id.register_username) EditText et_userName;
    @BindView(R.id.register_password) EditText et_pass;
    @BindView(R.id.register_confirm_pass) EditText et_confirm_pass;
    @BindView(R.id.register_now) Button btn_register;

    SharedPreferences.Editor share =
            getSharedPreferences(getResources().getString(R.string.config_file_path), MODE_PRIVATE)
                    .edit();    //打开编辑开关

    private String userName;
    private String pass;
    private String confirm_pass;

    private Users users;

    @Override
    protected void onStart() {

        users = (Users) getIntent().getSerializableExtra("user_mine");

        super.onStart();
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {
        userName = et_userName.getText().toString();
        pass = et_pass.getText().toString();
        confirm_pass = et_confirm_pass.getText().toString();

        if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(confirm_pass)) {

            if (pass.equals(confirm_pass)) {
                if (pass.length() >= 6) {
                    //说明条件已经匹配
                    Intent intent = getIntent();
                    Bundle bun = new Bundle();
                    bun.putString("username", userName);
                    bun.putString("password", pass);
                    intent.putExtras(bun);
                    //序列化到本地
                    share.putString("username", userName);
                    share.putString("password", pass);

                    if (users != null) {
                        if (!users.checkLogin(userName, pass)) {
                            //说明users不为空 且 当前没有登陆
                            users.register(userName, pass);
                            ToastUtil.showToast("注册成功，1秒后返回主界面");
                            finish();
                        }
                    }

                    //说明users为null
                    finish();  //直接结束当前活动

                } else {
                    ToastUtil.showToast("密码的长度不能低于六位");
                    et_pass.setText("");
                    et_confirm_pass.setText("");
                }
            } else {
                ToastUtil.showToast("两次输入的密码不同");
                et_confirm_pass.setText("");
                et_pass.setText("");
            }

        } else {
            //三者中有为空的部分
            ToastUtil.showToast("信息没有填写完全，请检查后补充完整");
        }

    }

    @Override
    protected void onRetry() {

    }
}
