package com.levent_j.airlinebooksystem.activity;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseActivity;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.fragment.AdminFlightFragment;
import com.levent_j.airlinebooksystem.fragment.AdminClientFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by levent_j on 16-7-4.
 */
public class AdminActivity extends BaseActivity{
    @Bind(R.id.tabLayout) TabLayout tabLayout;
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.admin_toolbar) Toolbar toolbar;

    private PageAdpater pageAdpater;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_admin;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        pageAdpater = new PageAdpater(getSupportFragmentManager(),this);
        pageAdpater.addFragment(AdminFlightFragment.newInstance("航班"),"航班");
        pageAdpater.addFragment(AdminClientFragment.newInstance("乘客"),"乘客");
        viewPager.setAdapter(pageAdpater);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    public class PageAdpater extends FragmentPagerAdapter{
        private List<BaseFragment> fragmentList;
        private List<String> titleList;
        private Context context;

        public PageAdpater(FragmentManager fm,Context context) {
            super(fm);
            this.context = context;
            fragmentList = new ArrayList<>();
            titleList = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return titleList.get(position);
        }

        public void addFragment(BaseFragment fragment,String title){
            fragmentList.add(fragment);
            titleList.add(title);
        }
    }
}
