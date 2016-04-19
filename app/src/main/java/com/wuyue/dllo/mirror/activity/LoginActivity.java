package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.LoginEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView closeIv, sinaLogin;
    private RelativeLayout createNumber;
    private Handler handler;
    private EditText telEt, passwordEt;
    private RelativeLayout relativeLayout;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        closeIv = bindView(R.id.login_close);
        closeIv.setOnClickListener(this);
        createNumber = bindView(R.id.create_number_relative);
        sinaLogin = bindView(R.id.sina_login);
        sinaLogin.setOnClickListener(this);
        telEt = bindView(R.id.login_tel_et);
        passwordEt = bindView(R.id.login_password_et);
        relativeLayout = bindView(R.id.login_relative);
        if (passwordEt.getText().toString() != null && passwordEt.length() > 0) {
            relativeLayout.setBackgroundResource(R.mipmap.login_press);
        }
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                okhttp();
            }
        });
        createNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountNumberActivity.class);
                startActivity(intent);
            }
        });
    }

    public void okhttp() {
        if (telEt != null && telEt.length() > 0 && passwordEt != null && passwordEt.length() > 0) {
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    LoginEntity entity = new Gson().fromJson(msg.obj.toString(), LoginEntity.class);
                    token = entity.getData().getToken();
                    Intent intent = new Intent(LoginActivity.this, AddAddressActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                    return false;
                }
            });

            String url = "http://api101.test.mirroreye.cn/index.php/user/login";
            OkHttpUtils.post().url(url).addParams("phone_number", telEt.getText().toString()).addParams("password", passwordEt.getText().toString())
                    .build().execute(new Callback() {
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
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_close:
                finish();
                break;
            case R.id.sina_login:
                ShareSDK.initSDK(this);
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (platform.isAuthValid()) {
                    platform.removeAccount();
                }
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });

                platform.SSOSetting(false);
                platform.showUser(null);
                break;
        }
    }
}
