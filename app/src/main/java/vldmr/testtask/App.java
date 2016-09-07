package vldmr.testtask;

import android.app.Application;
import android.content.SharedPreferences;


import java.util.Random;

import vldmr.testtask.database.DaoMaster;
import vldmr.testtask.database.DaoSession;
import vldmr.testtask.database.Product;

/**
 * Created by Vladimir on 06.09.2016.
 */
public class App  extends Application{

    DaoMaster.DevOpenHelper helper;
    @Override
    public void onCreate() {
        super.onCreate();
        helper= new DaoMaster.DevOpenHelper(this, "db1", null);
    }


}
