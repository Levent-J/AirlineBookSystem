package com.levent_j.airlinebooksystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.AdminActivity;
import com.levent_j.airlinebooksystem.activity.EditClientActivity;
import com.levent_j.airlinebooksystem.activity.EditFlightActivity;
import com.levent_j.airlinebooksystem.adapter.AdminFlightAdapter;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.bean.Flight;
import com.levent_j.airlinebooksystem.utils.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by levent_j on 16-7-4.
 */
public class AdminFlightFragment extends BaseFragment{
    @Bind(R.id.rlv_admin_flight) RecyclerView flightRecyclerView;
    @Bind(R.id.srl_flight) SwipeRefreshLayout refresh;
    @Bind(R.id.fab_flight) FloatingActionButton fab;

    private static final String KEY = "ARGS";
    private AdminFlightAdapter adapter;

    public static AdminFlightFragment newInstance(String s){
        AdminFlightFragment fragment = new AdminFlightFragment();
        Bundle args = new Bundle();
        args.putString(KEY, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatas() {
        updateDate();
    }

    public void updateDate() {
        BmobQuery<Flight> query = new BmobQuery<>();
        query.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> list, BmobException e) {
                refresh.setRefreshing(false);
                if (e==null){
                    adapter.replaceData(list);
                }else {
                    Toa("网络繁忙，请稍后再试");
                }
            }
        });
    }

    @Override
    protected void initView() {
        adapter = new AdminFlightAdapter(getContext());
        flightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        flightRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        flightRecyclerView.setAdapter(adapter);

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateDate();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditFlightActivity.class);
                intent.putExtra("isNew",true);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_admin_flight;
    }
}
