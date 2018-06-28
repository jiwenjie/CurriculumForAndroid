package com.example.root.curriculum.api;

import com.example.root.curriculum.bean.ArticleData;

import io.reactivex.Observable;
import retrofit2.http.GET;

//获取实体数据的方法
public interface ApiService {

    @GET("/article/list/0/json")
//    Observable<ArticleData> article(@Query("page")int page);
    Observable<ArticleData> getArticle();

}
