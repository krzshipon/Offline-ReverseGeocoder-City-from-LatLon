package com.cyclicsoft.offlinereversegeocoder.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.cyclicsoft.offlinereversegeocoder.database.CSDBModel;
import com.cyclicsoft.offlinereversegeocoder.database.DatabaseController;
import com.cyclicsoft.offlinereversegeocoder.database.SEntity;
import com.cyclicsoft.offlinereversegeocoder.database.UtilsCursor;
import com.cyclicsoft.offlinereversegeocoder.database.observer.ResponseObject;
import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.GeoName;

import org.json.JSONException;

public class GeoNameDBModel extends CSDBModel {

    private static GeoNameDBModel mGeoNameDBModel;
    private String TAG = getClass().getSimpleName();

    private GeoNameDBModel(Context context) {
        super(context);
    }

    public static GeoNameDBModel getInstance(Context context) {
        if (mGeoNameDBModel == null) {
            mGeoNameDBModel = new GeoNameDBModel(context);
        }
        return mGeoNameDBModel;
    }

    /**
     * Return the create table script
     *
     * @return
     */
    public static String getCreateTable() {

        return "CREATE TABLE IF NOT EXISTS " + Table.TABLE_NAME + "("
                + Table.INT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ","
                + Table.DOUBLE_LAT + " REAL" + ","
                + Table.DOUBLE_LON + " REAL" + ","
                + Table.TEXT_COUNTRY_CODE + " TEXT"+ ","
                + Table.TEXT_EN_NAME + " TEXT"+ ","
                + Table.TEXT_JA_NAME + " TEXT"+ ","
                + Table.TEXT_ZH_NAME + " TEXT"+ ","
                + Table.TEXT_KO_NAME + " TEXT"+ ","
                + Table.TEXT_DE_NAME + " TEXT"+ ","
                + Table.TEXT_ES_NAME + " TEXT"+ ","
                + Table.TEXT_IT_NAME + " TEXT"+ ","
                + Table.TEXT_FR_NAME + " TEXT"
                + ");";
    }

    public void createTable() {
        mDatabaseController.execSQL(getCreateTable());
    }

    /**
     * Return the drop table script
     *
     * @return
     */
    public static String getDropTable() {
        return "DROP TABLE IF EXISTS " + Table.TABLE_NAME;
    }

    @Override
    protected void loadData(Cursor cursor) {
        // TODO Auto-generated method stub
        clear(false);
        if (cursor == null) return;

        // looping through all rows and adding to list
        // Add dummy Entity to start the list from index 1, Because header view is index number 0
        this.add(new BadgeHistoryEntity(-1));
        if (cursor.moveToFirst()) {

            do {
                BadgeHistoryEntity entity = new BadgeHistoryEntity(UtilsCursor.getIntFromCursor(Table.INT_ID, cursor));
                // LIKE ANY ONE ADD ITEMS..
                entity.setValue(Table.DOUBLE_LAT, UtilsCursor.getFloatFromCursor(Table.DOUBLE_LAT, cursor));
                entity.setValue(Table.DOUBLE_LON, UtilsCursor.getFloatFromCursor(Table.DOUBLE_LON, cursor));
                entity.setValue(Table.TEXT_COUNTRY_CODE, UtilsCursor.getStringFromCursor(Table.TEXT_COUNTRY_CODE, cursor));
                entity.setValue(Table.TEXT_EN_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_EN_NAME, cursor));
                entity.setValue(Table.TEXT_JA_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_JA_NAME, cursor));
                entity.setValue(Table.TEXT_ZH_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_ZH_NAME, cursor));
                entity.setValue(Table.TEXT_KO_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_KO_NAME, cursor));
                entity.setValue(Table.TEXT_DE_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_DE_NAME, cursor));
                entity.setValue(Table.TEXT_FR_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_FR_NAME, cursor));
                entity.setValue(Table.TEXT_IT_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_IT_NAME, cursor));
                entity.setValue(Table.TEXT_ES_NAME, UtilsCursor.getStringFromCursor(Table.TEXT_ES_NAME, cursor));

                // Adding contact to list
                this.add(entity);

            } while (cursor.moveToNext());
        }

        // Must close this cursor
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    public long insert(SEntity entity) {
        BadgeHistoryEntity item = (BadgeHistoryEntity) entity;
        ContentValues cv = new ContentValues();
        try {
            cv.put(Table.DOUBLE_LAT, item.getDouble(Table.DOUBLE_LAT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cv.put(Table.DOUBLE_LON, item.getDouble(Table.DOUBLE_LON));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cv.put(Table.TEXT_COUNTRY_CODE, item.getString(Table.TEXT_COUNTRY_CODE));
        cv.put(Table.TEXT_EN_NAME, item.getString(Table.TEXT_EN_NAME));
        cv.put(Table.TEXT_JA_NAME, item.getString(Table.TEXT_JA_NAME));
        cv.put(Table.TEXT_ZH_NAME, item.getString(Table.TEXT_ZH_NAME));
        cv.put(Table.TEXT_KO_NAME, item.getString(Table.TEXT_KO_NAME));
        cv.put(Table.TEXT_ES_NAME, item.getString(Table.TEXT_ES_NAME));
        cv.put(Table.TEXT_DE_NAME, item.getString(Table.TEXT_DE_NAME));
        cv.put(Table.TEXT_FR_NAME, item.getString(Table.TEXT_FR_NAME));
        cv.put(Table.TEXT_IT_NAME, item.getString(Table.TEXT_IT_NAME));

        long row = mDatabaseController.insert(Table.TABLE_NAME, cv);
        return row;
    }

    @Override
    public int update(SEntity entity) {
        BadgeHistoryEntity item = (BadgeHistoryEntity) entity;
        ContentValues cv = new ContentValues();

        try {
            cv.put(Table.DOUBLE_LAT, item.getDouble(Table.DOUBLE_LAT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            cv.put(Table.DOUBLE_LON, item.getDouble(Table.DOUBLE_LON));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cv.put(Table.TEXT_COUNTRY_CODE, item.getString(Table.TEXT_COUNTRY_CODE));
        cv.put(Table.TEXT_EN_NAME, item.getString(Table.TEXT_EN_NAME));
        cv.put(Table.TEXT_JA_NAME, item.getString(Table.TEXT_JA_NAME));
        cv.put(Table.TEXT_ZH_NAME, item.getString(Table.TEXT_ZH_NAME));
        cv.put(Table.TEXT_KO_NAME, item.getString(Table.TEXT_KO_NAME));
        cv.put(Table.TEXT_ES_NAME, item.getString(Table.TEXT_ES_NAME));
        cv.put(Table.TEXT_DE_NAME, item.getString(Table.TEXT_DE_NAME));
        cv.put(Table.TEXT_FR_NAME, item.getString(Table.TEXT_FR_NAME));
        cv.put(Table.TEXT_IT_NAME, item.getString(Table.TEXT_IT_NAME));

        String[] whereArgs = new String[]{DatabaseController.toString(entity.getId())};
        String where = Table.INT_ID + "=?";

        int row = mDatabaseController.update(Table.TABLE_NAME, cv, where, whereArgs);
        return row;
    }

    @Override
    public void query() {
        Cursor c = mDatabaseController.rawQuery("SELECT * FROM " + Table.TABLE_NAME +
                " WHERE id IN (SELECT MAX(id) FROM " + Table.TABLE_NAME + " GROUP BY " + Table.DOUBLE_LAT + ") ORDER BY " + Table.TEXT_COUNTRY_CODE + Table.DESC_STRING, null);
        this.loadData(c);
    }

    @Override
    protected ResponseObject doExecute() {
        query();
        return null;
    }


    public long insertGeoName(GeoName geoName) {
        if(geoName == null){
            return -1;
        }
        BadgeHistoryEntity entity = new BadgeHistoryEntity(geoName.getLatitude(),
                geoName.getLongitude(),
                geoName.getCountryCode(),
                geoName.getName(),
                geoName.getJaName(),
                geoName.getZhName(),
                geoName.getKoName(),
                geoName.getDeName(),
                geoName.getEsName(),
                geoName.getItName(),
                geoName.getFrName());
        Log.d(TAG, geoName.getName());
        return insert(entity);
    }


    public boolean isAcquiredBadge(int badgeType) {
        String[] selectionArgs = new String[]{Integer.toString(badgeType)};
        Cursor cursor = mDatabaseController.query(Table.TABLE_NAME, null, Table.DOUBLE_LAT + " =?", selectionArgs, null, null, null);
        boolean exists = false;
        if (cursor != null) {
            exists = (cursor.getCount() > 0);
            cursor.close();
        }
        return exists;
    }

    public static class Table {
        // TABLE NAME
        public static final String TABLE_NAME = "GeoNameTable";
        // TABLE COLUMNS
        public static final String INT_ID = "id";
        public static final String DOUBLE_LAT = "DOUBLE_LAT";
        public static final String DOUBLE_LON = "DOUBLE_LON";
        public static final String TEXT_COUNTRY_CODE = "TEXT_COUNTRY_CODE";
        public static final String TEXT_EN_NAME = "TEXT_EN_NAME";
        public static final String TEXT_JA_NAME = "TEXT_JA_NAME";
        public static final String TEXT_ZH_NAME = "TEXT_ZH_NAME";
        public static final String TEXT_KO_NAME = "TEXT_KO_NAME";
        public static final String TEXT_DE_NAME = "TEXT_DE_NAME";
        public static final String TEXT_ES_NAME = "TEXT_ES_NAME";
        public static final String TEXT_IT_NAME = "TEXT_IT_NAME";
        public static final String TEXT_FR_NAME = "TEXT_FR_NAME";

        // Sqlite command
        public static final String DESC_STRING = " DESC";

    }

    // TABLE ROW/ENTITY
    public static class BadgeHistoryEntity extends SEntity {

        BadgeHistoryEntity(double lat,
                           double lon,
                           String countryCode,
                           String enName,
                           String jaName,
                           String zhName,
                           String koName,
                           String deName,
                           String esName,
                           String itName,
                           String frName) {
            super(-1);
            this.setValue(Table.DOUBLE_LAT, lat);
            this.setValue(Table.DOUBLE_LON, lon);
            this.setValue(Table.TEXT_COUNTRY_CODE, countryCode);
            this.setValue(Table.TEXT_EN_NAME, enName);
            this.setValue(Table.TEXT_JA_NAME, jaName);
            this.setValue(Table.TEXT_ZH_NAME, zhName);
            this.setValue(Table.TEXT_KO_NAME, koName);
            this.setValue(Table.TEXT_DE_NAME, deName);
            this.setValue(Table.TEXT_ES_NAME, esName);
            this.setValue(Table.TEXT_IT_NAME, itName);
            this.setValue(Table.TEXT_FR_NAME, frName);
        }
        BadgeHistoryEntity(long id) {
            super(id);
        }
    }

}