package com.levent_j.airlinebooksystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseAdapter;
import com.levent_j.airlinebooksystem.bean.Flight;

import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-7-4.
 */
public class AdminFlightAdapter extends BaseAdapter<Flight,AdminFlightAdapter.mViewHolder>{

    public AdminFlightAdapter(Context context) {
        super(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_admin_flight,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.bindView(mDatas.get(position));
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_admin_start_time) TextView start;
        @Bind(R.id.tv_admin_end_time) TextView end;
        @Bind(R.id.tv_admin_place_start) TextView origin;
        @Bind(R.id.tv_admin_place_end) TextView destination;
        @Bind(R.id.tv_admin_plane_no) TextView flightNo;
        @Bind(R.id.tv_admin_price) TextView price;
        @Bind(R.id.tv_admin_flight_surplus) TextView surplus;
        @Bind(R.id.tv_admin_flight_booked) TextView booked;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(Flight flight) {
            start.setText(flight.getStartTime());
            end.setText(flight.getEndTime());
            origin.setText(flight.getOriginPlace());
            destination.setText(flight.getDestinationPlace());
            flightNo.setText(flight.getFlightNo()+"("+flight.getFlightType()+")");
            price.setText(String.valueOf(flight.getPrice())+"￥");
            surplus.setText("剩余"+String.valueOf(flight.getSurplusTickets())+"张");
            booked.setText("已订"+String.valueOf(flight.getBookedTickets())+"张");
        }
    }
}
