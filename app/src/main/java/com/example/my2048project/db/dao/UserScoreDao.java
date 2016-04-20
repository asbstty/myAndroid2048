package com.example.my2048project.db.dao;

import android.content.Context;

import com.example.my2048project.db.BaseDao;
import com.example.my2048project.db.DatabaseHelper;
import com.example.my2048project.db.bean.UserScoreBean;
import com.example.my2048project.utils.LogUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 854638 on 2016/4/20.
 */
public class UserScoreDao extends BaseDao {

    private static UserScoreDao userScoreDao;

    public static UserScoreDao getInstance(Context context) {
        if (userScoreDao == null || DatabaseHelper.getInstance(context).getMapDao(UserScoreBean.class) == null) {
            userScoreDao = new UserScoreDao(context);
            LogUtils.d("mDao1111 = " + userScoreDao.mDao.toString());
        }
        LogUtils.d("mDao = " + userScoreDao.mDao.toString());
        return userScoreDao;
    }

    public UserScoreDao(Context context) {
        super(context, UserScoreBean.class);
    }

    public List<UserScoreBean> getNBGamerByScore(int gameType) {
        List<UserScoreBean> result = null;
        try {
            return result = mDao.queryBuilder().orderBy("score", false).limit(5L).where().eq("gameType", gameType).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
