package com.wuyue.dllo.mirror.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.DirectionalViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.MenuEntity;
import com.wuyue.dllo.mirror.fragment.PlaceholderFragment;
import com.wuyue.dllo.mirror.fragment.ShopingCarFragment;
import com.wuyue.dllo.mirror.fragment.ThematicSharingFragment;
import com.wuyue.dllo.mirror.myinterface.SetTitle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SetTitle {
    private SectionsPagerAdapter mSectionsPagerAdapter;//纵向滑动viewPager的适配器
    private DirectionalViewPager mViewPager;//纵向滑动的viewpager
    private ArrayList<Fragment> datas;//添加Fragment到集合
    private ImageView mainIv;
    private TextView loginTv;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private MainActivityBroadCast broadCast = new MainActivityBroadCast();

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            datas = new ArrayList<>();
            MenuEntity entity = new Gson().fromJson(msg.obj.toString(), MenuEntity.class);
            int i = 0;
            //复用的fragment,有多少个标题就复用多少个fragmt 拉出了4个标题 只复用了3个fragment所以size -1
            for (i = 0; i < entity.getData().getList().size() - 1; i++) {
                datas.add(new PlaceholderFragment(i, entity.getData().getList().get(i).getTitle()));
            }
            //额外写死的fragmet,购物车 和 专题分享()
            datas.add(new ShopingCarFragment(i));
            datas.add(new ThematicSharingFragment(i + 1));
            //初始化纵向viewPager setOrientation方法 设置方向
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), datas);
            mViewPager = (DirectionalViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
            //从popwindow中穿过了position 对应相应的fragment
            Intent intent = getIntent();
            int ss = intent.getIntExtra("position", 0);
            mViewPager.setCurrentItem(ss);
            return false;
        }
    });
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Mirror 图标 点击会跳动
        mainIv = (ImageView) findViewById(R.id.main_iv);
        mainIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.myanim);
                mainIv.startAnimation(animation);
            }
        });
        //登录 图标 点击会跳到登录界面
        loginTv = (TextView) findViewById(R.id.login_tv);
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        String url = "http://api101.test.mirroreye.cn/index.php/index/menu_list";

        OkHttpUtils.post().url(url).build().execute(new Callback() {
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
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.wuyue.dllo.mirror.LoginBroadcast");
        registerReceiver(broadCast, intentFilter);
    }


    //通过接口回调来 对应标题和相应的fragment
    @Override
    public void setTitle(String title, int position) {
        mViewPager.setCurrentItem(position);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentDatas;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> data) {
            super(fm);
            this.fragmentDatas = data;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentDatas.get(position);
        }

        public int getCount() {
            return fragmentDatas.size();
        }
    }

    class MainActivityBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            loginTv.setText("購物車");
            loginTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mViewPager.setCurrentItem(3);
                    Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.myanim);
                    loginTv.startAnimation(animation);
                }
            });


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCast);
    }
}

