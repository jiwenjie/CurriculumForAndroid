package com.example.root.curriculum.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.activity.DisplayNewDetailActivity;
import com.example.root.curriculum.bean.NewsBean;
import com.example.root.curriculum.util.ToastUtil;

import java.util.List;

/**
 * 新闻类的适配器
 */
public class NewsAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public NewsAdapter(List<NewsBean> list) {
        super(R.layout.item_new_layout, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NewsBean item) {
        helper.setText(R.id.item_text_id, item.getTitle());
        helper.setText(R.id.item_text_source_id, item.getSource());

        ImageView imageView = helper.getView(R.id.img_title);   //获取实例

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        Glide.with(App.getInstance())
                .load(item.getImgsrc())
                .override(120, 80)
                .placeholder(R.color.alpha_20_black)
                .error(R.drawable.ic_avatar)
                .into(imageView);

        //点击事件的实现
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(item.getDocid());
                DisplayNewDetailActivity.runActivity(App.getInstance(), item);
            }
        });

    }

}
