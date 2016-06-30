package com.levent_j.airlinebooksystem.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by levent_j on 16-6-30.
 */
public abstract class BaseAdapter<T,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH>{

    public List<T> mDatas;

    private Context context;

    public BaseAdapter(Context context){
        mDatas = new ArrayList<T>();
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void appendData(List<T> datas){
        int start = mDatas.size();
        int itemCount = datas.size();
        mDatas.addAll(datas);
        notifyItemRangeInserted(start,itemCount);
    }

    public void replaceData(List<T> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }
}
