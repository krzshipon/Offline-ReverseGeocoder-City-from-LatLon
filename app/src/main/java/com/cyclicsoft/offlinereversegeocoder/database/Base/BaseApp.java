/**
 * @author CyclicSoft
 */
package com.cyclicsoft.offlinereversegeocoder.database.Base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.cyclicsoft.offlinereversegeocoder.R;
import com.cyclicsoft.offlinereversegeocoder.database.DatabaseConstants;
import com.cyclicsoft.offlinereversegeocoder.database.DatabaseController;
import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.ReverseGeoCoder;

import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipInputStream;


// Put this in the manifest file
// android:name="com.cyclicsoft.eubus.base.BaseApplication">
public class BaseApp extends Application {
    // private ArrayList<String> mLocalList = new ArrayList<String>();
    private final String TAG = getClass().getSimpleName();

    public void onCreate() {
        super.onCreate();
        this.init();
    }

    public void onTerminate() {
        super.onTerminate();
    }

    /**
     * We need a folder, localization, or other data that should be available in whole application.
     */
    @SuppressLint("StaticFieldLeak")
    public void init() {
        // Create Download Folder.
       // File file = new File(com.cyclicsoft.eubus.util.Constants.PATH_DOWNLOAD);
       // boolean success = file.mkdirs();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseController dbController = DatabaseController.getInstance();
                dbController.createDatabase(getApplicationContext(), DatabaseConstants.DATABASE_NAME, DatabaseConstants.DATABASE_VERSION);
                //Create Tables here

                initReverseGeoCoder();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initReverseGeoCoder() {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(getResources().openRawResource(R.raw.city_coordinates_list_locality));
            ReverseGeoCoder.initialize(zipInputStream, true);
        } catch (IOException e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
