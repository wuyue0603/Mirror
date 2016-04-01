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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.ShowMenu;
import com.wuyue.dllo.mirror.entity.GoodsListEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private DirectionalViewPager mViewPager;
    private ArrayList<Fragment> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        datas = new ArrayList<>();
        datas.add(new PlaceholderFragment(0));
        datas.add(new PlaceholderFragment(1));
        datas.add(new PlaceholderFragment(2));
        datas.add(new PlaceholderFragment(3));
        datas.add(new PlaceholderFragment(4));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), datas);

        mViewPager = (DirectionalViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOrientation(DirectionalViewPager.VERTICAL);
        Intent intent = getIntent();
        int ss = intent.getIntExtra("position", 0);
        mViewPager.setCurrentItem(ss);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private int i;

        private RecyclerView recyclerView;
        private MyAdapter myAdapter;
        private ArrayList<String> data;
        private LinearLayout linearLayout;
        private ShowMenu showMenu;
        private Handler handler;

        public PlaceholderFragment() {
        }

        public PlaceholderFragment(int i) {
            this.i = i;
        }

        /**
         * Returns a new instance实例 of this fragment for the given section
         * number.
         */

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_all_type, container, false);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    GoodsListEntity entity = new Gson().fromJson(msg.obj.toString(), GoodsListEntity.class);
                    // 2.设置布局管理器

                    myAdapter = new MyAdapter(getActivity(), entity.getData());

                    LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                    manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(myAdapter);
                    return false;
                }
            });
            super.onActivityCreated(savedInstanceState);
            String url = "http://api101.test.mirroreye.cn/" + "index.php/products/goods_list";
            OkHttpUtils.post().url(url).addParams("token", "").addParams("device_type", "2")
                    .addParams("page", "").addParams("last_time", "").addParams("category_id", "")
                    .addParams("version", "1.0.0").build().execute(new Callback() {
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
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycleViewAllType);

            linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
            showMenu = new ShowMenu(getContext());
            data = new ArrayList<>();
            data.add("浏览所有分类");
            data.add("浏览平光眼镜");
            data.add("浏览太阳眼镜");
            data.add("专题分享");
            data.add("购物车");
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showMenu.showPopupWindow(v, data, i);
                }
            });
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        private Context context;
        //        private ArrayList<Integer> dataContent;
        private GoodsListEntity.DataEntity dataEntity;

        public MyAdapter(Context context, GoodsListEntity.DataEntity dataEntity1) {
            this.context = context;
            this.dataEntity = dataEntity1;
            notifyDataSetChanged();
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item, null);

            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.draweeView.setImageURI(Uri.parse(dataEntity.getList().get(position).getGoods_img()));
            holder.goodsNameTv.setText(dataEntity.getList().get(position).getGoods_name());
            holder.areaTv.setText(dataEntity.getList().get(position).getProduct_area());

            holder.brandTv.setText(dataEntity.getList().get(position).getBrand());
            holder.priceTv.setText("¥" + dataEntity.getList().get(position).getGoods_price());

        }

        @Override
        public int getItemCount() {

            return dataEntity.getList().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView goodsNameTv, areaTv, priceTv, brandTv;
            private SimpleDraweeView draweeView;


            public MyViewHolder(View itemView) {
                super(itemView);
                draweeView = (SimpleDraweeView) itemView.findViewById(R.id.all_type_iv);
                goodsNameTv = (TextView) itemView.findViewById(R.id.goods_name);
                areaTv = (TextView) itemView.findViewById(R.id.area);
                brandTv = (TextView) itemView.findViewById(R.id.brand);
                priceTv = (TextView) itemView.findViewById(R.id.price);
            }
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> datass;

        public SectionsPagerAdapter(FragmentManager fm, ArrayList<Fragment> data) {
            super(fm);
            this.datass = data;
        }

        @Override
        public Fragment getItem(int position) {
            return datass.get(position);
        }

        @Override
        public int getCount() {
            return datass.size();
        }
    }
}

