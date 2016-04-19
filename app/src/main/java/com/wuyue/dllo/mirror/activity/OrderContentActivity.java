package com.wuyue.dllo.mirror.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;

import com.wuyue.dllo.mirror.alipay.PayResult;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.AilpayEntity;
import com.wuyue.dllo.mirror.entity.MyAddressEntity;
import com.wuyue.dllo.mirror.entity.MyAddressListEntity;
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
    private RelativeLayout relativelayout;
    private static final int SDK_PAY_FLAG = 1;
    private String payInfo;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderContentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderContentActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderContentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    //private String payInfo;
    @Override
    protected void initData() {

    }

    @Override
    protected void init() {
        /**
         * 回调setAddress方法
         */
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

    /**
     * 定义从MyAddressEntity中解析出来的数据,设置为全局便于下面使用
     */
    String addr_id;
    String order_id;
    String good_name;

    public void setAddress() {
        /**
         * 绑定各个组件
         */
        brandTv = bindView(R.id.things_brand);
        priceTv = bindView(R.id.things_price);
        closeIv = bindView(R.id.login_close);
        sureOrderIv = bindView(R.id.sure_list_iv);
        receiverInfo = bindView(R.id.receiver_info);
        orderName = bindView(R.id.order_content_name);
        orderTel = bindView(R.id.order_content_tel);
        orderAddress = bindView(R.id.order_content_address);
        img = bindView(R.id.things_iv);
        /**
         * handler回调
         */
        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                MyAddressEntity entity1 = new Gson().fromJson(msg.obj.toString(), MyAddressEntity.class);
                if (entity1 != null) {

                    addr_id = entity1.getData().getAddress().getAddr_id();
                    order_id = entity1.getData().getOrder_id();
                    good_name = entity1.getData().getGoods().getGoods_name();

                    /**
                     * 设置提交订单按钮的监听,
                     * 并在监听中传入addr_id, order_id, good_name 用来解析
                     */
                    sureOrderIv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            payDialog(addr_id, order_id, good_name);
                        }
                    });

                    /**
                     * 给小白方块里的内容设置具体的值
                     */
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

        /**
         * 通过从传过来的id 和 price 作为参数来解析订单详情
         */
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

    /**
     * 这里是显示dialog的方法,下面这三个参数是解析支付宝支付过程中需要用到的参数,具体接口文档中有
     * 终极目标是获得payInfo 接口中有个叫Str 的一串字符串,字符串中包含了 公钥,私钥,商户id,商品详情等内容
     *
     * @param addr_id
     * @param order_id
     * @param good_name 在成功的方法onResponse 中设置dialog中的行监听
     *                  用来调到支付宝的webwiew中 具体怎样跳转 并不清楚 下载demo
     *                  直接就跳了 非常好用
     */
    private void payDialog(String addr_id, String order_id, String good_name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pay_dialog, null);
        builder.setView(view);
        builder.show();
        relativelayout = (RelativeLayout) view.findViewById(R.id.relativelayout);

        String url = "http://api101.test.mirroreye.cn/index.php/pay/ali_pay_sub";
        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152").addParams("order_no", order_id).addParams("addr_id", addr_id)
                .addParams("goodsname", good_name).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();

                AilpayEntity ailpayEntity = new Gson().fromJson(body.toString(), AilpayEntity.class);
                payInfo = ailpayEntity.getData().getStr();
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(Object response) {

                relativelayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                // 构造PayTask 对象
                                PayTask alipay = new PayTask(OrderContentActivity.this);
                                // 调用支付接口，获取支付结果
                                String result = alipay.pay(payInfo, true);
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                });
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_ordercontent;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == 101) {
            orderName.setText(data.getStringExtra("orname"));
            orderTel.setText(data.getStringExtra("orcell"));
            orderAddress.setText(data.getStringExtra("orinfo"));
        }
    }
}
