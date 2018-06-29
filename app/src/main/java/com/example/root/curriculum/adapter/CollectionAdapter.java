package com.example.root.curriculum.adapter;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.activity.WebViewActivity;
import com.example.root.curriculum.bean.ArticleData;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<ArticleData.DataBean.DatasBean, BaseViewHolder> {

    public CollectionAdapter(List<ArticleData.DataBean.DatasBean> list) {
        super(R.layout.item_home_part, list);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ArticleData.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());
        final TextView tvCollect = helper.getView(R.id.tv_collect);
        //显示已经收藏
        tvCollect.setText(R.string.ic_collect_sel);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(App.getInstance(), item.getTitle(), item.getLink());
            }
        });
    }
}
