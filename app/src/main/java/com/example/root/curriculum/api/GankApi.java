package com.example.root.curriculum.api;

import com.example.root.curriculum.base.GankBeautyResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

//有关福利部分的api参数
public interface GankApi {

    @GET("data/福利/10/{page}")
    Observable<GankBeautyResult> getBeauties(@Path("page") int page);

}
