package com.wuyue.dllo.mirror.activity;

import android.view.View;
import android.widget.ImageView;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/30.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView addIv;
    @Override
    protected void initData() {

    }

    @Override
    protected void init() {

        addIv = bindView(R.id.add_address_close);
        addIv.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addaddress;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_close:
                finish();
                break;
        }
    }
}
