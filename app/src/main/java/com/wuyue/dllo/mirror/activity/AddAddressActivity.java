
package com.wuyue.dllo.mirror.activity;

import android.content.Intent;
import android.location.Address;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.base.BaseActivity;
import com.wuyue.dllo.mirror.entity.LoginEntity;
import com.wuyue.dllo.mirror.entity.MyAddressListEntity;
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
        Intent i = getIntent();
        if (i.getStringExtra("addr_id1") != null) {
            addNameEt.setText(i.getStringExtra("username1"));
            addTelEt.setText(i.getStringExtra("cellphone1"));
            addAddressEt.setText(i.getStringExtra("addr_info1"));
        }
    }

    public void upInfo() {
        Intent i = getIntent();


        updateHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                return false;
            }
        });

        String url = "http://api101.test.mirroreye.cn/index.php/user/edit_address";
        OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
                .addParams("addr_id", i.getStringExtra("addr_id1")).addParams("username", addNameEt.getText().toString())
                .addParams("cellphone", addTelEt.getText().toString()).addParams("addr_info", addAddressEt.getText().toString())
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                Message m = new Message();
                m.obj = body;
                updateHandler.sendMessage(m);
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {
                Log.d("ttttttt22","66666666");
            }

            @Override
            public void onResponse(Object response) {
                Log.d("ttttttt333","66666666");
            }
        });

    }

    @Override
    protected void init() {
        addIv = bindView(R.id.add_address_close);
        addNameEt = bindView(R.id.add_name_et);
        addTelEt = bindView(R.id.add_tel_et);
        addAddressEt = bindView(R.id.add_address_et);
        relativeLayout = bindView(R.id.commit_address_relative);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                if (i.getStringExtra("username1") != null) {
                    upInfo();
                    Toast.makeText(AddAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    okhttp();
                }
//                Intent intent = new Intent(AddAddressActivity.this, AddAddressActivityA.class);
//                intent.setAction("com.wuyue.dllo.mirror.Broadcast");
//                sendBroadcast(intent);

            }
        });
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

            String url = "http://api101.test.mirroreye.cn/index.php/user/add_address";
            OkHttpUtils.post().url(url).addParams("token", "433ae165cc754e151c0e8de2ed6ba152")
                    .addParams("username", addNameEt.getText().toString()).addParams("cellphone", addTelEt.getText().toString())
                    .addParams("addr_info", addAddressEt.getText().toString()).build().execute(new Callback() {
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
        } else {
            Toast.makeText(AddAddressActivity.this, "请填写完整信息", Toast.LENGTH_SHORT).show();
        }
    }
}
