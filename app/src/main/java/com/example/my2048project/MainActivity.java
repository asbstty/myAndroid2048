package com.example.my2048project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2048project.db.bean.UserScoreBean;
import com.example.my2048project.db.dao.UserScoreDao;
import com.example.my2048project.utils.LogUtils;
import com.example.my2048project.view.My2048View;
import com.example.my2048project.widget.RankDialog;
import com.example.my2048project.widget.UserScoreDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private My2048View my2048View;

    private TextView mTxtScore;

    private Button mBtnRestart, mBtnRank;

    private int nowScore;

    private RankDialog rankDialog;

    private UserScoreDao userScoreDao;

    private UserScoreDialog mUserScoreDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my2048View = (My2048View) findViewById(R.id.gameView);
        mTxtScore = (TextView) findViewById(R.id.txtScore);
        mBtnRestart = (Button) findViewById(R.id.btnRestart);
        mBtnRank = (Button) findViewById(R.id.btnRank);
        nowScore = 0;
        mTxtScore.setText("0");
        initView();
        initProcess();
    }

    public void initView() {
    }

    public void initProcess() {
        userScoreDao = UserScoreDao.getInstance(this);
        my2048View.SetOnGamePlayListener(new My2048View.OnGamePlayListener() {
            @Override
            public void onScore(int score) {
                nowScore += score;
                mTxtScore.setText(nowScore + "");
            }

            @Override
            public void onFail() {
                Toast.makeText(MainActivity.this, "you lost!!!", Toast.LENGTH_SHORT).show();
                boolean caninDb = canInDb(nowScore);
                if(caninDb) {
                    String mStrScore = String.valueOf(nowScore);
                    rankDialog = new RankDialog(MainActivity.this, mStrScore);
                    rankDialog.setCancelable(false);
                    rankDialog.show();
                    rankDialog.setOnYesOrNotListener(new RankDialog.OnYesOrNotListener() {
                        @Override
                        public void onSure(String userName) {
                            Date date = new Date();
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String dateStr = df.format(date);
                            UserScoreBean userScoreBean = new UserScoreBean(userName, my2048View.gameType, nowScore, dateStr);
                            boolean indb = userScoreDao.add(userScoreBean);
                            Log.i("indb", String.valueOf(indb));
                        }

                        @Override
                        public void onCancel() {
                            LogUtils.i("取消存入数据库");
                        }
                    });
                }
            }
        });
        mBtnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my2048View.restart();
            }
        });
        mBtnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<UserScoreBean> userScoreBeans = getHighestScoreUser(my2048View.gameType);
                if(userScoreBeans.size() > 0) {
                    mUserScoreDialog = new UserScoreDialog(MainActivity.this, true);
                    mUserScoreDialog.setData(userScoreBeans);
                    mUserScoreDialog.show();
                } else {
                    mUserScoreDialog = new UserScoreDialog(MainActivity.this, false);
                    mUserScoreDialog.show();
                }
            }
        });
    }

    public List<UserScoreBean> getHighestScoreUser(int gameType) {
        List<UserScoreBean> userScoreBeans = new ArrayList<UserScoreBean>();
        userScoreBeans = userScoreDao.getNBGamerByScore(gameType);
        return userScoreBeans;
    }

    public boolean canInDb(int score) {
        List<UserScoreBean> userScoreBeanList = userScoreDao.getNBGamerByScore(my2048View.gameType);
        int length = userScoreBeanList.size();
        Log.i("indb", String.valueOf(length));
        if(length < 5)
            return true;
        else {
            UserScoreBean userScoreBean = userScoreBeanList.get(length - 1);
            int lowScore = userScoreBean.score;
            if(score > lowScore)
                return true;
            else
                return false;
        }
    }

}
