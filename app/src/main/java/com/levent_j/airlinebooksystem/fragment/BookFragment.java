package com.levent_j.airlinebooksystem.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.MainActivity;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.bean.Client;
import com.levent_j.airlinebooksystem.bean.Flight;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    @Bind(R.id.tv_book_data) TextView data;
    @Bind(R.id.btn_book_search) Button search;

    private String customerId;
    private DatePickerPopWin pickerPopWin;

    private boolean isBooked = false;
    private boolean isUpdated = false;

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

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerPopWin.showPopWin(getActivity());
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String clientName = name.getText().toString();
                final String clientIdCard = idCard.getText().toString();
                final String clientFlightNo = flightNo.getText().toString();
                final String day = data.getText().toString();
                if (TextUtils.isEmpty(clientName)) {
                    nameWrapper.setError("姓名不能为空");
                    return;
                } else {
                    nameWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(clientIdCard)) {
                    idCardWrapper.setError("身份证号不能为空");
                    return;
                } else {
                    idCardWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(clientFlightNo)) {
                    flightNoWrapper.setError("航班号不能为空");
                    return;
                } else {
                    flightNoWrapper.setErrorEnabled(false);
                }
                if (day.equals("日期")) {
                    Toa("请选择日期");
                    return;
                }

                search.setText("请等待");
                search.setEnabled(false);

                BmobQuery<Flight> query = new BmobQuery<Flight>();
                query.addWhereEqualTo("flightNo", clientFlightNo);
                query.addWhereEqualTo("data", day);
                query.findObjects(new FindListener<Flight>() {
                    @Override
                    public void done(List<Flight> list, BmobException e) {
                        search.setText("查询");
                        search.setEnabled(true);
                        if (e == null) {
                            if (list.size()>0){
                                if (list.get(0).getSurplusTickets() > 0) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("订票")
                                            .setMessage("现有余票" + list.get(0).getSurplusTickets() + "张，是否购票？")
                                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    bookTicket(clientName, clientIdCard, clientFlightNo,day, dialog);
                                                }
                                            })
                                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .create()
                                            .show();
                                } else {
                                    Toa("该航班已无余票");
                                }
                            }else {
                                Toa("无此航班信息");
                            }

                        } else {
                            Toa("网络繁忙，请稍后再试");
                            Log.e("Bmob", "error:" + e.getMessage());
                        }
                    }
                });


            }
        });
    }

    private void bookTicket(String name, String idCard, String flightNo,String data, final DialogInterface dialog) {
        search.setText("请等待");
        search.setEnabled(false);

        //增加客户
        addClient(name, idCard, flightNo, data);

        //航班票量改变
        updateTickets(flightNo);

        dialog.dismiss();
    }

    private void updateTickets(String flightNo) {
        BmobQuery<Flight> query = new BmobQuery();
        query.addWhereEqualTo("flightNo", flightNo);
        query.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> list, BmobException e) {
                if (e == null) {
                    String id = list.get(0).getObjectId();
                    int booked = list.get(0).getBookedTickets();
                    int surplus = list.get(0).getSurplusTickets();

                    Flight flight = list.get(0);
                    flight.setBookedTickets( booked + 1);
                    flight.setSurplusTickets( surplus - 1);
                    flight.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                isUpdated = true;
                                startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            } else {
                                Toa("网络繁忙，请稍后再试");
                                Log.e("Bmob", "update error:" + e.getMessage());
                            }
                        }
                    });
                } else {
                    Toa("网络繁忙，请稍后再试");
                    Log.e("Bmob", "change error:" + e.getMessage());
                }
            }
        });
    }

    private void addClient(String name, String idCard, String flightNo,String data) {
        Client client = new Client();
        client.setName(name);
        client.setIdCard(idCard);
        client.setFlightNo(flightNo);
        client.setData(data);
        client.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    isBooked = true;
                }else {
                    Toa("网络繁忙，请稍后再试");
                    Log.e("Bmob","save error:"+e.getMessage());
                }
            }
        });
    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_book;
    }
}
