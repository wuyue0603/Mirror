package com.wuyue.dllo.mirror.entity;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dllo on 16/4/6.
 */
public class HomepageContentTypeEntity {
    List<String> data;
    List<Integer> type;
    public static final int FIRST_TYPE = 0;
    public static final int SECOND_TYPE = 1;
    public static final int THIRD_TYPE = 2;
    public static final int FOURTH_TYPE = 3;

    public HomepageContentTypeEntity(List<String> data, List<Integer> type) {
        this.data = data;
        this.type = type;
    }

    public HomepageContentTypeEntity() {
        data = new ArrayList<>();
        type = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 50; i++) {
            data.add("这是数据" + i);
            //type.add(random.nextInt(3)); //随机数0 1 2

            type.add(i % 4);
            Log.d("Mybean", "type.get(i):" + type.get(i));
        }
    }

    //有多少条数据
    public int getCount() {
        return 50;
    }

    //哪一个类型 0 还是 1 还是 2 还是 3
    public int getType(int pos) {
        return type.get(pos);
    }

    //提供指定位置的信息
    public String getData(int pos) {
        return data.get(pos);
    }
}

