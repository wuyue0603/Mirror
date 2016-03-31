package com.wuyue.dllo.mirror.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by dllo on 16/3/31.
 */
public class BaseApplication extends Application{
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(context);
    }
    public static Context getContext(){
        return context;
    }
}
