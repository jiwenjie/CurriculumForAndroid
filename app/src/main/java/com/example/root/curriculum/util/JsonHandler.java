package com.example.root.curriculum.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.root.curriculum.R;
import com.example.root.curriculum.fragment.MineFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class JsonHandler extends Handler {

    private MineFragment mineFragment;

    public JsonHandler(MineFragment fragment) {
        this.mineFragment = fragment;
    }

    @Override
    public void handleMessage(Message msg) {

        super.handleMessage(msg);
        switch (msg.what){
            case 1:
                ToastUtil.showToast("JsonHandle Success");
                JSONObject obj = (JSONObject) msg.obj;
                handleJson(obj);
                break;
            default: break;

        }
    }

    private void handleJson(JSONObject obj){
        try {
            Boolean result = obj.getBoolean("result");
            String event = obj.getString("event");
            if (result == false) {
                Log.v("info", "json result false, event is " + event + ", message is " + obj.getString("msg"));
                mineFragment.onFailed(event);
                return;
            }
            if (event.equals("login")){
                mineFragment.onLoginSuccess();
            }
            if (event.equals("register")){
                mineFragment.onLoginSuccess();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
