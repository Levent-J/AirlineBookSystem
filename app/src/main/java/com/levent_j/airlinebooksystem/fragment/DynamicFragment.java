package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;

/**
 * Created by levent_j on 16-7-1.
 */
public class DynamicFragment extends BaseFragment{

    private String customerId;

    public static DynamicFragment newInstance(String id){
        DynamicFragment dynamicFragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        dynamicFragment.setArguments(args);
        return dynamicFragment;
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
        return R.layout.fragment_dynamic;
    }
}
