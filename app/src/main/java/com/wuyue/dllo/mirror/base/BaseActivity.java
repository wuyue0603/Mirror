package com.wuyue.dllo.mirror.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wuyue.dllo.mirror.R;

/**
 * Created by dllo on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init();
        initData();
    }

    protected abstract void initData();

    protected abstract void init();

    protected abstract int getLayout();

    protected <T extends View> T bindView(int id){
        return (T)findViewById(id);
    }

}
