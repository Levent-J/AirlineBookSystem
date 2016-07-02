package com.levent_j.airlinebooksystem.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-2.
 */
public class PlaceListActivity extends BaseActivity{
    @Bind(R.id.lv_cities) ListView cityList;

    private int type = 0;
    private String[] cities = new String[]{"北京","上海","广州","深圳","成都","西安","昆明","厦门","杭州","乌鲁木齐","南京","哈尔滨"};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_place_list;
    }

    @Override
    protected void initView() {


        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toa("选择了" + cities[position]);
                Intent intent  = new Intent();
                intent.putExtra("place",cities[position]);
                setResult(type,intent);
                finish();
            }
        });


    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra("type",0);
    }

    @Override
    protected void setListener() {

    }
}
