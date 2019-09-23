/*
The MIT License (MIT)
[OSI Approved License]
The MIT License (MIT)

Copyright (c) 2014 Daniel Glasson

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

package com.cyclicsoft.offlinereversegeocoder.reversegeocoder;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Computes the reverse geo coding of the latitude and the longitude
 * provided by the user in the background thread.
 *
 */
public final class KDTreeGeoCoder extends AsyncTask<Double, Void,GeoName> {

    private static final String TAG = "==> KDTreeGeoCoder";

    private boolean enableDebugging = true;
    private Context context;
    private int requestCode;
    private boolean hasDialog = false;
    private OnGeoCodeCompleteListener mListener;
    private double latitude, longitude;
    private ProgressDialog mDialog;

    public KDTreeGeoCoder(int requestCode, @Nullable OnGeoCodeCompleteListener callback) {
        this.requestCode = requestCode;
        this.mListener = callback;
    }

    public KDTreeGeoCoder(@NonNull Context context, int requestCode, boolean hasDialog, OnGeoCodeCompleteListener callback) {
        this(requestCode,callback);
        this.context = context;
        this.hasDialog = hasDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(this.hasDialog){
            this.mDialog = new ProgressDialog(this.context);
            this.mDialog.setMessage("Decoding location...");
            this.mDialog.setCancelable(false);
            this.mDialog.setCanceledOnTouchOutside(false);
            this.mDialog.show();
        }
    }

    @Override
    protected GeoName doInBackground(Double... latLongs) {
        try{

            this.latitude = latLongs[0];
            this.longitude = latLongs[1];
            return ReverseGeoCoder.getInstance().nearestPlace(this.latitude,this.longitude);

        }catch (ArrayIndexOutOfBoundsException e){
            if(this.enableDebugging) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: ArrayIndexOutOfBounds : " + e.getMessage());
            }
            return null;
        }catch (NullPointerException e){
            if(this.enableDebugging) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: NullPointer : " + e.getMessage());
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(GeoName geoName) {
        super.onPostExecute(geoName);
        if(this.mDialog != null && this.mDialog.isShowing()){
            this.mDialog.dismiss();
        }
        String geoStr;
        if(geoName != null){
            if(this.enableDebugging){
                Log.d(TAG, "onPostExecute: "+geoName.toString());
            }
            geoStr = geoName.getName()+", "+geoName.getCountryCode();
        }
        else{
            if(this.enableDebugging){
                Log.d(TAG, "onPostExecute: Reverse Geo Coder returned null!");
            }
            geoStr = "Nepal";
        }

        if(this.mListener != null){
            this.mListener.onGeoCodeComplete(this.requestCode,geoStr);
        }

    }

    public void debugging(boolean enable){
        this.enableDebugging = enable;
    }

}
