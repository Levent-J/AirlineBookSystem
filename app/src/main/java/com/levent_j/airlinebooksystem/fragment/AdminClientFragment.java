package com.levent_j.airlinebooksystem.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.adapter.AdminClientAdapter;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.bean.Client;
import com.levent_j.airlinebooksystem.utils.DividerItemDecoration;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by levent_j on 16-7-4.
 */
public class AdminClientFragment extends BaseFragment{
    @Bind(R.id.rlv_admin_client) RecyclerView clientRecyclerView;
    @Bind(R.id.srl_client) SwipeRefreshLayout refresh;

    private static final String KEY = "ARGS";
    private AdminClientAdapter adapter;

    public static AdminClientFragment newInstance(String s){
        AdminClientFragment fragment = new AdminClientFragment();
        Bundle args = new Bundle();
        args.putString(KEY, s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initDatas() {
        updateData();
    }

    @Override
    protected void initView() {
        adapter = new AdminClientAdapter(getContext());
        clientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        clientRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        clientRecyclerView.setAdapter(adapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
    }

    private void updateData() {
        BmobQuery<Client> query = new BmobQuery<>();
        query.findObjects(new FindListener<Client>() {
            @Override
            public void done(List<Client> list, BmobException e) {
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
    protected int setRootViewId() {
        return R.layout.fragment_admin_client;
    }
}
