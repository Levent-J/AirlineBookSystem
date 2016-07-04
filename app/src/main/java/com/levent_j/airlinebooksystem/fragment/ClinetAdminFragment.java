package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;

/**
 * Created by levent_j on 16-7-4.
 */
public class ClinetAdminFragment extends BaseFragment{

    private static final String KEY = "ARGS";

    public static ClinetAdminFragment newInstance(String s){
        ClinetAdminFragment fragment = new ClinetAdminFragment();
        Bundle args = new Bundle();
        args.putString(KEY, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_admin_client;
    }
}