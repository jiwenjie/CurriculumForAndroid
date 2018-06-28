package com.example.root.curriculum.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.multiple_status_view.MultipleStatusView;
import com.example.root.curriculum.R;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.NetWorkUtil;
import com.example.root.curriculum.util.ToastUtil;
import com.example.root.curriculum.util.Utils;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity<IBasePresenter> {

    public final static String URL = "url";
    public final static String TITLE = "title";

    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_share) IconFontTextView tv_share;
    @BindView(R.id.tv_return) IconFontTextView tv_return;
    @BindView(R.id.web_title) TextView tv_title;
    @BindView(R.id.web_multipleStatusView) MultipleStatusView web_multile;

    private Dialog mWebViewDialog;
    private CardView share_friend;
    private CardView copy_link;
    private CardView btn_cancel;
    private String url;

    public static void runActivity(Context context, String title, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initViews() {

        web_multile = mLayoutStatusView;
        web_multile.showContent();
        url = getIntent().getStringExtra(URL);
        String title = getIntent().getStringExtra(TITLE);
        mWebViewDialog = new Dialog(this, R.style.BottomDialog);
        pb.setMax(100);
        //设置标题
        tv_title.setText(title);
        webSetting();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });

        doOthers();
    }


    private void doOthers() {
        //正式加载
        webView.loadUrl(url);

        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
            }
        });

        if (!NetWorkUtil.isWifiConnected(this) && !NetWorkUtil.isNetWorkConnected(this)) {
            web_multile.showNoNetwork();
        }

        tv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //直接退出结束当前活动界面
            }
        });
    }

    //底部菜单栏，弹出一个选择，提供是选择对于网页的操作
    private void setDialog() {
        //加载dialog的style（不是布局），这行代码应在总初始化的位置
        //mCameraDialog = new Dialog(getContext(), R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.bottom_dialog, null);
        //初始化视图
        share_friend = root.findViewById(R.id.share_friend);
        copy_link = root.findViewById(R.id.copy_link);
        btn_cancel = root.findViewById(R.id.btn_cancel);
        //最终加载显示视图
        mWebViewDialog.setContentView(root);
        Window dialogWindow = mWebViewDialog.getWindow();
        //设置dialog的显示位置
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mWebViewDialog.show();
        BottomClick();
    }

    private void BottomClick() {
        //复制链接
        copy_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.copy(getBaseContext(), url);
                mWebViewDialog.dismiss();
                ToastUtil.showToast("链接复制成功");
            }
        });

        //分享好友
        share_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("你点击了分享好友");
                mWebViewDialog.dismiss();
            }
        });

        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("你点击了取消按钮");
                mWebViewDialog.dismiss();
            }
        });
    }

    private void webSetting() {
        WebSettings settings = webView.getSettings();

        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        // 缓存(cache)
        settings.setAppCacheEnabled(true);      // 默认值 false
        settings.setAppCachePath(this.getCacheDir().getAbsolutePath());
        // 存储(storage)
        settings.setDomStorageEnabled(true);    // 默认值 false
        settings.setDatabaseEnabled(true);      // 默认值 false

        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 是否支持Javascript，默认值false
        settings.setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 5.0以上允许加载http和https混合的页面(5.0以下默认允许，5.0+默认禁止)
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (NetWorkUtil.isNetWorkConnected(this) || NetWorkUtil.isWifiConnected(this)) {
            // 根据cache-control决定是否从网络上取数据
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            // 没网，离线加载，优先加载缓存(即使已经过期)
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress >= 100) {
                    pb.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onRetry() {
        ToastUtil.showToast("点击重试");
        doOthers();
    }
}
