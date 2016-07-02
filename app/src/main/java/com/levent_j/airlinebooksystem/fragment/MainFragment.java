package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;

/**
 * Created by levent_j on 16-7-1.
 */
public class MainFragment extends BaseFragment{

    private String customerId;

    public static MainFragment newInstance(String id){
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Override
    protected void initDatas() {
        customerId = getArguments().getString("id");
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_main;
    }
}
