package com.levent_j.airlinebooksystem.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.bean.Client;
import com.levent_j.airlinebooksystem.bean.Flight;

import java.util.List;

import butterknife.Bind;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by levent_j on 16-7-1.
 */
public class QueryFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.til_query_name) TextInputLayout nameWrapper;
    @Bind(R.id.til_query_idcard) TextInputLayout idCardWrapper;
    @Bind(R.id.til_query_flightNo) TextInputLayout flightNoWrapper;
    @Bind(R.id.et_query_name) EditText name;
    @Bind(R.id.et_query_idcard) EditText idCard;
    @Bind(R.id.et_query_flightNo) EditText flightNo;
    @Bind(R.id.tv_query_data) TextView data;
    @Bind(R.id.btn_query_search) Button search;
    @Bind(R.id.btn_query_unsubscribe) Button unSubscribe;
    @Bind(R.id.tv_ticket_destination) TextView sDestination;
    @Bind(R.id.tv_ticket_flightNo) TextView sFlightNo;
    @Bind(R.id.tv_ticket_name) TextView sName;
    @Bind(R.id.tv_ticket_seat) TextView sSeat;
    @Bind(R.id.tv_ticket_no) TextView sNo;
    @Bind(R.id.layout_ticket) LinearLayout ticket;


    private String customerId;

    //id值，用来删除更新数据
    private String clientId;
    private String flightId;
    private String dData;

    private DatePickerPopWin pickerPopWin;

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
        ticket.setVisibility(View.GONE);
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
                .colorConfirm(Color.parseColor("#009900"))
                .minYear(1970)
                .maxYear(2020)
                .dateChose("2016-07-02")
                .build();
        data.setOnClickListener(this);
        search.setOnClickListener(this);
        unSubscribe.setOnClickListener(this);
    }

    @Override
    protected int setRootViewId() {
        return R.layout.fragment_query;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_query_data:
                pickerPopWin.showPopWin(getActivity());
                break;
            case R.id.btn_query_search:

                ticket.setVisibility(View.GONE);

                String tName = name.getText().toString();
                String tIdCard = idCard.getText().toString();
                String tFlightNo = flightNo.getText().toString();
                String tData = data.getText().toString();
                dData = tData;

                if (TextUtils.isEmpty(tName)){
                    nameWrapper.setError("姓名不能为空");
                    break;
                }else {
                    nameWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(tIdCard)){
                    idCardWrapper.setError("身份证号不能为空");
                    break;
                }else {
                    idCardWrapper.setErrorEnabled(false);
                }
                if (TextUtils.isEmpty(tFlightNo)){
                    flightNoWrapper.setError("航班号不能为空");
                    break;
                }else {
                    flightNoWrapper.setErrorEnabled(false);
                }
                if (tData.equals("日期")){
                    Toa("请选择正确日期");
                    break;
                }

                BmobQuery<Client> query = new BmobQuery<>();
                query.addWhereEqualTo("name",tName);
                query.addWhereEqualTo("idCard",tIdCard);
                query.addWhereEqualTo("flightNo",tFlightNo);
                query.addWhereEqualTo("data",tData);
                query.findObjects(new FindListener<Client>() {
                    @Override
                    public void done(List<Client> list, BmobException e) {
                        if (e == null) {
                            Client client = list.get(0);
                            sName.setText("姓名\n" + client.getName());
                            sFlightNo.setText("航班号\n" + client.getFlightNo());
                            sSeat.setText("座位号\n" + client.getSeatNo());
                            sNo.setText("NO:"+client.getObjectId());

                            clientId = client.getObjectId();
                            flightId = client.getFlightNo();
                            Log.e("Bmob","flightid:"+flightId);

                            ticket.setVisibility(View.VISIBLE);

                            //查询航班目的地
                            BmobQuery<Flight> fquery = new BmobQuery<Flight>();
                            fquery.addWhereEqualTo("flightNo", client.getFlightNo());
                            fquery.findObjects(new FindListener<Flight>() {
                                @Override
                                public void done(List<Flight> list, BmobException e) {
                                    if (e == null) {
                                        sDestination.setText("目的地\n"+list.get(0).getDestinationPlace());
                                    } else {
                                        Log.e("Bmob","fquery error:"+e.getMessage());
                                    }
                                }
                            });
                        } else {
                            ticket.setVisibility(View.GONE);
                            Log.e("Bmob", "query error:" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_query_unsubscribe:
                //首先要查询一下该票是否存在
                BmobQuery<Client> cquery = new BmobQuery<>();
                cquery.addWhereEqualTo("objectId",clientId);
                cquery.findObjects(new FindListener<Client>() {
                    @Override
                    public void done(List<Client> list, BmobException e) {
                        if (e == null) {
                            if (list.size() > 0) {
                                //存在此机票
                                deleTicket();
                            } else {
                                Toa("该机票不存在！");
                                ticket.setVisibility(View.GONE);
                            }
                        } else {
                            Log.e("Bmob","delete error:"+e.getMessage());
                        }
                    }
                });


                break;
        }

    }

    private void deleTicket() {
        //删除客户信息
        Client client = new Client();
        client.setObjectId(clientId);
        client.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toa("删除成功！");
                } else {
                    Log.e("Bmob","delete error:"+e.getMessage());
                }
            }
        });
        //更新票数信息
        BmobQuery<Flight> fquery = new BmobQuery<>();
        fquery.addWhereEqualTo("flightNo",flightId);
        fquery.addWhereEqualTo("data",dData);
        fquery.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> list, BmobException e) {

                if (e == null) {
                    String id = list.get(0).getObjectId();
                    int booked = list.get(0).getBookedTickets();
                    int surplus = list.get(0).getSurplusTickets();

                    Flight flight = new Flight();
                    flight.setBookedTickets(booked - 1);
                    flight.setSurplusTickets(surplus + 1);
                    flight.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {

                            } else {
                                Log.e("Bmob","change error:"+e.getMessage());
                            }
                        }
                    });
                } else {

                }

            }
        });
    }
}
