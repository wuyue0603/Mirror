package com.wuyue.dllo.mirror.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.wuyue.dllo.mirror.R;

/**
 * Created by dllo on 16/3/29.
 */
public class WelcomeActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定布局
        setContentView(R.layout.activity_welcome);
<<<<<<< HEAD

        


=======
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
                //创建跳转页面意图,从欢迎页面跳转到引导页面
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                //设置欢迎页跳转到MainActivity后点击返回直接退出程序,而不是返回欢迎页
                finish();
            }
        }).start();
>>>>>>> 5fcdb01faf75523904e9a07b63bb265fa05354d9
    }
}
