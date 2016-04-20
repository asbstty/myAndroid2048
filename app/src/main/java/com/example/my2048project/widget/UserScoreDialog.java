package com.example.my2048project.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.my2048project.R;
import com.example.my2048project.adapter.UserScoreAdapter;
import com.example.my2048project.db.bean.UserScoreBean;

import java.util.List;

/**
 * Created by 854638 on 2016/4/20.
 */
public class UserScoreDialog extends Dialog{

    private RecyclerView mRecyclerView;

    private UserScoreAdapter mUserScoreAdapter;

    private Button mBtnOk;

    private TextView mTxtHint;

    private boolean hasHero;

    public UserScoreDialog(Context context, boolean hasHero) {
        super(context, R.style.dialog);
        this.hasHero = hasHero;
        setContentView(R.layout.user_score_rank);
        initView();
        initProcess();
        initListener();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        lp.width = dm.widthPixels - 182;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(lp);

    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mUserScoreAdapter = new UserScoreAdapter(getContext());
        mBtnOk = (Button) findViewById(R.id.btnOk);
        mTxtHint = (TextView) findViewById(R.id.rankHint);
    }

    public void initProcess() {
        if(hasHero) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTxtHint.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mUserScoreAdapter);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            mTxtHint.setVisibility(View.VISIBLE);
        }
    }

    public void initListener() {
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setData(List<UserScoreBean> userScoreBeanList) {
        mUserScoreAdapter.setData(userScoreBeanList);
    }

}
