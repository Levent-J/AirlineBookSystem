package com.levent_j.airlinebooksystem;

import android.app.Application;
import android.util.Log;

import cn.bmob.v3.Bmob;

/**
 * Created by levent_j on 16-6-30.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "0021d3d62dc0cd2ef2352ffd1575a51e");
    }
}
