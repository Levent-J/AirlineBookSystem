package com.levent_j.airlinebooksystem.base;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Bundle;

import com.levent_j.airlinebooksystem.R;

import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-6-30.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public String TAG = this.getClass().getSimpleName();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        setListener();
        initData();

    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setListener();

    protected void msg(String s) {
        Log.d(TAG, s);
    }

    protected void Toa(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }



}