package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-1.
 */
public class BookFragment extends BaseFragment{
    @Bind(R.id.til_book_name) TextInputLayout nameWrapper;
    @Bind(R.id.til_book_idcard) TextInputLayout idCardWrapper;
    @Bind(R.id.til_book_flightNo) TextInputLayout flightNoWrapper;
    @Bind(R.id.et_book_name) EditText name;
    @Bind(R.id.et_book_idcard) EditText idCard;
    @Bind(R.id.et_book_flightNo) EditText flightNo;
    @Bind(R.id.btn_search) Button search;

    private String customerId;

    public static BookFragment newInstance(String id){
        BookFragment bookFragment = new BookFragment();
        Bundle args = new Bundle();
        args.putString("id",id);
        bookFragment.setArguments(args);
        return bookFragment;
    }

    @Override
    protected void initDatas() {
        customerId = getArguments().getString("id");
    }

    @Override
    protected void initView() {
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
        return R.layout.fragment_book;
    }
}
