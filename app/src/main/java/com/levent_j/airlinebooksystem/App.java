package com.levent_j.airlinebooksystem;

import android.app.Application;
import android.util.Log;

/**
 * Created by levent_j on 16-6-30.
 */
public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Bmob","link it");
    }
}
