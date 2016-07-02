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
 * Created by levent_j on 16-7-2.
 */
public class DynamicAdapter extends BaseAdapter<Flight,DynamicAdapter.mViewHolder>{

    public DynamicAdapter(Context context) {
        super(context);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic,parent,false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Flight flight = mDatas.get(position);
        holder.bindView(flight);
    }

    class mViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_start_time) TextView start;
        @Bind(R.id.tv_end_time) TextView end;
        @Bind(R.id.tv_place_start) TextView origin;
        @Bind(R.id.tv_place_end) TextView destination;
        @Bind(R.id.tv_plane_no) TextView flightNo;
        @Bind(R.id.tv_price) TextView price;
        @Bind(R.id.tv_surplus_tickets) TextView tickets;

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
            tickets.setText(String.valueOf(flight.getSurplusTickets()));
        }
    }
}
