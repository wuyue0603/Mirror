package com.wuyue.dllo.mirror.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.DirectionalViewPager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.adapter.ShowMenuAdapter;
import com.wuyue.dllo.mirror.entity.MenuEntity;
import com.wuyue.dllo.mirror.entity.ShowMenu;
import com.wuyue.dllo.mirror.entity.GoodsListEntity;
import com.wuyue.dllo.mirror.fragment.PlaceholderFragment;
import com.wuyue.dllo.mirror.fragment.ThematicSharingFragment;
import com.wuyue.dllo.mirror.myinterface.SetTitle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SetTitle {
    private SectionsPagerAdapter mSectionsPagerAdapter;


    private DirectionalViewPager mViewPager;
    private ArrayList<Fragment> datas;
    private static final String ARG_SECTION_NUMBER = "section_number";
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            datas = new ArrayList<>();

            MenuEntity entity = new Gson().fromJson(msg.obj.toString(), MenuEntity.class);
            int i = 0;
            for (i = 0; i < entity.getData().getList().size(); i++) {
                datas.add(new PlaceholderFragment(i, entity.getData().getList().get(i).getTitle()));

            }

            datas.add(new ThematicSharingFragment(i + 1));
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), datas);

            mViewPager = (DirectionalViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
            Intent intent = getIntent();
            int ss = intent.getIntExtra("position", 0);
            mViewPager.setCurrentItem(ss);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

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

}

