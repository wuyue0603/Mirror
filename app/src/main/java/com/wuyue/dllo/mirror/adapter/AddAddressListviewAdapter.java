//package com.wuyue.dllo.mirror.adapter;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.wuyue.dllo.mirror.R;
//import com.wuyue.dllo.mirror.activity.AddAddressActivity;
//import com.wuyue.dllo.mirror.activity.AddAddressActivityA;
//import com.wuyue.dllo.mirror.activity.OrderContentActivity;
//import com.wuyue.dllo.mirror.entity.MyAddressListEntity;
//import com.wuyue.dllo.mirror.net.GetInfo;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.Callback;
//
//import java.util.ArrayList;
//
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * Created by dllo on 16/4/12.
// */
//public class AddAddressListviewAdapter extends BaseAdapter {
//
//    private MyAddressListEntity data;
//    private Context context;
//    private int resultCode = 101;
//    private Handler handler;
//
//    public AddAddressListviewAdapter(MyAddressListEntity data, Context context) {
//        this.data = data;
//        this.context = context;
//    }
//
//    @Override
//    public int getCount() {
//
//        return data.getData().getList().size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return data.getData().getList().get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        MyViewHolder holder = new MyViewHolder();
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.addaddressitem, parent, false);
//            holder.addName = (TextView) convertView.findViewById(R.id.address_name);
//            holder.tell = (TextView) convertView.findViewById(R.id.address_tel);
//            holder.address = (TextView) convertView.findViewById(R.id.address_address);
//            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.address_a_linear);
//            convertView.setTag(holder);
//        } else {
//            holder = (MyViewHolder) convertView.getTag();
//            holder.addName.setText(data.getData().getList().get(position).getUsername());
//            holder.tell.setText(data.getData().getList().get(position).getCellphone());
//            holder.address.setText(data.getData().getList().get(position).getAddr_info());
//
//
//            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent();
//
//                    handler = new Handler(new Handler.Callback() {
//                        @Override
//                        public boolean handleMessage(Message msg) {
//                            return false;
//                        }
//                    });
//
//
//                    String url = "http://api101.test.mirroreye.cn/index.php/user/mr_address";
//                    OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
//                            .addParams("addr_id", data.getData().getList().get(position).getAddr_id())
//                            .build().execute(new Callback() {
//                        @Override
//                        public Object parseNetworkResponse(Response response) throws Exception {
//                            String body = response.body().string();
//                            Message message = new Message();
//                            message.obj = body;
//                            return null;
//                        }
//
//                        @Override
//                        public void onError(Call call, Exception e) {
//
//                        }
//
//                        @Override
//                        public void onResponse(Object response) {
//
//                        }
//                    });
//
//                    ((AppCompatActivity) context).setResult(resultCode, intent);
//                    ((AppCompatActivity) context).finish();
//                    Toast.makeText(context, "设置成功", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//        return convertView;
//    }
//
//    public class MyViewHolder {
//        private TextView addName, tell, address;
//        private LinearLayout linearLayout;
//
//
//    }
//}
