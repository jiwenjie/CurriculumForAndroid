package com.example.root.curriculum.model;

import com.example.root.curriculum.api.RetrofitManager;
import com.example.root.curriculum.base.GankBeautyResult;
import com.example.root.curriculum.bean.ArticleData;
import com.example.root.curriculum.bean.WelfarePhotoInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc: 首页精选 model
 */
public class Model {

    public static Observable<ArticleData> getArtData(int page) {
        return RetrofitManager.getApiService().getArticle(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    //获取图片部分的model
    public static Observable<GankBeautyResult> getPicture(int page) {
        return RetrofitManager.getGankApi().getBeauties(page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
