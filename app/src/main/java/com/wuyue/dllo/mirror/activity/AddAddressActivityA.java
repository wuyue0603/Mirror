package com.wuyue.dllo.mirror.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.entity.MyAddressListEntity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/4/12.
 */
public class AddAddressActivityA extends AppCompatActivity implements View.OnClickListener {
    private Button button;
    private Handler handler, handler1, handler2;
    private ImageView iv;
    private List<ApplicationInfo> mAppList;
    private AppAdapter mAdapter;
    private SwipeMenuListView listView;
    private static MyAddressListEntity entity;
    private MyBroadcast myBroadcast;
    private int resultCode = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_a);

        button = (Button) findViewById(R.id.address_btn);
        iv = (ImageView) findViewById(R.id.address_a_close_iv);
        iv.setOnClickListener(this);
        button.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.wuyue.dllo.mirror.Broadcast");
        registerReceiver(myBroadcast, intentFilter);
        okHttp();

        mAppList = getPackageManager().getInstalledApplications(0);

        listView = (SwipeMenuListView) findViewById(R.id.add_address_a_listview);

        // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // Create different menus depending on the view type
                switch (menu.getViewType()) {

                    case 0:
                        createMenu2(menu);
                        break;
                }
            }

            private void createMenu2(SwipeMenu menu) {
                SwipeMenuItem item2 = new SwipeMenuItem(
                        getApplicationContext());
                item2.setBackground(R.mipmap.chatpay);
                item2.setWidth(dp2px(90));
                item2.setTitle("删除");
                item2.setTitleColor(R.color.colorPrimary);
                item2.setTitleSize(20);
                menu.addMenuItem(item2);
            }
        };

        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                ApplicationInfo item = mAppList.get(position);
                switch (index) {
                    case 0:
                        handler1 = new Handler(new Handler.Callback() {
                            @Override
                            public boolean handleMessage(Message msg) {
                                mAppList.remove(entity.getData().getList().get(position).getAddr_id());
                                mAdapter.upData(position);
                                listView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                                Toast.makeText(AddAddressActivityA.this, "删除成功", Toast.LENGTH_SHORT).show();

                                return false;
                            }
                        });

                        String url = "http://api101.test.mirroreye.cn/index.php/user/del_address";
                        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152").
                                addParams("addr_id", entity.getData().getList().get(position).getAddr_id())
                                .build().execute(new Callback() {
                            @Override
                            public Object parseNetworkResponse(Response response) throws Exception {
                                Message message = new Message();
                                String body = response.body().string();
                                message.obj = body;
                                handler1.sendMessage(message);
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
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handler2 = new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        okHttp();
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(AddAddressActivityA.this, "设置完成", Toast.LENGTH_SHORT).show();
                        finish();
                        return false;
                    }
                });

                String url = "http://api101.test.mirroreye.cn/index.php/user/mr_address";
                OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
                        .addParams("addr_id", entity.getData().getList().get(position).getAddr_id())
                        .build().execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        String body = response.body().string();
                        Message message = new Message();
                        message.obj = body;
                        handler2.sendMessage(message);
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

        });
    }

    public void okHttp() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                entity = new Gson().fromJson(msg.obj.toString(), MyAddressListEntity.class);
                mAdapter = new AppAdapter(entity, AddAddressActivityA.this);
                listView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                return false;
            }
        });

        String url = "http://api101.test.mirroreye.cn/index.php/user/address_list";
        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152").addParams("device_type", "2")
                .build().execute(new Callback() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address_btn:
                Intent intent = new Intent(AddAddressActivityA.this, AddAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.address_a_close_iv:
                AddAddressActivityA.this.finish();
                break;
        }
    }

    class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            okHttp();
        }
    }


    class AppAdapter extends BaseAdapter {
        private MyAddressListEntity data;
        private Context context;
        private int resultCode = 101;
        private Handler handler;

        public AppAdapter(MyAddressListEntity data, Context context) {
            this.data = data;
            this.context = context;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.getData().getList().size();
        }

        @Override
        public Object getItem(int position) {
            return data.getData().getList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder holder = new MyViewHolder();
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addaddressitem, null);
                holder.addName = (TextView) convertView.findViewById(R.id.address_name);
                holder.tell = (TextView) convertView.findViewById(R.id.address_tel);
                holder.address = (TextView) convertView.findViewById(R.id.address_address);
                holder.upIv = (ImageView) convertView.findViewById(R.id.updata_iv);
                convertView.setTag(holder);
            }

            holder = (MyViewHolder) convertView.getTag();
            holder.addName.setText(data.getData().getList().get(position).getUsername());
            holder.tell.setText(data.getData().getList().get(position).getCellphone());
            holder.address.setText(data.getData().getList().get(position).getAddr_info());
            holder.upIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddAddressActivity.class);
                    intent.putExtra("addr_id1", data.getData().getList().get(position).getAddr_id());
                    intent.putExtra("username1", data.getData().getList().get(position).getUsername());
                    intent.putExtra("cellphone1", data.getData().getList().get(position).getCellphone());
                    intent.putExtra("addr_info1", data.getData().getList().get(position).getAddr_info());
                    intent.setAction("com.wuyue.dllo.mirror.Broadcast");
                    context.sendBroadcast(intent);
                    context.startActivity(intent);
                    finish();
                }
            });

            return convertView;
        }

        public class MyViewHolder {
            private TextView addName, tell, address;
            private ImageView upIv;
        }

        public void upData(int position) {
            this.data.getData().getList().remove(position);
            notifyDataSetChanged();
            listView.setSelection(position);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
