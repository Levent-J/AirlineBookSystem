package com.levent_j.airlinebooksystem.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.activity.EditFlightActivity;
import com.levent_j.airlinebooksystem.base.BaseAdapter;
import com.levent_j.airlinebooksystem.bean.Flight;

import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
        @Bind(R.id.layout_admin_flight) LinearLayout layout;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(final Flight flight) {
            start.setText(flight.getStartTime());
            end.setText(flight.getEndTime());
            origin.setText(flight.getOriginPlace());
            destination.setText(flight.getDestinationPlace());
            flightNo.setText(flight.getFlightNo()+"("+flight.getFlightType()+")");
            price.setText(String.valueOf(flight.getPrice())+"￥");
            surplus.setText("剩余"+String.valueOf(flight.getSurplusTickets())+"张");
            booked.setText("已订"+String.valueOf(flight.getBookedTickets())+"张");
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示")
                           .setMessage("选择操作选项").setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(mContext, EditFlightActivity.class);
                                    intent.putExtra("isNew",false);
                                    intent.putExtra("id",flight.getObjectId());
                                    intent.putExtra("origin",flight.getOriginPlace());
                                    intent.putExtra("destination",flight.getDestinationPlace());
                                    intent.putExtra("date",flight.getData());
                                    intent.putExtra("start",flight.getStartTime());
                                    intent.putExtra("end",flight.getEndTime());
                                    intent.putExtra("no",flight.getFlightNo());
                                    intent.putExtra("type",flight.getFlightType());
                                    intent.putExtra("price",flight.getPrice());
                                    intent.putExtra("booked",flight.getBookedTickets());
                                    intent.putExtra("surplus",flight.getSurplusTickets());
                                    mContext.startActivity(intent);
                                    dialog.dismiss();
                                }
                            })
                           .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                           .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Flight f = new Flight();
                                    f.setObjectId(flight.getObjectId());
                                    f.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e==null){
                                                Toast.makeText(mContext,"删除成功！",Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(mContext,"网络繁忙，请稍后再试",Toast.LENGTH_SHORT).show();
                                                Log.e("Bmob","delete error:"+e.getMessage());
                                            }
                                        }
                                    });
                                    dialog.dismiss();
                                }
                            })
                           .create()
                           .show();
                }
            });
        }
    }
}
