package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;

/**
 * Created by dllo on 16/3/31.
 */
public class OrderContentActivity extends BaseActivity {
    private SimpleDraweeView img;
    private TextView brandTv, priceTv,writeTv;
    private ImageView closeIv;


    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        brandTv = bindView(R.id.things_brand);
        priceTv = bindView(R.id.things_price);
        closeIv = bindView(R.id.login_close);

        img = bindView(R.id.things_iv);
        Intent intent = getIntent();
        String brand = intent.getStringExtra("brand");
        String price = intent.getStringExtra("price");
        String i = intent.getStringExtra("img");
        brandTv.setText(brand);
        priceTv.setText("¥" + price);
        img.setImageURI(Uri.parse(i));

        writeTv = bindView(R.id.write_address);
        writeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OrderContentActivity.this,AddAddressActivity.class);
                startActivity(intent1);
            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ordercontent;
    }
}
