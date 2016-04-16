package com.wuyue.dllo.mirror.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;

import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.MyAddressEntity;
import com.wuyue.dllo.mirror.entity.MyAddressListEntity;
import com.wuyue.dllo.mirror.net.GetInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/31.
 */
public class OrderContentActivity extends BaseActivity {
    private SimpleDraweeView img;
    private TextView brandTv, priceTv, writeTv;
    private TextView orderName, orderTel, orderAddress, receiverInfo;
    private ImageView closeIv;
    private Handler handler;
    private String addrId;
    private MyAddressListEntity entity;
    private String price, id;
    private ImageView sureOrderIv;
    private int requesCode = 102;

    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        setAddress();
        writeTv = bindView(R.id.write_address);
        writeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(OrderContentActivity.this, AddAddressActivityA.class);
                startActivityForResult(intent1, 102);
            }
        });
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void setAddress(){
        brandTv = bindView(R.id.things_brand);
        priceTv = bindView(R.id.things_price);
        closeIv = bindView(R.id.login_close);
        sureOrderIv = bindView(R.id.sure_list_iv);
        sureOrderIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialog();
            }
        });
        receiverInfo = bindView(R.id.receiver_info);
        orderName = bindView(R.id.order_content_name);
        orderTel = bindView(R.id.order_content_tel);
        orderAddress = bindView(R.id.order_content_address);
        img = bindView(R.id.things_iv);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                MyAddressEntity entity1 = new Gson().fromJson(msg.obj.toString(), MyAddressEntity.class);
                if (entity1 != null) {
                    orderName.setText("收件人:" + entity1.getData().getAddress().getUsername());
                    orderTel.setText(entity1.getData().getAddress().getCellphone());
                    orderAddress.setText("地址:" + entity1.getData().getAddress().getAddr_info());
                    brandTv.setText(entity1.getData().getGoods().getGoods_name());
                    priceTv.setText("¥" + entity1.getData().getGoods().getPrice());
                    img.setImageURI(Uri.parse(entity1.getData().getGoods().getPic()));
                    writeTv.setText("更改地址");
                    receiverInfo.setVisibility(View.GONE);
                } else {
                    receiverInfo.setVisibility(View.VISIBLE);
                    writeTv.setText("添加地址");
                }
                return false;
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        price = intent.getStringExtra("price");
        String url = "http://api101.test.mirroreye.cn/index.php/order/sub";
        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152").addParams("goods_id", id).addParams("price", price)
                .addParams("device_type", "2").addParams("goods_num", "30").build().execute(new Callback() {
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

    private void payDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pay_dialog, null);
        builder.setView(view);
        builder.show();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ordercontent;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 101){
            orderName.setText(data.getStringExtra("orname"));
            orderTel.setText(data.getStringExtra("orcell"));
            orderAddress.setText(data.getStringExtra("orinfo"));
        }
    }
}
