package com.example.root.curriculum.activity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.root.curriculum.App;
import com.example.root.curriculum.R;
import com.example.root.curriculum.util.IconFontTextView;
import com.example.root.curriculum.util.ToastUtil;


/**
 * 关于我们部分的活动
 */
public class AboutMyTeamActivity extends AppCompatActivity {

    private IconFontTextView ift_return;

    private MediaPlayer player;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_my);

        //打算开启当前活动的时候播放音乐，退出的时候取消
        try {

            player = MediaPlayer.create(App.getInstance(), R.raw.sky_city);
            player.prepare();
            player.setLooping(true);    //开启循环播放

        } catch (Exception e) {
            e.printStackTrace();
        }
        player.start();
        ToastUtil.showToast("开始播放音乐");


        ift_return = findViewById(R.id.about_return);
        ift_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出当前活动
                if (player.isPlaying() || player != null) {
                    player.stop();   //释放资源
                }
                player.release();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        player.release(); //释放资源
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        player.release();
        super.onBackPressed();
    }
}
