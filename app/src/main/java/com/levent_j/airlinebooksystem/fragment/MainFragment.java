package com.levent_j.airlinebooksystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.AdminActivity;
import com.levent_j.airlinebooksystem.activity.BoardingActivity;
import com.levent_j.airlinebooksystem.activity.GuideActivity;
import com.levent_j.airlinebooksystem.activity.PriceActivity;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.widget.InputDialog;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-1.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.layout_guide) LinearLayout guide;
    @Bind(R.id.layout_boarding) LinearLayout boarding;
    @Bind(R.id.layout_price) LinearLayout price;
    @Bind(R.id.layout_admin) LinearLayout admin;
    @Bind(R.id.iv_bg) ImageView bg;

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
        Picasso.with(getContext())
                .load(R.drawable.bg3)
                .resize(400,280)
                .into(bg);
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
                startActivity(new Intent(getActivity(), GuideActivity.class));
                break;
            case R.id.layout_boarding:
                startActivity(new Intent(getActivity(), BoardingActivity.class));
                break;
            case R.id.layout_price:
                startActivity(new Intent(getActivity(), PriceActivity.class));
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
