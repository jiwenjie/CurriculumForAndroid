package com.example.root.curriculum;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class App extends Application {

    private static List<AppCompatActivity> activities = new LinkedList<>();

    public static List<?> images = new ArrayList<>();
    public static List<?> titles = new ArrayList<>();
    public static int H,W;

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();

        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList<>(list);
        List list1 = Arrays.asList(tips);
        titles = new ArrayList<>(list1);
        getScreen(context);

        //初始化 okgo 的方法
        //initOkGo();
    }

    public void getScreen(Context aty) {
        //获取当前手机宽高
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        H = dm.heightPixels;
        W = dm.widthPixels;
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 退出程序
     */
    public static void exitAllActivity() {
        for (AppCompatActivity activity : activities) {
            activity.finish();
        }
    }

}
