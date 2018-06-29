package com.example.root.curriculum.adapter;

import android.content.SharedPreferences;
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
import com.example.root.curriculum.bean.DatasBean;
import com.example.root.curriculum.bean.UserMessage;
import com.example.root.curriculum.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

/**
 * 首页数据适配器
 */
public class ArticleAdapter extends BaseQuickAdapter<ArticleData.DataBean.DatasBean, BaseViewHolder> {

    private UserMessage message = new UserMessage();
    private List<ArticleData.DataBean.DatasBean> dataList = new ArrayList<>();
    private DatasBean bean;
    // 初始化用户类
    SharedPreferences share = App.getInstance().getSharedPreferences(
            App.getInstance().getResources().getString(R.string.config_file_path), MODE_PRIVATE);

    public ArticleAdapter(List<ArticleData.DataBean.DatasBean> classList) {
        super(R.layout.item_home_part, classList);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ArticleData.DataBean.DatasBean item) {
        helper.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                .setText(R.id.tv_author, item.getAuthor())
                .setText(R.id.tv_time, item.getNiceDate())
                .setText(R.id.tv_type, item.getChapterName());

        String userName = share.getString("username", "Test");
        message.setUserName(userName);
        //获取收藏按钮的控件实例
        final TextView tvCollect = helper.getView(R.id.tv_collect);
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否登陆，如果登陆则出现收藏按钮，否则给出提示询问是否现在登陆
                if (Constants.IS_LOGIN) {   //已经登陆
                    //显示已经收藏
                    changeBean(item);
                    tvCollect.setText(R.string.ic_collect_sel);
                    //需要把数据放入列表中
                    message.getList().add(bean);
                    message.saveAsync();   //保存失败抛出异常
                    ToastUtil.showToast("录入保存成功");
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

    private void changeBean(ArticleData.DataBean.DatasBean item) {
        bean = new DatasBean();
        bean.setApkLink(item.getApkLink());
        bean.setAuthor(item.getAuthor());
        bean.setChapterId(item.getChapterId());
        bean.setChapterName(item.getChapterName());
        bean.setCollect(item.isCollect());
        bean.setCourseId(item.getCourseId());
        bean.setDesc(item.getDesc());
        bean.setEnvelopePic(item.getEnvelopePic());
        bean.setFresh(item.isFresh());
        bean.setId(item.getId());
        bean.setLink(item.getLink());
        bean.setNiceDate(item.getNiceDate());
        bean.setOrigin(item.getOrigin());
        bean.setProjectLink(item.getProjectLink());
        bean.setPublishTime(item.getPublishTime());
        bean.setSuperChapterId(item.getSuperChapterId());
        bean.setSuperChapterName(item.getSuperChapterName());
//        bean.setTags(item.getTags());
        bean.setTitle(item.getTitle());
        bean.setType(item.getType());
        bean.setVisible(item.getVisible());
        bean.setZan(item.getZan());
        bean.saveAsync();
    }
}
