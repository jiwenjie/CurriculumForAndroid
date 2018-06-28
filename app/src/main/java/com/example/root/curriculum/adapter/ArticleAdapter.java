package com.example.root.curriculum.adapter;

import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.R;
import com.example.root.curriculum.bean.ArticleData;

import java.util.List;

/**
 * 首页数据适配器
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleData.DataBean.DatasBean, BaseViewHolder> {

    private ImageView mIv;

    public ArticleAdapter(List<ArticleData.DataBean.DatasBean> classList) {
        super(R.layout.item_home_part, classList);
    }

    @Override
    protected void convert(BaseViewHolder helper, ArticleData.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());

        //获取收藏按钮的控件实例
        TextView tvCollect = helper.getView(R.id.tv_collect);

//        tvCollect.set

    }
}
