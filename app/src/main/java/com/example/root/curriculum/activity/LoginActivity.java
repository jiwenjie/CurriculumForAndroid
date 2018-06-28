package com.example.root.curriculum.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.root.curriculum.Constants;
import com.example.root.curriculum.R;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.bean.Users;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.ToastUtil;

import butterknife.BindView;

/**
 * 有关登陆界面的活动布局
 */
public class LoginActivity extends BaseActivity<IBasePresenter> {

    @BindView(R.id.returnOne) IconFontTextView returnOne;
    @BindView(R.id.username) EditText et_userName;
    @BindView(R.id.password) EditText et_password;
    @BindView(R.id.confirm_password) EditText et_confirm;
    @BindView(R.id.login) Button btn_login;
    @BindView(R.id.register) TextView tv_register;
    @BindView(R.id.third) TextInputLayout layout;
    @BindView(R.id.login_register) Button btn_register;
    @BindView(R.id.title) TextView tv_title;

    private String userName;
    private String userPass;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        clickThing();
    }

    private void clickThing() {

        checkIsSalfe();

        //注册的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把几个状态值设置一下
                layout.setVisibility(View.VISIBLE);
                btn_register.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                tv_register.setVisibility(View.GONE);
                et_confirm.setVisibility(View.VISIBLE);
                tv_title.setText("注册");

            }
        });

        returnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出返回上一级
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("注册部分");
                userName = et_userName.getText().toString();
                userPass = et_password.getText().toString();
                String confirm = et_confirm.getText().toString();

                if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userPass) || !TextUtils.isEmpty(confirm)) {
                    ToastUtil.showToast("输入不合法，请重新输入");
                } else {
                    if (userPass.equals(confirm)) {
                        //说明密码相等
                        Intent intent = getIntent();
                        Bundle bun = new Bundle();
                        bun.putString("username", userName);
                        bun.putString("password", userPass);
                        intent.putExtras(bun);
                        //Constants.IS_LOGIN = true;  //把登陆状态设置为 true
                        intent.putExtras(bun);
                        LoginActivity.this.setResult(3, intent);
                        LoginActivity.this.finish();
                        return;
                    }
                }
            }
        });
    }

    private void checkIsSalfe() {
        userName = et_userName.getText().toString();
        userPass = et_password.getText().toString();
        if (!TextUtils.isEmpty(userName) || !TextUtils.isEmpty(userPass)) {

            //说明输入都不为空
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    Bundle bun = new Bundle();
                    bun.putString("username", userName);
                    bun.putString("password", userPass);
                    intent.putExtras(bun);
                    Constants.IS_LOGIN = true;  //把登陆状态设置为 true
                    LoginActivity.this.setResult(1, intent);
                    LoginActivity.this.finish();
                }
            });

        } else {
            //输入有为空的部分（不合法的输入）
            ToastUtil.showToast("用户名或密码不能为空");
        }
    }

    @Override
    protected void onRetry() {

    }
}
