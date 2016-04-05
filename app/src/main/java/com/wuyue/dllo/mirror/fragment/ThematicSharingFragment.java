package com.wuyue.dllo.mirror.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.adapter.ThematicSharingAdapter;
import com.wuyue.dllo.mirror.entity.ThematicSharingEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/1.
 */
public class ThematicSharingFragment extends Fragment{
    private Handler handler;
    private SimpleDraweeView picIv;
    private TextView tv;
    private ThematicSharingAdapter thematicSharingAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thematic_sharing,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.thematic_sharing_recyclerview);
        picIv = (SimpleDraweeView) getView().findViewById(R.id.all_type_iv);
        tv = (TextView) getView().findViewById(R.id.title);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                ThematicSharingEntity entity = new Gson().fromJson(msg.obj.toString(),ThematicSharingEntity.class);
                //布局操控者
//                data = entity.getData();
                Log.i("55555",entity.toString());
                thematicSharingAdapter = new ThematicSharingAdapter(entity,getContext());
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(thematicSharingAdapter);
                return false;
            }
        });
        super.onActivityCreated(savedInstanceState);
        String url = "http://api101.test.mirroreye.cn/"+"index.php/story_test/info";
        OkHttpUtils.post().url(url).addParams("token","").addParams("device_type","2").addParams("story_id","2").build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message message = new Message();
                message.obj = body;
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
