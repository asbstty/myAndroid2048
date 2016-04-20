package com.example.my2048project.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.my2048project.db.bean.UserScoreBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 866315 on 2015/10/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static DatabaseHelper mInstance;
    private static final String DB_NAME = "fv_game.db";
    private static int DB_VERSION = 1;

    /**
     * 对dao进行缓存
     */
    private Map<String, Dao> daoMaps = new HashMap<>();

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    /**
     * 单例模式，拿到helper对象
     *
     * @param context
     * @return
     */
    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DatabaseHelper.class) {
                if (mInstance == null)
                    mInstance = new DatabaseHelper(context);
            }
        }

        return mInstance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {

        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daoMaps.containsKey(className)) {
            dao = daoMaps.get(className);
        } else {
            dao = super.getDao(clazz);
            daoMaps.put(className, dao);
        }
        return dao;
    }

    public Dao getMapDao(Class clazz) {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daoMaps.containsKey(className)) {
            dao = daoMaps.get(className);
        }
        return dao;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
//            TableUtils.createTable(connectionSource, TestBean.class);
            TableUtils.createTable(connectionSource, UserScoreBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {


            //保留以前数据，添加本地数据表字段
            //Dao dao = getDao(TestBean.class);
            // dao.executeRaw("ALTER TABLE `sfnp_code_test` ADD COLUMN sign;");
//            TableUtils.dropTable(connectionSource, TestBean.class, true);
//            TableUtils.dropTable(connectionSource, SystemMessageBean.class, true);
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void close() {
        super.close();
        for (String key : daoMaps.keySet()) {
            Dao dao = daoMaps.get(key);
            dao = null;
        }

    }
}
