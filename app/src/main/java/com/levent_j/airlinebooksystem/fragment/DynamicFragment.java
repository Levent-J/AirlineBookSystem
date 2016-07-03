package com.levent_j.airlinebooksystem.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.PlaceListActivity;
import com.levent_j.airlinebooksystem.adapter.DynamicAdapter;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.bean.Flight;
import com.levent_j.airlinebooksystem.utils.DividerItemDecoration;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by levent_j on 16-7-1.
 */
public class DynamicFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.tv_origin_place) TextView originPlace;
    @Bind(R.id.tv_destination_place) TextView destinationPlace;
    @Bind(R.id.iv_backandforth) ImageView transfor;
    @Bind(R.id.tv_dynamic_data) TextView data;
    @Bind(R.id.iv_dynamic_data) ImageView ivData;
    @Bind(R.id.btn_dynamic_search) Button search;
    @Bind(R.id.rlv_dynamic) RecyclerView flightRecyclerView;
    @Bind(R.id.loading_dynamic) AVLoadingIndicatorView loading;

    private String customerId;
    private int REQUEST_ORIGIN = 0;
    private int REQUEST_DESTINATION = 1;

    private DynamicAdapter adapter;
    private DatePickerPopWin pickerPopWin;

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
        originPlace.setOnClickListener(this);
        destinationPlace.setOnClickListener(this);
        data.setOnClickListener(this);
        search.setOnClickListener(this);
        transfor.setOnClickListener(this);
        ivData.setOnClickListener(this);
        adapter = new DynamicAdapter(getActivity());


        flightRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        flightRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        flightRecyclerView.setAdapter(adapter);

        pickerPopWin = new DatePickerPopWin.Builder(getContext(), new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                data.setText(year+"-"+month+"-"+day);

            }
        })
                .textConfirm("选择")
                .textCancel("取消")
                .btnTextSize(16)
                .viewTextSize(25)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#7C4DEF"))
                .minYear(1970)
                .maxYear(2020)
                .dateChose("2016-07-02")
                .build();

    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_origin_place:
                startCityList(REQUEST_ORIGIN);
                break;
            case R.id.tv_destination_place:
                startCityList(REQUEST_DESTINATION);
                break;
            case R.id.iv_dynamic_data:
            case R.id.tv_dynamic_data:

                pickerPopWin.showPopWin(getActivity());

                break;
            case R.id.btn_dynamic_search:

                String origin = originPlace.getText().toString();
                String destination = destinationPlace.getText().toString();
                String day = data.getText().toString();
                if (origin.equals("出发地")||origin.equals("未选择")){
                    Toa("请选择正确的出发地");
                }else if (destination.equals("目的地")||destination.equals("未选择")){
                    Toa("请选择正确的目的地");
                }else if (day.equals("日期")){
                    Toa("请选择正确的时间");
                }else {
                    loading.setVisibility(View.VISIBLE);
                    BmobQuery<Flight> query = new BmobQuery<>();
                    query.addWhereEqualTo("originPlace", origin);
                    query.addWhereEqualTo("destinationPlace", destination);
                    query.addWhereEqualTo("data", day);
                    query.findObjects(new FindListener<Flight>() {
                        @Override
                        public void done(List<Flight> list, BmobException e) {
                            loading.setVisibility(View.GONE);
                            if (e == null) {

                                if (list.size() > 0) {
                                    adapter.replaceData(list);
                                }else {
                                    Toa("暂无航班");
                                }

                            } else {
                                Toa("网络繁忙，请稍后再试");
                                Log.e("Bmob", "error:" + e.getMessage());
                            }

                        }
                    });
                }
                break;
            case R.id.iv_backandforth:
                String swap = destinationPlace.getText().toString();
                destinationPlace.setText(originPlace.getText().toString());
                originPlace.setText(swap);
                break;
        }
    }

    private void startCityList(int code) {
        Intent intent = new Intent(getActivity(),PlaceListActivity.class);
        intent.putExtra("type",code);
        startActivityForResult(intent,code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==REQUEST_ORIGIN){
                originPlace.setText(data.getStringExtra("place"));
            }else {
                destinationPlace.setText(data.getStringExtra("place"));
            }

    }
}
