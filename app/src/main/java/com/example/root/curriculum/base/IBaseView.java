package com.example.root.curriculum.base;

/**
 * 基础BaseView接口
 */
public interface IBaseView {

    /**
     * 显示加载动画
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 显示网络错误
     */
    void showNetError();

    /**
     * 完成刷新, 新增控制刷新
     */
//    void finishRefresh();

}
