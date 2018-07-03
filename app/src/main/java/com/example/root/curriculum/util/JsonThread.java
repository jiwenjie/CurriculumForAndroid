package com.example.root.curriculum.util;

import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class JsonThread implements Runnable {

    private ArrayList<String> urlList;
    private Handler handler;
    Resources resources;

    public JsonThread(Resources resources, Handler handler) {
        urlList = new ArrayList<>();
        this.handler = handler;
        this.resources = resources;
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    public void pushUrlStr(String urlStr) {
        urlList.add(urlStr);
    }

    @Override
    public void run() {
        //ToastUtil.showToast("进入线程的死循环");
        while (true) {
            Log.v("info", "url list size is " + urlList.size());
            if (urlList.size() == 0) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }
            try {
                String urlStr = urlList.get(0);
                urlList.remove(0);
                URL url = new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                    BufferedReader bfReader = new BufferedReader(reader);
                    StringBuilder strBuilder = new StringBuilder();
                    String line;
                    while ((line = bfReader.readLine()) != null) {
                        strBuilder.append(line);
                    }
                    bfReader.close();
                    reader.close();
                    conn.disconnect();
                    JSONObject obj = new JSONObject(strBuilder.toString());
//                    String event = obj.getString("event");
                    Log.v("info", "=====================================信息发送成功===================================");
                    Message msg = new Message();
                    msg.obj = obj;
                    msg.what = 1;
                    handler.sendMessage(msg);
                } else {
                    conn.disconnect();
                    throw new Exception(code + "");
                }
            } catch (Exception e) {
                Log.e("error on thread", e.toString());
            }
        }
    }
}
