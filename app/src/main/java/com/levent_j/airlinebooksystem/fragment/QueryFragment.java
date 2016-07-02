package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-1.
 */
public class QueryFragment extends BaseFragment{
    @Bind(R.id.til_query_name) TextInputLayout nameWrapper;
    @Bind(R.id.til_query_idcard) TextInputLayout idCardWrapper;
    @Bind(R.id.til_query_flightNo) TextInputLayout flightNoWrapper;
    @Bind(R.id.et_query_name) EditText name;
    @Bind(R.id.et_query_idcard) EditText idCard;
    @Bind(R.id.et_query_flightNo) EditText flightNo;
    @Bind(R.id.tv_query_data) EditText data;
    @Bind(R.id.btn_query_search) Button search;


    private String customerId;

    public static QueryFragment newInstance(String id){
        QueryFragment queryFragment = new QueryFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        queryFragment.setArguments(args);
        return queryFragment;
    }

    @Override
    protected void initDatas() {
        customerId = getArguments().getString("id");
    }

    @Override
    protected void initView() {
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TODO:日期选择器
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())){
                    nameWrapper.setError("姓名不能为空");
                }else {
                    nameWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(idCard.getText().toString())){
                    idCardWrapper.setError("身份证号不能为空");
                }else {
                    idCardWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(flightNo.getText().toString())){
                    flightNoWrapper.setError("航班号不能为空");
                }else {
                    flightNoWrapper.setErrorEnabled(false);
                }
            }
        });
    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_query;
    }
}
