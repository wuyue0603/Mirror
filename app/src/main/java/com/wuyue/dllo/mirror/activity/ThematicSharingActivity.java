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
import android.widget.Button;
import android.widget.Toast;

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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/8.
 */
public class ThematicSharingActivity extends AppCompatActivity {
    private SimpleDraweeView main_iv;
    private List<Fragment> data;
    private ThematicSharingSecondAdapter adapter;
    private VerticalViewPager viewPager;
    private Handler handler;
    private ThameticSharingEntity bean;
    private ArrayList<String> list;
    private ArrayList<String> title;
    private ArrayList<String> subTitle;
    private ArrayList<String> picture;
    private Button sharedBtn;
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
        sharedBtn = (Button) findViewById(R.id.theme_share_btn);
        //设置分享按钮的监听事件
        sharedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSDK.initSDK(ThematicSharingActivity.this);
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
                //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
                // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
                oks.setTitle(ThematicSharingActivity.this.getString(R.string.share));
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl("http://sharesdk.cn");
                // text是分享文本，所有平台都需要这个字段
                oks.setText("我是分享文本");
                // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl("http://sharesdk.cn");
                // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                oks.setComment("我是测试评论文本");
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(ThematicSharingActivity.this.getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl("http://sharesdk.cn");
                // 启动分享GUI
                oks.show(ThematicSharingActivity.this);
            }
        });
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
                for (int i = 0; i < bean.getData().getStory_data().getText_array().size(); i++) {
                    data.add(new ThematicSharingSecondFragment(list.get(i), title.get(i), subTitle.get(i)));
                }
                adapter = new ThematicSharingSecondAdapter(ThematicSharingActivity.this.getSupportFragmentManager(), data, ThematicSharingActivity.this);
                viewPager.setAdapter(adapter);
                return false;
            }
        });
    }

    public static void EnterSecondThme(Context context, ThematicSharingAdapter adapter1) {
        Intent intent = new Intent(context, ThematicSharingActivity.class);
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
                    main_iv.setImageURI(Uri.parse(picture.get(viewPager.getCurrentItem())));
                }
            }
        });
    }
}
