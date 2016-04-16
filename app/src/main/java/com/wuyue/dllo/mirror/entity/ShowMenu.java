package com.wuyue.dllo.mirror.entity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wuyue.dllo.mirror.R;
import com.wuyue.dllo.mirror.activity.MainActivity;
import com.wuyue.dllo.mirror.adapter.ShowMenuAdapter;
import com.wuyue.dllo.mirror.cache.DaoMaster;
import com.wuyue.dllo.mirror.cache.DaoSession;
import com.wuyue.dllo.mirror.cache.InForEntity;
import com.wuyue.dllo.mirror.cache.InForEntityDao;

import com.wuyue.dllo.mirror.myinterface.SetTitle;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by dllo on 16/3/31.
 */
public class ShowMenu implements AdapterView.OnItemClickListener {

    private Context context;
    private PopupWindow popupWindow;
    private ListView listView;
    private ShowMenuAdapter showMenuAdapter;
    private TextView textView,popQuitTv;
    private Handler handler;
    private MainActivity mainActivity;

    private String title;
    private SetTitle setTitle;

    private SQLiteDatabase showDb;
    private DaoMaster showDaoMaster;
    private DaoSession showDaoSession;
    private InForEntityDao showEntityDao;
    private ArrayList<InForEntity> datadata;


    // 构造方法传入上下文环境
    public ShowMenu(Context context) {
        setTitle = (SetTitle) context;
        this.context = context;

    }

    // 弹出PopupWindow的方法
    // 参数一：当前view
    // 参数二：menu标题的集合
    // 参数三：当前fragment的位置
    public void showPopupWindow(View v, final ArrayList<String> titleData, final int linePosition) {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        textView = (TextView) view.findViewById(R.id.pop_return_textview);

        initView(view);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"InFor.entity",null);
        showDb = helper.getWritableDatabase();
        showDaoMaster = new DaoMaster(showDb);
        showDaoSession = showDaoMaster.newSession();
        showEntityDao = showDaoSession.getInForEntityDao();


        listView.setOnItemClickListener(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到MainActivity，显示menu对应的Fragment
                //  Intent rIntent = new Intent(context, MainActivity.class);
                // rIntent.putExtra("position", 0);
                //  context.startActivity(rIntent);
                popupWindow.dismiss();
                Log.d("Sysout", "新建了Activity");
            }
        });


        popQuitTv = (TextView) view.findViewById(R.id.pop_quit_textview);

        popQuitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity) context).finish();
            }
        });



        // 设置PopupWindow的布局，显示的位置
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
//
        DisplayMetrics dm = new DisplayMetrics();
        popupWindow = new PopupWindow(view, dm.widthPixels,
                dm.heightPixels, true);
        popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.update();


        datadata = new ArrayList<>();
        String url = "http://api101.test.mirroreye.cn/index.php/index/menu_list";
        OkHttpUtils.post().url(url).build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(Response response) throws Exception {
                String body = response.body().string();
                MenuEntity entity = new Gson().fromJson(body.toString(), MenuEntity.class);

                DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"InFor.entity",null);
                showDb = helper.getWritableDatabase();
                showDaoMaster = new DaoMaster(showDb);
                showDaoSession = showDaoMaster.newSession();
                showEntityDao = showDaoSession.getInForEntityDao();


                String name;
                InForEntity inforentity = null;

                for (int i = 0; i < entity.getData().getList().size(); i++) {
                    name = entity.getData().getList().get(i).getTitle();
                    inforentity = new InForEntity((long) i, name);
                    showEntityDao.insert(inforentity);
                    datadata.add(inforentity);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e) {
                ArrayList<InForEntity> quelist = (ArrayList<InForEntity>) showEntityDao.queryBuilder().list();
                showMenuAdapter = new ShowMenuAdapter(quelist, context, linePosition);
                listView.setAdapter(showMenuAdapter);

            }

            @Override
            public void onResponse(Object response) {
                ArrayList<InForEntity> quelist = (ArrayList<InForEntity>) showEntityDao.queryBuilder().list();
                showMenuAdapter = new ShowMenuAdapter(quelist, context, linePosition);
                listView.setAdapter(showMenuAdapter);
            }
        });

        initData(view);
    }


    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.pop_listview);
    }

    private void initData(View view) {
        // 点击PopupWindow其他未设置监听的位置，PopupWindow消失
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 跳转到MainActivity，显示menu对应的Fragment
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("position", position);
       // context.startActivity(intent);
        String title = showMenuAdapter.getTitle(position);
        setTitle.setTitle(title,position);

        popupWindow.dismiss();
        Log.d("Sysout", "onItemClick");
    }



}

