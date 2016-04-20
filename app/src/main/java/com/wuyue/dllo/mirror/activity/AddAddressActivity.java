
package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/30.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView addIv;
    private EditText addNameEt, addTelEt, addAddressEt;
    private RelativeLayout relativeLayout;
    private Handler updateHandler;
    private Handler handler;
    private String token;
    private ArrayList<String> names, tels, adds;

    @Override
    protected void initData() {
        //在我的所有地址页面点击修改地址按钮,获得传过来的数据
        Intent i = getIntent();
        //如果地址不为空,则成功跳转到添加地址页面,否则会报空指针异常
        if (i.getStringExtra("addr_id1") != null) {
            addNameEt.setText(i.getStringExtra("username1"));
            addTelEt.setText(i.getStringExtra("cellphone1"));
            addAddressEt.setText(i.getStringExtra("addr_info1"));
        }
    }

    //更新修改后的地址
    public void upInfo() {
        Intent i = getIntent();
        //重新解析一遍地址
        updateHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                return false;
            }
        });

        //解析修改之后的地址,结果为1表示修改成功
        String url = "http://api101.test.mirroreye.cn/index.php/user/edit_address";
        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
                .addParams("addr_id", i.getStringExtra("addr_id1")).addParams("username", addNameEt.getText().toString())
                .addParams("cellphone", addTelEt.getText().toString()).addParams("addr_info", addAddressEt.getText().toString())
                .build().execute(new Callback() {
            //把子线程传到主线程,确保每次拉取数据都能成功
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message m = new Message();
                m.obj = body;
                //将消息发送到主线程
                updateHandler.sendMessage(m);
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

    //绑定id
    @Override
    protected void init() {
        addIv = bindView(R.id.add_address_close);
        addNameEt = bindView(R.id.add_name_et);
        addTelEt = bindView(R.id.add_tel_et);
        addAddressEt = bindView(R.id.add_address_et);
        relativeLayout = bindView(R.id.commit_address_relative);
        //给提交地址按钮布局设置监听
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //把修改后的地址传值回我的所有地址页面
                Intent i = getIntent();
                //如果名字不为空,则为修改地址
                if (i.getStringExtra("username1") != null) {
                    upInfo();
                    //更新成功后显示修改成功提示
                    Toast.makeText(AddAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    //如果名字为空,则为添加地址
                    okhttp();
                }
            }
        });
        //添加地址页面关闭按钮监听
        addIv.setOnClickListener(this);
        names = new ArrayList<>();
        tels = new ArrayList<>();
        adds = new ArrayList<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addaddress;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address_close:
                finish();
                break;
        }
    }

    //添加地址,当三项内容都不为空且长度大于0时,将添加的地址传到服务器,显示添加成功
    private void okhttp() {
        if (addNameEt != null && addNameEt.length() > 0 && addTelEt != null && addTelEt.length() > 0 && addAddressEt != null && addAddressEt.length() > 0) {
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Intent intent = new Intent(AddAddressActivity.this, AddAddressActivityA.class);
                    startActivity(intent);
                    Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });

            //解析添加的地址
            String url = "http://api101.test.mirroreye.cn/index.php/user/add_address";
            OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
                    .addParams("username", addNameEt.getText().toString()).addParams("cellphone", addTelEt.getText().toString())
                    .addParams("addr_info", addAddressEt.getText().toString()).build().execute(new Callback() {

                //成功回调的方法
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
            finish();
            //如果信息填写不全,显示请填写完整信息提示
        } else {
            Toast.makeText(AddAddressActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
        }
    }
}
