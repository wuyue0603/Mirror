package com.wuyue.dllo.mirror.net;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dllo on 16/3/31.
 */
public class OkHttpClientHelper {
    public void getPostDataFromNet(FormEncodingBuilder builder, String leg, final NetLisner netListener) {

        // 创建okHttpClient 对象
        OkHttpClient okHttpClient = new OkHttpClient();

        // 创建一个Request
        Request request = new Request.Builder().url("http://api101.test.mirroreye.cn/" + leg).post(builder.build()).build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                netListener.getFaild("失败了");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String s = response.body().string();
                netListener.getSucceed(s);
            }
        });
    }

}
