package com.example.root.curriculum.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.root.curriculum.R;
import com.example.root.curriculum.base.BaseActivity;
import com.example.root.curriculum.base.IBasePresenter;
import com.youth.banner.loader.ImageLoader;

import butterknife.BindView;

/**
 * 用于展示图片的活动
 */
public class DisplayActivity extends BaseActivity<IBasePresenter> {

    @BindView(R.id.display_image)
    ImageView imageView;

    public static void runDisplay(Context context, String url) {
        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_display_picture;
    }

    @Override
    protected void initViews() {
        String url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder_banner)
                .error(R.drawable.img_splash)
                .into(imageView);
    }

    @Override
    protected void onRetry() {

    }
}
