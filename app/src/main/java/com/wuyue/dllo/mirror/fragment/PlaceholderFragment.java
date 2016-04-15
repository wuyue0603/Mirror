package com.wuyue.dllo.mirror.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.activity.HomepageContentActivity;
import com.wuyue.dllo.mirror.adapter.ShowMenuAdapter;
import com.wuyue.dllo.mirror.entity.GoodsListEntity;
import com.wuyue.dllo.mirror.entity.ShowMenu;
import com.wuyue.dllo.mirror.myinterface.SetTitle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/5.
 */
public class PlaceholderFragment extends Fragment {
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
    private TextView textView;
    private SetTitle setTitle;
    private ShowMenuAdapter showMenuAdapter;
    private String title;

    public PlaceholderFragment() {
    }

    public PlaceholderFragment(int i, String title) {
        this.title = title;
        this.i = i;
    }

    // private String title;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setTitle = (SetTitle) context;
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
        super.onActivityCreated(savedInstanceState);
        textView.setText(title);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                GoodsListEntity entity = new Gson().fromJson(msg.obj.toString(), GoodsListEntity.class);
                // 2.设置布局管理器
                myAdapter = new MyAdapter(getActivity(), entity.getData(), i);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(myAdapter);
                return false;
            }
        });

        String url = "http://api101.test.mirroreye.cn/" + "index.php/products/goods_list";
        OkHttpUtils.post().url(url).addParams("token", "").addParams("device_type", "1")
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
        textView = (TextView) view.findViewById(R.id.fragment_title);

        linearLayout = (LinearLayout) getView().findViewById(R.id.all_type_linearlayout);
        showMenu = new ShowMenu(getContext());

        data = new ArrayList<>();
        data.add("瀏覧所有分類");
        data.add("瀏覧平光眼鏡");
        data.add("瀏覧太陽眼鏡");
        data.add("専題分享");
        data.add("我的購物車");
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu.showPopupWindow(v, data, i);
            }
        });
    }
}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private int position;
    private Context context;
    private GoodsListEntity.DataEntity dataEntity;
    private int pos;

    public MyAdapter(Context context, GoodsListEntity.DataEntity dataEntity1, int pos) {
        this.context = context;
        this.dataEntity = dataEntity1;
        this.pos = pos;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_type_item, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        this.position = position;
        if (pos == 0) {
            holder.draweeView.setImageURI(Uri.parse(dataEntity.getList().get(pos).getGoods_img()));
            holder.goodsNameTv.setText(dataEntity.getList().get(pos).getGoods_name());
            holder.areaTv.setText(dataEntity.getList().get(pos).getProduct_area());
            holder.brandTv.setText(dataEntity.getList().get(pos).getBrand());
            holder.priceTv.setText("¥" + dataEntity.getList().get(pos).getGoods_price());
        } else if (pos == 1) {
            holder.draweeView.setImageURI(Uri.parse(dataEntity.getList().get(pos).getGoods_img()));
            holder.goodsNameTv.setText(dataEntity.getList().get(pos).getGoods_name());
            holder.areaTv.setText(dataEntity.getList().get(pos).getProduct_area());
            holder.brandTv.setText(dataEntity.getList().get(pos).getBrand());
            holder.priceTv.setText("¥" + dataEntity.getList().get(pos).getGoods_price());
        } else if (pos == 2) {
            holder.draweeView.setImageURI(Uri.parse(dataEntity.getList().get(pos).getGoods_img()));
            holder.goodsNameTv.setText(dataEntity.getList().get(pos).getGoods_name());
            holder.areaTv.setText(dataEntity.getList().get(pos).getProduct_area());
            holder.brandTv.setText(dataEntity.getList().get(pos).getBrand());
            holder.priceTv.setText("¥" + dataEntity.getList().get(pos).getGoods_price());
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView goodsNameTv, areaTv, priceTv, brandTv;
        private SimpleDraweeView draweeView;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.all_type_iv);
            goodsNameTv = (TextView) itemView.findViewById(R.id.goods_name);
            areaTv = (TextView) itemView.findViewById(R.id.area);
            brandTv = (TextView) itemView.findViewById(R.id.brand);
            priceTv = (TextView) itemView.findViewById(R.id.price);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.all_type_item_linear_layout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, HomepageContentActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("pos", pos);
            context.startActivity(intent);
        }
    }
}




