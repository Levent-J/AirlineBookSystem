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
import com.levent_j.airlinebooksystem.activity.EditClientActivity;
import com.levent_j.airlinebooksystem.activity.EditFlightActivity;
import com.levent_j.airlinebooksystem.base.BaseAdapter;
import com.levent_j.airlinebooksystem.bean.Client;
import com.levent_j.airlinebooksystem.fragment.AdminClientFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
        @Bind(R.id.layout_admin_client) LinearLayout layout;

        public mViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bindView(final Client client) {
            name.setText("姓名："+client.getName());
            idcard.setText("身份证：" + client.getIdCard());
            flight.setText("航班：" + client.getFlightNo());
            data.setText("乘机日期：" + client.getData());
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("提示")
                            .setMessage("选择操作选项").setPositiveButton("修改", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(mContext, EditClientActivity.class);
                            intent.putExtra("isNew", false);
                            intent.putExtra("id", client.getObjectId());
                            intent.putExtra("name", client.getName());
                            intent.putExtra("ic", client.getIdCard());
                            intent.putExtra("flight", client.getFlightNo());
                            intent.putExtra("date", client.getData());
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
                                    Client c = new Client();
                                    c.setObjectId(client.getObjectId());
                                    c.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null) {
                                                Toast.makeText(mContext,"删除成功！",Toast.LENGTH_SHORT).show();
                                            } else {
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
