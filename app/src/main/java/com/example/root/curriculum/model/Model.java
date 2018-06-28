package com.example.root.curriculum.model;

import com.example.root.curriculum.api.RetrofitManager;
import com.example.root.curriculum.bean.ArticleData;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * desc: 首页精选 model
 */
public class Model {

    public static Observable<ArticleData> getArtData() {
        return RetrofitManager.getApiService().getArticle()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
