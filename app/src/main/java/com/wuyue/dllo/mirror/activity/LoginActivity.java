package com.wuyue.dllo.mirror.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private ImageView closeIv;
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
