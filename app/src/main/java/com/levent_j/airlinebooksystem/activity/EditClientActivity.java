package com.levent_j.airlinebooksystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseActivity;
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
 * Created by levent_j on 16-7-4.
 */
public class EditClientActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_edit_name) EditText name;
    @Bind(R.id.et_edit_client_id) EditText idCard;
    @Bind(R.id.et_edit_client_flight) EditText flightNo;
    @Bind(R.id.tv_edit_client_data) TextView date;
    @Bind(R.id.btn_client_commit) Button commit;

    private DatePickerPopWin pickerPopWin;
    private boolean isNew = true;
    private String objectId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_client;
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
            name.setText(intent.getStringExtra("name"));
            idCard.setText(intent.getStringExtra("ic"));
            flightNo.setText(intent.getStringExtra("flight"));
            date.setText(intent.getStringExtra("date"));
        }
    }

    @Override
    protected void setListener() {
        date.setOnClickListener(this);
        commit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit_client_data:
                pickerPopWin.showPopWin(this);
                break;
            case R.id.btn_client_commit:
                String mName = name.getText().toString();
                String mIC = idCard.getText().toString();
                String mFlight = flightNo.getText().toString();
                String mDate = date.getText().toString();
                if (TextUtils.isEmpty(mName)||TextUtils.isEmpty(mIC)||TextUtils.isEmpty(mFlight)||mDate.equals("请选择")){
                    Toa("请填写完整乘客信息");
                }else {
                    commitClient(mName,mIC,mFlight,mDate);
                }
                break;
        }
    }

    private void commitClient(final String mName, final String mIC, final String mFlight, final String mDate) {
        commit.setText("请等待");
        commit.setEnabled(false);

        //先检查航班号是否存在
        BmobQuery<Flight> query = new BmobQuery<>();
        query.addWhereEqualTo("flightNo",mFlight);
        query.findObjects(new FindListener<Flight>() {
            @Override
            public void done(List<Flight> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        //存在该航班
                        Client client = new Client();
                        client.setName(mName);
                        client.setIdCard(mIC);
                        client.setFlightNo(mFlight);
                        client.setData(mDate);
                        if (isNew) {
                            createClient(client);
                        } else {
                            updateClient(client);
                        }
                    } else {
                        Toa("不存在该航班！");
                        commit.setText("提交");
                        commit.setEnabled(true);
                    }
                } else {
                    Toa("网络繁忙，请稍后再试");
                    Log.e("Bmob", "query error:" + e.getMessage());
                }
            }
        });
    }

    private void createClient(Client client) {
        client.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    finish();
                }else {
                    Toa("网络繁忙，请稍后再试");
                    Log.e("Bmob", "save error:" + e.getMessage());
                    commit.setText("提交");
                    commit.setEnabled(true);
                }
            }
        });
    }

    private void updateClient(Client client) {
        client.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                    finish();
                }else {
                    Toa("网络繁忙，请稍后再试");
                    Log.e("Bmob", "save error:" + e.getMessage());
                    commit.setText("提交");
                    commit.setEnabled(true);
                }
            }
        });
    }
}
