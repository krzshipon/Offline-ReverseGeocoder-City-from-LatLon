/**
 * @author CyclicSoft
 */
package com.cyclicsoft.offlinereversegeocoder.database.Base;

import android.app.Application;
import android.os.AsyncTask;

import com.cyclicsoft.offlinereversegeocoder.database.DatabaseConstants;
import com.cyclicsoft.offlinereversegeocoder.database.DatabaseController;


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
                DatabaseController databaseConterller = DatabaseController.getInstance();
                databaseConterller.createDatabase(getApplicationContext(), DatabaseConstants.DATABASE_NAME, DatabaseConstants.DATABASE_VERSION);
                //Create Tables here

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
