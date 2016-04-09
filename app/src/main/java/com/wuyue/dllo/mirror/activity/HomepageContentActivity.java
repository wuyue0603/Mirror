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

import com.wuyue.dllo.mirror.adapter.DownListViewAdapter;
import com.wuyue.dllo.mirror.adapter.UpListViewAdapter;
import com.wuyue.dllo.mirror.entity.AllGoodsListEntity;
import com.wuyue.dllo.mirror.entity.Costant;

import android.support.v4.view.LinkageListView;
import android.util.Log;

import java.io.IOException;

/**
 * Created by dllo on 16/3/30.
 */
public class HomepageContentActivity extends Activity {
    private LinkageListView listView;
    private AllGoodsListEntity allGoodsListEntity1;
    private Handler handler;
    private int position, pos;
    private SimpleDraweeView background;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepagecontent);
        listView = (LinkageListView) findViewById(R.id.detail_listview);
        background = (SimpleDraweeView) findViewById(R.id.goodsdetail_background);
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
//                Log.d("XXXXXXXXXXXXXX", pos + "handleMessage: ");
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
}


