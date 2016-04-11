package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.adapter.AlbumAdapter;
import com.wuyue.dllo.mirror.entity.AlbumEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/9.
 */
public class AlbumActivity extends AppCompatActivity{
    private Handler handler;
    private AlbumAdapter adapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        Intent intent = getIntent();
        final int pos = intent.getIntExtra("pos",0);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                AlbumEntity entity = new Gson().fromJson(msg.obj.toString(), AlbumEntity.class);
                Log.d("ccccccc1", entity.toString());
                listView = (ListView) findViewById(R.id.listView);
                adapter = new AlbumAdapter(entity, AlbumActivity.this,pos);
                listView.setAdapter(adapter);
                return false;
            }
        });



        init();
    }
    private void init() {
        String url = "http://api101.test.mirroreye.cn/index.php/products/goods_list";
        OkHttpUtils.post().url(url).addParams("device_type", "2").addParams("version", "1.0.1").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
                Log.d("ccccccc", body.toString());
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
    }
}
