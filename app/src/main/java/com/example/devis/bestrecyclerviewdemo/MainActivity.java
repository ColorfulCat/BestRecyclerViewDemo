package com.example.devis.bestrecyclerviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.devis.bestrecyclerviewdemo.lib.BestRecyclerView;
import com.example.devis.bestrecyclerviewdemo.lib.LoadFinishCallBack;
import com.example.devis.bestrecyclerviewdemo.lib.MyImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {


    // listview
    private BestRecyclerView mRecyclerView;
    private LoadFinishCallBack mLoadFinisCallBack;
    private DataAdapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private boolean isRefreshing = false;// 是否正在刷新操作
    private Context mContext;

    private List<DataVO> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //造假数据,10项分页
        datas = new ArrayList<>();
        for(int i =0; i< 15; i++){
            DataVO data = new DataVO();
            data.setNum(i);
            datas.add(data);
        }

        //初始化imageloader
        MyImageLoader.init(mContext);
        initView();
    }

    private void initView(){
        // 用户列表
        mRecyclerView = (BestRecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mLoadFinisCallBack = mRecyclerView;
        mAdapter = new DataAdapter(mContext, datas);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefreshing)
                    return;
                isRefreshing = true;
                mAdapter.loadFirst();
                //模拟请求数据延时
                new Handler().postDelayed(new Runnable() { // 模拟网络请求数据
                    @Override
                    public void run() {
                        isRefreshing = false;
                        refreshLayout.setRefreshing(false); // 停止顶部那个progress
                    }
                }, 2000);
            }
        });
        //上拉加载更多
        mRecyclerView.setLoadMoreListener(new BestRecyclerView.onLoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });



        mRecyclerView.setAdapter(mAdapter);

    }

}
