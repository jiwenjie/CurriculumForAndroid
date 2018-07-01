package com.example.root.curriculum.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.bean.NewsBean;
import com.example.root.curriculum.bean.NewsDetailBean;
import com.example.root.curriculum.model.Model;
import com.example.root.curriculum.util.ToastUtil;
import com.example.root.curriculum.util.Urls;
import com.example.root.curriculum.util.WebConnectUtil;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class DisplayNewDetailActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private String bean_id;
    private LinearLayout ll_error;
    private NewsBean bean;
    private TextView tv_detail;
    private ImageView img_display;
    private ScrollView sc_content;

    public static void runActivity(Context context, NewsBean bean) {
        Intent intent = new Intent(context, DisplayNewDetailActivity.class);
        intent.putExtra("bean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_new_detail);
        bean = (NewsBean) getIntent().getSerializableExtra("bean");

        showLoading();

        ll_error = findViewById(R.id.display_error);    //错误显示界面
        tv_detail = findViewById(R.id.new_desc);
        img_display = findViewById(R.id.img_dis);
        sc_content = findViewById(R.id.content);

        if (bean != null) {
            disImage();
            getNewDetail();
        } else {
            //判断是否为空
            if_DATAISNULL();
        }
    }

    private void disImage() {
        Glide.with(this)
                .load(bean.getImgsrc())
                .override(App.W, 250)
                .placeholder(R.color.alpha_20_black)
                .error(R.drawable.ic_avatar)
                .into(img_display);
    }

    private void getNewDetail() {
        ToastUtil.showToast("获取数据部分");
        //得到数据
        WebConnectUtil.sendRequestWidthOkHttp("http://c.m.163.com/nc/article/" + bean.getDocid() +"/full.html", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtil.showToast("获取数据失败");
                if_DATAISNULL();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ToastUtil.showToast("获取数据成功");
                hideLoading();
                final String data = response.body().string();
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       parseDataWithJson(data);
                   }
               });
            }
        });
    }


    //显示加载框
    public void showLoading() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("正在加载...");
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    //隐藏加载框
    public void hideLoading() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    //原生方法解析 JSON 数据
    private void parseDataWithJson(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String first = object.getString(bean.getDocid());
            JSONObject seObj = new JSONObject(first);
            String body = seObj.getString("body");
            Log.v("Detail", body);
            tv_detail.setText(body);
            //ToastUtil.showToast(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void if_DATAISNULL() {
        hideLoading();
        tv_detail.setVisibility(View.GONE);
        img_display.setVisibility(View.GONE);
        sc_content.setVisibility(View.GONE);
        ll_error.setVisibility(View.VISIBLE);
    }

}
