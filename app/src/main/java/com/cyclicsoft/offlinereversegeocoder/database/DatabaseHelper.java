/**
 * @author BJIT
 * Helper class for database controller
 */
package com.cyclicsoft.offlinereversegeocoder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DataBaseHelper";
    private SQLiteDatabase mDataBase;

    public DatabaseHelper(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
    }

    /**
     * Upgrade the tables
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
}
