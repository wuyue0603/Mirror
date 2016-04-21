package com.wuyue.dllo.mirror.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.WelcomeEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends Activity {
    private Handler handler;
    private SimpleDraweeView draweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //绑定布局
        setContentView(R.layout.activity_welcome);
        draweeView = (SimpleDraweeView) findViewById(R.id.wel_iv);

        //handler回调,到主线程
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                WelcomeEntity entity = gson.fromJson(msg.obj.toString(), WelcomeEntity.class);
                draweeView.setImageURI(Uri.parse(entity.getImg()));
                return false;
            }
        });
        //匿名一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //欢迎页睡眠4秒钟
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //创建跳转页面意图,从欢迎页面跳转到MainActivity页面
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                //设置欢迎页跳转到MainActivity后点击返回直接退出程序,而不是返回欢迎页
                finish();
            }
        }).start();
        showImg();
    }

    //拉取欢迎页网络图片
    private void showImg() {
        String url = "http://api101.test.mirroreye.cn/" + "index.php/index/started_img";
        OkHttpUtils.post().url(url).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                handler.sendMessage(message);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

            }
        });
    }
}
