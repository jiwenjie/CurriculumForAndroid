package com.example.root.curriculum.bean;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;

import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.util.JsonThread;
import com.example.root.curriculum.util.NetWorkUtil;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 用户的实体类
 */
public class Users implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;
    private String username;
    private String password;
    private SharedPreferences share;
    private Resources resources;
    private Handler handler;
    private JsonThread thread;

    public Users(Resources resources, SharedPreferences share, Handler handler, JsonThread thread) {
        password = username = "";
        this.share = share;
        this.handler = handler;
        this.resources = resources;
        this.thread = thread;
    }

    public Boolean login(String username, String password) {
        return login(username, encrypt(password), false);
    }

    public Boolean login(String username, String password, Boolean register) {
        this.username = username;
        this.password = password;
        //http://192.168.1.111:3000/json
//        String urlStr = resources.getString(R.string.server_url) + "?username=" + username + "&password=" + this.password;
        String urlStr = "http://" + NetWorkUtil.getIP(App.getInstance()) + "/json" + "?username=" + username + "&password=" + this.password;
        if (register) {
            urlStr += "&register";
        }
        thread.pushUrlStr(urlStr);
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username", username);
        editor.putString("password", this.password);
        editor.apply();
        return true;
    }

    public Boolean register(String username, String password) {
        return login(username, encrypt(password), true);
    }

    public void logout() {
        password = username = "";
        SharedPreferences.Editor editor = share.edit();
        editor.putString("username", "");
        editor.putString("password", "");
        editor.apply();
    }

    public Boolean checkLogin(String username, String password) {
        if (username.equals("") || password.equals("")) {
            return false;
        }
        login(username, password, false);
        return true;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SharedPreferences getShare() {
        return share;
    }

    public void setShare(SharedPreferences share) {
        this.share = share;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public JsonThread getThread() {
        return thread;
    }

    public void setThread(JsonThread thread) {
        this.thread = thread;
    }

    //加密部分
    private String encrypt(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte data[] = md.digest(password.getBytes());
            StringBuilder strBuild =new StringBuilder();
            for(byte b:data){
                strBuild.append(Integer.toHexString(b&0xff));
            }
            return strBuild.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
