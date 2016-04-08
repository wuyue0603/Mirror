package com.wuyue.dllo.mirror.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.FormEncodingBuilder;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.net.NetLisner;
import com.wuyue.dllo.mirror.net.OkHttpClientHelper;

/**
 * Created by dllo on 16/3/30.
 */
public class CreateAccountNumberActivity extends BaseActivity implements View.OnClickListener {
    private Button sendCodeBtn;
    private EditText codeEt;

    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        sendCodeBtn = bindView(R.id.send_code_btn);
        sendCodeBtn.setOnClickListener(this);
        codeEt = bindView(R.id.code_et);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_create_account_number;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_code_btn:
                OkHttpClientHelper okHttpClientHelper = new OkHttpClientHelper();
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("phone number", codeEt.getText().toString());
                okHttpClientHelper.getPostDataFromNet(builder, "index.php/user/send_code", new NetLisner() {
                    @Override
                    public void getSucceed(String s) {
                        Log.i("phone number", s);
                    }

                    @Override
                    public void getFaild(String o) {

                    }
                });
                break;
        }
    }
}
