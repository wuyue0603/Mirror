package com.wuyue.dllo.mirror.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    TextView titleTv, loginTv;
    PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        titleTv = bindView(R.id.tv);
        titleTv.setOnClickListener(this);
        loginTv = bindView(R.id.main_login_tv);
        loginTv.setOnClickListener(this);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private void initView() {
        popupWindow = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.popwindow, null);
        popupWindow.setContentView(view);


        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);


        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();

//                    popupWindow.update();
//                    popupWindow.dismiss();
                }
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                initView();
                break;
            case R.id.main_login_tv:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

        }
    }


}

