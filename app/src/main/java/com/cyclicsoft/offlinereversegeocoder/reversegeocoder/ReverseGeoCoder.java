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


import android.content.Context;
import android.content.res.AssetManager;


import androidx.annotation.Nullable;

import com.cyclicsoft.offlinereversegeocoder.model.GeoNameDBModel;
import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.kdtree.KDTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Uses KD-trees to quickly find the nearest point
 * ;
 */
public class ReverseGeoCoder {

    private static Context mContext;
    private final String TAG = this.getClass().getSimpleName();
    private static ReverseGeoCoder reverseGeoCoder = null;

    KDTree<GeoName> kdTree;

    // Get placenames from http://download.geonames.org/export/dump/

    /**
     * Parse the zipped geonames file.
     *
     * @param zippedPlacednames a {@link ZipInputStream} zip file downloaded from http://download.geonames.org/export/dump/; can not be null.
     * @param majorOnly         only include major cities in KD-tree.
     * @throws IOException if there is a problem reading the {@link ZipInputStream}.
     */
    private ReverseGeoCoder(ZipInputStream zippedPlacednames, boolean majorOnly) throws IOException {
        //depending on which zip file is given,
        //country specific zip files have read me files
        //that we should ignore
        ZipEntry entry;
        do {
            entry = zippedPlacednames.getNextEntry();
        } while (entry.getName().equals("readme.txt"));

        createKdTree(zippedPlacednames, majorOnly);

    }

    /**
     * Parse the raw text geo-names file.
     *
     * @param placenames the text file downloaded from http://download.geonames.org/export/dump/; can not be null.
     * @param majorOnly  only include major cities in KD-tree.
     * @throws IOException if there is a problem reading the stream.
     */
    private ReverseGeoCoder(InputStream placenames, boolean majorOnly) throws IOException {
        createKdTree(placenames, majorOnly);
    }

    /**
     * Initialize the reverse geo-coder for the application
     *
     * @param assetManager
     * @param assetPath
     * @param majorOnly
     * @throws IOException
     */
    public static void initialize(AssetManager assetManager, String assetPath, boolean majorOnly) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(assetManager.open(assetPath));
        if (reverseGeoCoder == null) {
            reverseGeoCoder = new ReverseGeoCoder(zipInputStream, majorOnly);
        }
    }

    /**
     * Initialize the reverse geo-coder for the application.
     *
     * @param zippedPlacednames
     * @param majorOnly
     * @throws IOException
     */
    public static void initialize(ZipInputStream zippedPlacednames, boolean majorOnly) throws IOException {
        if (reverseGeoCoder == null) {
            reverseGeoCoder = new ReverseGeoCoder(zippedPlacednames, majorOnly);
        }
    }

    public static void initialize(InputStream placenames, boolean majorOnly) throws IOException {
        if (reverseGeoCoder == null) {
            reverseGeoCoder = new ReverseGeoCoder(placenames, majorOnly);
        }
    }

    /**
     * Gets the instance of the geocoder.
     * <br>
     * This may return null, unless the class has been initialized at the entry point of the application
     * by either {@linkplain #initialize(InputStream, boolean)} or {@linkplain #initialize(ZipInputStream, boolean)}
     *
     * @return
     */
    @Nullable
    public static ReverseGeoCoder getInstance(Context context) {
        mContext = context;
        return reverseGeoCoder;
    }

    private void createKdTree(InputStream placenames, boolean majorOnly)
            throws IOException {
        ArrayList<GeoName> arPlaceNames;
        arPlaceNames = new ArrayList<GeoName>();
        // Read the geonames file in the directory
        BufferedReader in = new BufferedReader(new InputStreamReader(placenames));
        String str;
        try {
            int count = 0;
            while ((str = in.readLine()) != null) {
//                PTLog.d(TAG, "line--> "+count);
                count++;
                GeoName newPlace = new GeoName(str);
                GeoNameDBModel.getInstance(mContext).insertGeoName(newPlace);
                arPlaceNames.add(newPlace);
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            in.close();
        }
        kdTree = new KDTree<GeoName>(arPlaceNames);
    }

    public GeoName nearestPlace(double latitude, double longitude) {
        return kdTree.findNearest(new GeoName(latitude, longitude));
    }

}
