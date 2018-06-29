package com.example.root.curriculum.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.activity.DisplayActivity;
import com.example.root.curriculum.base.GankBeautyResult;
import com.example.root.curriculum.bean.WelfarePhotoInfo;
import com.example.root.curriculum.util.ToastUtil;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Search 部分的 Adapter
 */
public class SearchAdapter extends BaseQuickAdapter<WelfarePhotoInfo, BaseViewHolder> {

    public SearchAdapter(List<WelfarePhotoInfo> result) {
        super(R.layout.item_picture, result);
    }

    @Override
    protected void convert(BaseViewHolder helper, final WelfarePhotoInfo item) {
        final ImageView ivPhoto = helper.getView(R.id.iv_picture_photo);
        helper.setText(R.id.tv_title, item.getCreatedAt());

        Glide.with(mContext)
                .load(item.getUrl())
                .error(R.drawable.img_splash)
                .into(ivPhoto);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("你点击了图片" + item.getDesc());
                DisplayActivity.runDisplay(App.getInstance(), item.getUrl());
            }
        });

    }
}
