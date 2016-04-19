package com.wuyue.dllo.mirror.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wuyue.dllo.mirror.R;

import com.wuyue.dllo.mirror.adapter.AlbumAdapter;
import com.wuyue.dllo.mirror.adapter.DownListViewAdapter;
import com.wuyue.dllo.mirror.adapter.UpListViewAdapter;
import com.wuyue.dllo.mirror.entity.AllGoodsListEntity;
import com.wuyue.dllo.mirror.entity.Costant;

import android.support.v4.view.LinkageListView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

/**
 * Created by dllo on 16/3/30.
 */
public class HomepageContentActivity extends Activity implements View.OnClickListener {
    private LinkageListView listView;
    private AllGoodsListEntity allGoodsListEntity1;
    private Handler handler;
    private static int position, pos;
    private SimpleDraweeView background;
    private String url;
    private Button albumBtn;
    private ImageView imageView;
    private static AlbumAdapter albumAdapter;
    private ImageView buyIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagecontent);
        listView = (LinkageListView) findViewById(R.id.detail_listview);
        background = (SimpleDraweeView) findViewById(R.id.goodsdetail_background);
        albumBtn = (Button) findViewById(R.id.album_btn);
        imageView = (ImageView) findViewById(R.id.return_iv);
        imageView.setOnClickListener(this);
        albumBtn.setOnClickListener(this);
        buyIv = (ImageView) findViewById(R.id.buy_iv);
        buyIv.setOnClickListener(this);

        allGoodsListEntity1 = new AllGoodsListEntity();
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        pos = intent.getIntExtra("pos", 0);
        post();
        addData();
    }

    private void addData() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Gson gson = new Gson();
                allGoodsListEntity1 = gson.fromJson(msg.obj.toString(), AllGoodsListEntity.class);
                listView.setAdapter(new UpListViewAdapter(allGoodsListEntity1, getApplication(), pos), new DownListViewAdapter(allGoodsListEntity1, getApplication(), pos));
                listView.setLinkageSpeed(1.2f);
                background.setImageURI(Uri.parse(allGoodsListEntity1.getData().getList().get(pos).getGoods_img()));
                return false;
            }
        });
    }

    private void post() {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormEncodingBuilder builder = new FormEncodingBuilder();
        builder.add("device_type", "1");
        url = Costant.TEXT_HEAD + Costant.GOODS_LIST;
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                Message message = new Message();
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album_btn:
                Intent intent = new Intent(HomepageContentActivity.this, AlbumActivity.class);
                intent.putExtra("pos", pos);
                startActivity(intent);
                break;
            case R.id.return_iv:
                finish();
                break;
            case R.id.buy_iv:
                Intent intent1 = new Intent(HomepageContentActivity.this, OrderContentActivity.class);
                intent1.putExtra("pos", pos);
                intent1.putExtra("id", allGoodsListEntity1.getData().getList().get(pos).getGoods_id());
                intent1.putExtra("price", allGoodsListEntity1.getData().getList().get(pos).getGoods_price());
                startActivity(intent1);
                break;
        }
    }
}


