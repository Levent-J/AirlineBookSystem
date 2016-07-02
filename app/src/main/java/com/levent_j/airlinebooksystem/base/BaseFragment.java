package com.levent_j.airlinebooksystem.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by levent_j on 16-6-30.
 */

public abstract class BaseFragment extends Fragment {
    public String TAG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();

        View view = inflater.inflate(setRootViewId(), container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDatas();
        initView();
    }



    protected abstract void initDatas();

    protected abstract void initView();

    protected abstract int setRootViewId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    protected void msg(String s) {
        Log.d(TAG, s);
    }

    protected void Toa(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
