package com.levent_j.airlinebooksystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.AdminActivity;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.widget.InputDialog;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-1.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.layout_guide) LinearLayout guide;
    @Bind(R.id.layout_boarding) LinearLayout boarding;
    @Bind(R.id.layout_price) LinearLayout price;
    @Bind(R.id.layout_admin) LinearLayout admin;

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
        guide.setOnClickListener(this);
        boarding.setOnClickListener(this);
        price.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_guide:
                break;
            case R.id.layout_boarding:
                break;
            case R.id.layout_price:
                break;
            case R.id.layout_admin:
                InputDialog.Builder builder = new InputDialog.Builder(getContext());
                builder.setTitle("登陆认证")
                        .setInputHint("输入管理员密码")
                        .setInputMaxWords(6)
                        .setPositiveButton("登陆", new InputDialog.ButtonActionListener() {
                            @Override
                            public void onClick(CharSequence inputText) {
                                if (inputText.toString().equals("admin")){
                                    startActivity(new Intent(getActivity(), AdminActivity.class));
                                }else {
                                    Toa("密码错误！");
                                }
                            }
                        })
                        .show();
                break;
        }
    }
}
