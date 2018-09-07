package com.xc.xc_test.none;

import android.app.Application;

import com.xc.xc_test.none.LogcatHelper;

/**
 * Created by yidouco.ltdyellow on 2018/6/21.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogcatHelper.getInstance(this).start();
    }
}
