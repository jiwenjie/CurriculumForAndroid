package com.example.root.curriculum.bean;


import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * 用户相关操作信息的实体类
 */
public class UserMessage extends LitePalSupport {

    private String userName;
    private List<ArticleData.DataBean.DatasBean> list;  //收藏的文章

    private ArticleData.DataBean.DatasBean bean;

    public UserMessage() {
        super();
    }

    public UserMessage(String name, List<ArticleData.DataBean.DatasBean> beans) {
        this.userName = name;
        this.list = beans;
    }

    public UserMessage(String name, ArticleData.DataBean.DatasBean beans) {
        this.userName = name;
        this.bean = beans;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ArticleData.DataBean.DatasBean> getList() {
        return list;
    }

    public void setList(List<ArticleData.DataBean.DatasBean> list) {
        this.list = list;
    }

    public ArticleData.DataBean.DatasBean getBean() {
        return bean;
    }

    public void setBean(ArticleData.DataBean.DatasBean bean) {
        this.bean = bean;
    }
}
