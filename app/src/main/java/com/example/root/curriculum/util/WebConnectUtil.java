package com.example.root.curriculum.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 使用 okhttp3 框架链接网络获取数据
 */
public class WebConnectUtil {
    //提出网络请求，获取所得的数据(返回数据)
    public static void sendRequestWidthOkHttp(String url, okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);      //异步
    }

}