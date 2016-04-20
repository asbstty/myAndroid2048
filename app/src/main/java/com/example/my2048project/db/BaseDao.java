package com.example.my2048project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 866315 on 2015/10/19.
 */
public abstract class BaseDao<T> {
    protected Dao mDao;
    protected Context mContext;


    public BaseDao(Context context, Class<T> mBean) {
        mContext = context;

        try {
            mDao = DatabaseHelper.getInstance(context).getDao(mBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<T> queryAll() {
        try {

            return mDao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public boolean add(T bean) {

        int result = -1;
        try {
            result = mDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return result > 0 ? true : false;
        }
    }

    public boolean update(T bean) {
        try {
            int returnId = -1;

            returnId = mDao.update(bean);
            return returnId > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }


    }

    public boolean delete(T bean) {
        try {
            return mDao.delete(bean) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 批量刪除数据
     */
    public boolean deleteList(List<T> beans) {
        boolean delFlag = true;
        SQLiteDatabase db = DatabaseHelper.getInstance(mContext.getApplicationContext()).getWritableDatabase();
        try {
            db.beginTransaction();
            delFlag = mDao.delete(beans) > 0;
            db.setTransactionSuccessful();
        } catch (SQLException e) {
        } finally {
            db.endTransaction();
            return delFlag;
        }
    }

    /**
     * 批量插入数据
     * 在ormlite未添加事务，所以在批量插入或者删除数据时，需主动添加事务,提高效率
     */
    public boolean addList(List<T> list) {

        SQLiteDatabase db = DatabaseHelper.getInstance(mContext.getApplicationContext()).getReadableDatabase();
        db.beginTransaction();
        Boolean isSuccess = true;
        for (T bean : list) {
            isSuccess = add(bean);
            if (!isSuccess)
                break;
        }
        if (isSuccess) {
            //有插入失败的数据，数据回滚
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        return isSuccess;

    }


}
