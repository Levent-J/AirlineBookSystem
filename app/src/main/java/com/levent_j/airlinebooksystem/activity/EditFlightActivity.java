package com.levent_j.airlinebooksystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseActivity;
import com.levent_j.airlinebooksystem.bean.Flight;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by levent_j on 16-7-4.
 */
public class EditFlightActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.tv_edit_origin) TextView origin;
    @Bind(R.id.tv_edit_destination) TextView destination;
    @Bind(R.id.tv_edit_flight_date) TextView date;
    @Bind(R.id.et_edit_time_start) EditText start;
    @Bind(R.id.et_edit_time_end) EditText end;
    @Bind(R.id.et_edit_flightNo) EditText flightNo;
    @Bind(R.id.et_edit_flight_type) EditText flightType;
    @Bind(R.id.et_edit_price) EditText price;
    @Bind(R.id.et_edit_booked) TextView booked;
    @Bind(R.id.et_edit_surplus) TextView surplus;
    @Bind(R.id.btn_flight_commit) Button commit;

    private boolean isNew = true;
    private int REQUEST_ORIGIN = 0;
    private int REQUEST_DESTINATION = 1;
    private String objectId;
    private DatePickerPopWin pickerPopWin;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_flight;
    }

    @Override
    protected void initView() {
        pickerPopWin = new DatePickerPopWin.Builder(this, new DatePickerPopWin.OnDatePickedListener() {
            @Override
            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                date.setText(year+"-"+month+"-"+day);

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
    protected void initData() {
        Intent intent = getIntent();
        isNew = intent.getBooleanExtra("isNew", true);
        if (!isNew){
            //填充数据
            objectId = intent.getStringExtra("id");
            origin.setText(intent.getStringExtra("origin"));
            destination.setText(intent.getStringExtra("destination"));
            date.setText(intent.getStringExtra("date"));
            start.setText(intent.getStringExtra("start"));
            end.setText(intent.getStringExtra("end"));
            flightNo.setText(intent.getStringExtra("no"));
            flightType.setText(intent.getStringExtra("type"));
            price.setText(String.valueOf(intent.getIntExtra("price", 0)));
            booked.setText(String.valueOf(intent.getIntExtra("booked",0)));
            surplus.setText(String.valueOf(intent.getIntExtra("surplus", 0)));
        }
    }

    @Override
    protected void setListener() {
        origin.setOnClickListener(this);
        destination.setOnClickListener(this);
        date.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit_origin:
                startCityList(REQUEST_ORIGIN);
                break;
            case R.id.tv_edit_destination:
                startCityList(REQUEST_DESTINATION);
                break;
            case R.id.tv_edit_flight_date:
                pickerPopWin.showPopWin(this);
                break;
            case R.id.btn_flight_commit:
                String mOrigin = origin.getText().toString();
                String mDestination = destination.getText().toString();
                String mDate = date.getText().toString();
                String mStart = start.getText().toString();
                String mEnd = end.getText().toString();
                String mFlight = flightNo.getText().toString();
                String mType = flightType.getText().toString();
                int mPrice = Integer.parseInt(TextUtils.isEmpty(price.getText().toString())?"0":price.getText().toString());
                int mBooked = Integer.parseInt(TextUtils.isEmpty(booked.getText().toString())?"0":booked.getText().toString());
                int mSurplus = Integer.parseInt(TextUtils.isEmpty(surplus.getText().toString())?"0":surplus.getText().toString());
                if (mOrigin.equals("请选择")){
                    Toa("请选择出发地");
                }else if (mDestination.equals("请选择")) {
                    Toa("请选择目的地");
                }else if (mDate.equals("请选择")){
                    Toa("请选择日期");
                } else if (TextUtils.isEmpty(mStart)||TextUtils.isEmpty(mEnd)||TextUtils.isEmpty(mFlight)
                        ||TextUtils.isEmpty(mType)){
                    Toa("请填写完整航班信息");
                }else {
                    commitFlight(mOrigin,mDestination,mDate,mStart,mEnd,mFlight,mType,mPrice,mBooked,mSurplus);
                }

                break;
        }
    }

    private void commitFlight(String mOrigin, String mDestination, String mDate, String mStart, String mEnd,
                              String mFlight, String mType, int mPrice, int mBooked, int mSurplus) {
        commit.setText("请等待");
        commit.setEnabled(false);

        Flight flight = new Flight();
        flight.setOriginPlace(mOrigin);
        flight.setDestinationPlace(mDestination);
        flight.setData(mDate);
        flight.setStartTime(mStart);
        flight.setEndTime(mEnd);
        flight.setFlightNo(mFlight);
        flight.setFlightType(mType);
        flight.setPrice(mPrice);
        flight.setBookedTickets(mBooked);
        flight.setSurplusTickets(mSurplus);

        if (isNew){
            createFlight(flight);
        }else {
            updateFlight(flight);
        }
    }

    private void updateFlight(Flight flight) {
        flight.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    finish();
                }else {
                    Toa("网络繁忙，请稍后再试");
                    commit.setText("提交");
                    commit.setEnabled(true);
                    Log.e("Bmob","save error:"+e.getMessage());
                }
            }
        });
    }

    private void createFlight(Flight flight) {

        flight.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    finish();
                }else {
                    Toa("网络繁忙，请稍后再试");
                    commit.setText("提交");
                    commit.setEnabled(true);
                    Log.e("Bmob","save error:"+e.getMessage());
                }
            }
        });

    }

    private void startCityList(int code) {
        Intent intent = new Intent(this,PlaceListActivity.class);
        intent.putExtra("type",code);
        startActivityForResult(intent,code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_ORIGIN){
            origin.setText(data.getStringExtra("place"));
        }else {
            destination.setText(data.getStringExtra("place"));
        }
    }
}
