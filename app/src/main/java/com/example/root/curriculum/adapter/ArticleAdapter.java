package com.example.root.curriculum.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.root.curriculum.App;
import com.example.root.curriculum.Constants;
import com.example.root.curriculum.R;
import com.example.root.curriculum.activity.WebViewActivity;
import com.example.root.curriculum.bean.ArticleData;
import com.example.root.curriculum.util.ToastUtil;

import java.util.List;

/**
 * 首页数据适配器
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleData.DataBean.DatasBean, BaseViewHolder> {

    public ArticleAdapter(List<ArticleData.DataBean.DatasBean> classList) {
        super(R.layout.item_home_part, classList);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ArticleData.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());

        //获取收藏按钮的控件实例
        final TextView tvCollect = helper.getView(R.id.tv_collect);
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否登陆，如果登陆则出现收藏按钮，否则给出提示询问是否现在登陆
                if (Constants.IS_LOGIN) {   //已经登陆
                    //显示已经收藏
                    tvCollect.setText(R.string.ic_collect_sel);
                    //需要把数据放入数据库，方便在我的部分查看


                } else {
                    ToastUtil.showToast("对不起，您还没有登陆，请先登陆之后在操作");
                }
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.runActivity(App.getInstance(), item.getTitle(), item.getLink());
            }
        });

    }
}
