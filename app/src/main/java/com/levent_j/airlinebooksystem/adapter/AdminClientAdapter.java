package com.levent_j.airlinebooksystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseAdapter;
import com.levent_j.airlinebooksystem.bean.Client;
import com.levent_j.airlinebooksystem.fragment.AdminClientFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-7-4.
 */
public class AdminClientAdapter extends BaseAdapter<Client,AdminClientAdapter.mViewHolder>{

    public AdminClientAdapter(Context context) {
        super(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_admin_client,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.bindView(mDatas.get(position));
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_admin_client_name) TextView name;
        @Bind(R.id.tv_admin_client_id) TextView idcard;
        @Bind(R.id.tv_admin_client_flight) TextView flight;
        @Bind(R.id.tv_admin_client_data) TextView data;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(Client client) {
            name.setText("姓名："+client.getName());
            idcard.setText("身份证："+client.getIdCard());
            flight.setText("航班："+client.getFlightNo());
            data.setText("乘机日期："+client.getData());
        }
    }
}
