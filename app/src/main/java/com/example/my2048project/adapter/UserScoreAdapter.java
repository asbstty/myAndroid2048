package com.example.my2048project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.my2048project.R;
import com.example.my2048project.base.BaseRecyclerViewAdapter;
import com.example.my2048project.db.bean.UserScoreBean;


/**
 * Created by 854638 on 2016/4/20.
 */
public class UserScoreAdapter extends BaseRecyclerViewAdapter<UserScoreBean> {
    public UserScoreAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.user_score, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder mv = (MyHolder) holder;
        UserScoreBean userScoreBean = getList().get(position);
        if(null != userScoreBean) {
            int score = userScoreBean.score;
            String userName = userScoreBean.userName;
            mv.mTxtScore.setText(score + "分");
            mv.mTxtUserName.setText(userName);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public TextView mTxtUserName;

        public TextView mTxtScore;

        public MyHolder(View itemView) {
            super(itemView);
            mTxtUserName = (TextView) itemView.findViewById(R.id.txtUserName);
            mTxtScore = (TextView) itemView.findViewById(R.id.txtScore);
        }
    }
}
