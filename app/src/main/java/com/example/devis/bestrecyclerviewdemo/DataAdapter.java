package com.example.devis.bestrecyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private Context mContext;
    private int lastPosition = -1;//for animation position
    private List<DataVO> mData;
    private int page;

    public DataAdapter(Context context, List<DataVO> data) {
        this.mContext = context;
        this.mData = data;
    }


    protected void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R
                    .anim.item_bottom_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        holder.textView.clearAnimation();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        DataVO data = mData.get(position);

        viewHolder.textView.setText("No. " + data.getNum());
        setAnimation(viewHolder.textView, position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void refreshAdapter(List<DataVO> data) {
        this.mData = data;
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View contentView) {
            super(contentView);
            textView = (TextView) contentView.findViewById(R.id.textView);

        }
    }

    public void loadFirst() {
        page = 1;
        getDatasByPage(page);
        Log.e("TTT", "loadFirst " + page);
    }

    public void loadNextPage() {
        page++;
        getDatasByPage(page);
        Log.e("TTT", "loadNextPage " + page);
    }

    //用来造数据的 = =
    private void getDatasByPage(int pageIndex){
        //获取数据的方法中可以加入网络判断，从而实现数据缓存
        //---
        if(mData == null)
            mData = new ArrayList<>();
        else
            mData.clear();

        //造假数据,10项分页
        for(int i =0; i< 10 * pageIndex; i++){
            DataVO data = new DataVO();
            data.setNum(i);
            mData.add(data);
        }
        //更新界面
        notifyDataSetChanged();
    }

}
