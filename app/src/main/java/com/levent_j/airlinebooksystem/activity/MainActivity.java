package com.levent_j.airlinebooksystem.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.levent_j.airlinebooksystem.R;
import com.levent_j.airlinebooksystem.base.BaseActivity;
import com.levent_j.airlinebooksystem.base.BaseFragment;
import com.levent_j.airlinebooksystem.fragment.BookFragment;
import com.levent_j.airlinebooksystem.fragment.DynamicFragment;
import com.levent_j.airlinebooksystem.fragment.MainFragment;
import com.levent_j.airlinebooksystem.fragment.QueryFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;
    @Bind(R.id.container) FrameLayout container;

    private ActionBarDrawerToggle toggle;
    private static final String TITLE_MAIN = "首页";
    private static final String TITLE_DYNAMIC = "航班动态";
    private static final String TITLE_BOOK = "预订机票";
    private static final String TITLE_QUERY = "查询机票";
    private static final String TITLE_INFORMAtiON = "个人信息";
    private static final String TITLE_ABOUT = "关于";

    private int current;
    private String id = "id";
    private FragmentManager fragmentManager;

    private List<BaseFragment> fragmentList;

    private long lastBackTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_main);

        //填充所有fragment
        fragmentManager = getSupportFragmentManager();
        fragmentList = new ArrayList<>();
        fragmentList.add(MainFragment.newInstance(id));
        fragmentList.add(DynamicFragment.newInstance(id));
        fragmentList.add(BookFragment.newInstance(id));
        fragmentList.add(QueryFragment.newInstance(id));
        fragmentManager.beginTransaction()
                .replace(R.id.container,fragmentList.get(0))
                .addToBackStack(fragmentList.get(0).getClass().getSimpleName())
                .commit();
    }

    @Override
    protected void initData() {
        toolbar.setTitle(TITLE_MAIN);
    }

    @Override
    protected void setListener() {
        fab.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis()-lastBackTime>3000){
                Toa("重复操作退出程序");
                lastBackTime = System.currentTimeMillis();
            }else {
//                super.onBackPressed();
                finish();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 监听侧滑菜单栏
     * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        int select = item.getItemId();
        if (select == current){
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        current = select;
        BaseFragment fragment = null;

        switch (select){
            case R.id.nav_main:
                fragment = fragmentList.get(0);
                toolbar.setTitle(TITLE_MAIN);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_dynamic:
                fragment = fragmentList.get(1);
                toolbar.setTitle(TITLE_DYNAMIC);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_book:
                fragment = fragmentList.get(2);
                toolbar.setTitle(TITLE_BOOK);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_query:
                fragment = fragmentList.get(3);
                toolbar.setTitle(TITLE_QUERY);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_about:
                //关于
                startActivity(new Intent(this,AboutActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
//        fragmentManager.popBackStack();
        fragmentManager.beginTransaction()
                .replace(R.id.container,fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        return true;
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void backMainFragment(){

    }
}
