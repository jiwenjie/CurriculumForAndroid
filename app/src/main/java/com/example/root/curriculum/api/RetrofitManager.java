package com.example.root.curriculum.api;

import com.example.root.curriculum.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static ApiService apiService;
    private static GankApi gankApi;
    private static OkHttpClient okHttpClient;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

//    private static File cacheFile = new File(App.getContext().getCacheDir(), "cache");
//    private static Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);   //50Mb 缓存的大小

    private static OkHttpClient getClient() {
        //可以设置请求过滤的水平,body,basic,headers
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor) //日志,所有的请求响应度看到
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                //.cache(cache)  //添加缓存
                .build();
    }

    public static ApiService getApiService() {

        if (apiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl("http://www.wanandroid.com/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();

            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    public static GankApi getGankApi() {

        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getClient())
                    .baseUrl("http://gank.io/api/")
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .addConverterFactory(gsonConverterFactory)
                    .build();

            gankApi = retrofit.create(GankApi.class);
        }

        return gankApi;
    }

}
