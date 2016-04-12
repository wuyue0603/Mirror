package com.wuyue.dllo.mirror.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;

import com.wuyue.dllo.mirror.adapter.ThematicSharingAdapter;
import com.wuyue.dllo.mirror.adapter.ThematicSharingSecondAdapter;
import com.wuyue.dllo.mirror.entity.ThameticSharingEntity;

import com.wuyue.dllo.mirror.fragment.ThematicSharingSecondFragment;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/8.
 */
public class ThematicSharingActivity extends AppCompatActivity{
    private SimpleDraweeView main_iv;
    private List<Fragment> data;
    private ThematicSharingSecondAdapter adapter;
    private VerticalViewPager viewPager;
    //    private int[] back = {R.mipmap.a, R.mipmap.b, R.mipmap.c,
// R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
// R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
// R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private Handler handler;
    private ThameticSharingEntity bean;
    private ArrayList<String> list;
    private ArrayList<String> title;
    private ArrayList<String> subTitle;
    private ArrayList<String> picture;
    private static ThematicSharingAdapter thematicSharingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_thematicsharing);
        setTitle("");
        initViewPager();
        data = new ArrayList<>();
        main_iv = (SimpleDraweeView) findViewById(R.id.main_iv);
//        main_iv.setBackgroundResource(back[0]);


        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                bean = new ThameticSharingEntity();
                bean = new Gson().fromJson(msg.obj.toString(), ThameticSharingEntity.class);
                list = new ArrayList<>();
                title = new ArrayList<>();
                subTitle = new ArrayList<>();
                picture = new ArrayList<>();
                for (int i = 0; i < bean.getData().getStory_data().getText_array().size(); i++) {
                    String s = bean.getData().getStory_data().getText_array().get(i).getSmallTitle();
                    list.add(s);
                    String t = bean.getData().getStory_data().getText_array().get(i).getTitle();
                    title.add(t);
                    String sub = bean.getData().getStory_data().getText_array().get(i).getSubTitle();
                    subTitle.add(sub);
                }
                for (int i = 0; i < bean.getData().getStory_data().getImg_array().size(); i++) {
                    String img = bean.getData().getStory_data().getImg_array().get(i);
                    picture.add(img);

                }
                main_iv.setImageURI(Uri.parse(picture.get(0)));
                Log.d("sssss", picture.get(0));
                for (int i = 0; i < bean.getData().getStory_data().getText_array().size(); i++) {
                    data.add(new ThematicSharingSecondFragment(list.get(i), title.get(i), subTitle.get(i)));
                }
                adapter = new ThematicSharingSecondAdapter(ThematicSharingActivity.this.getSupportFragmentManager(), data, ThematicSharingActivity.this);
                viewPager.setAdapter(adapter);
                return false;
            }
        });


    }
    public static void EnterSecondThme(Context context, ThematicSharingAdapter adapter1){
        Intent intent = new Intent(context,ThematicSharingActivity.class);
        context.startActivity(intent);
        thematicSharingAdapter = adapter1;

    }

    private void initViewPager() {
        viewPager =
                (VerticalViewPager) findViewById(R.id.vertical_viewpager);


        String url = "http://api101.test.mirroreye.cn/" + "index.php/story/info";
        OkHttpUtils.post().url(url).addParams("device_type", "2").addParams("story_id", "2").build().execute(new Callback() {
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

        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAddStatesFromChildren(true);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


                if (state == 2) {
//                    main_iv.setBackgroundResource(back[viewPager.getCurrentItem()]);
                    main_iv.setImageURI(Uri.parse(picture.get(viewPager.getCurrentItem())));
                }
            }


        });
    }
}
