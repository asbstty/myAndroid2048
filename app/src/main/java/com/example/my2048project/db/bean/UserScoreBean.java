package com.example.my2048project.db.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by 854638 on 2016/4/20.
 */
@DatabaseTable(tableName = "userScoreBean")
public class UserScoreBean implements Parcelable {

    @DatabaseField(generatedId = true)
    public int id = 0;
    @DatabaseField
    public String userName = "";
    @DatabaseField
    public int gameType = 0;//（0：2048, 1：俄罗斯方块, 2:打飞机，3：...）
    @DatabaseField
    public int score = 0;//玩家得分
    @DatabaseField
    public String createTime = "";//分数创建时间

    public UserScoreBean() {

    }

    public UserScoreBean(String userName, int gameType, int score, String createTime) {
        this.userName = userName;
        this.gameType = gameType;
        this.score = score;
        this.createTime = createTime;
    }

    protected UserScoreBean(Parcel in) {
    }

    public static final Creator<UserScoreBean> CREATOR = new Creator<UserScoreBean>() {
        @Override
        public UserScoreBean createFromParcel(Parcel in) {
            return new UserScoreBean(in);
        }

        @Override
        public UserScoreBean[] newArray(int size) {
            return new UserScoreBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
