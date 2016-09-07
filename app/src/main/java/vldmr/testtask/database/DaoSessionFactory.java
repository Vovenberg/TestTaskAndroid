package vldmr.testtask.database;

import android.content.Context;

/**
 * Created by Vladimir on 07.09.2016.
 */
public class DaoSessionFactory {
    private static DaoSession daoSession;

    public static DaoSession getDaoSesion(Context context){
        DaoMaster.DevOpenHelper helper= new DaoMaster.DevOpenHelper(context, "db1", null);
        daoSession =new DaoMaster(helper.getWritableDatabase()).newSession();
        return daoSession;
    }

    public static void releaseSesion(){
        daoSession.clear();
    }
}
