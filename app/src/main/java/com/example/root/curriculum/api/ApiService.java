package com.example.root.curriculum.api;

import com.example.root.curriculum.bean.ArticleData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

//获取实体数据的方法
public interface ApiService {

    @GET("/article/list/{page}/json")
    Observable<ArticleData> getArticle(@Path("page") int page);
    //Observable<ArticleData> getArticle();

}
