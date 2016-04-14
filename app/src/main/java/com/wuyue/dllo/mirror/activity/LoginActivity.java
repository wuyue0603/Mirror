package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView closeIv;
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

        telEt = bindView(R.id.login_tel_et);
        passwordEt = bindView(R.id.login_password_et);
        relativeLayout = bindView(R.id.login_relative);
        if (passwordEt.getText().toString() != null) {
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
            Log.i("6666666666", "8888");
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    LoginEntity entity = new Gson().fromJson(msg.obj.toString(), LoginEntity.class);
                    token = entity.getData().getToken();
                    Log.d("tttt", token);
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
                    Log.d("body", body);
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
            finish();

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
        }
    }

}
