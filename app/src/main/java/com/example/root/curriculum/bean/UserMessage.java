package com.example.root.curriculum.bean;


import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户相关操作信息的实体类
 */
public class UserMessage extends LitePalSupport implements Serializable {

    private String userName;
    private List<DatasBean> list = new ArrayList<>();

    public UserMessage() {
        super();
    }

    public UserMessage(String name, List<DatasBean> beans) {
        this.userName = name;
        this.list = beans;
    }

    public UserMessage(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<DatasBean> getList() {
        return list;
    }

    public void setList(List<DatasBean> list) {
        this.list = list;
    }

}
