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
import android.util.Log;

import com.cyclicsoft.offlinereversegeocoder.Constants;
import com.cyclicsoft.offlinereversegeocoder.reversegeocoder.kdtree.KDNodeComparator;
import java.util.Comparator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

/**
 *
 * This class works with a placenames files from http://download.geonames.org/export/dump/
 */

public class GeoName extends KDNodeComparator<GeoName> {

    public static final String TAG = GeoName.class.getSimpleName();
    private String name;
    private double latitude;
    private double longitude;
    private double point[] = new double[3]; // The 3D coordinates of the point
    private String countryCode;
    private String jaName;
    private String zhName;
    private String koName;
    private String itName;
    private String deName;
    private String esName;
    private String frName;


    GeoName(String data) {
        String[] names = data.split("#");
        name = names[3];
        latitude = Double.parseDouble(names[0]);
        longitude = Double.parseDouble(names[1]);
        setPoint();
        countryCode = names[2];
        jaName = names[4];
        zhName = names[5];
        koName = names[6];
        itName = names[7];
        deName = names[8];
        esName = names[9];
        frName = names[10];
    }

    GeoName(Double latitude, Double longitude) {
        name = countryCode = "Search";
        this.latitude = latitude;
        this.longitude = longitude;
        setPoint();
    }

    private void setPoint() {
        point[0] = cos(toRadians(latitude)) * cos(toRadians(longitude));
        point[1] = cos(toRadians(latitude)) * sin(toRadians(longitude));
        point[2] = sin(toRadians(latitude));
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double[] getPoint() {
        return point;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getJaName() {
        return jaName;
    }

    public String getZhName() {
        return zhName;
    }

    public String getKoName() {
        return koName;
    }

    public String getItName() {
        return itName;
    }

    public String getDeName() {
        return deName;
    }

    public String getEsName() {
        return esName;
    }

    public String getFrName() {
        return frName;
    }

    @Override
    public String toString() {
        return "GeoName{" +
                "name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", jaName='" + jaName + '\'' +
                ", zhName='" + zhName + '\'' +
                ", koName='" + koName + '\'' +
                ", itName='" + itName + '\'' +
                ", deName='" + deName + '\'' +
                ", esName='" + esName + '\'' +
                ", frName='" + frName + '\'' +
                '}';
    }

    @Override
    protected double squaredDistance(GeoName other) {
        double x = this.point[0] - other.point[0];
        double y = this.point[1] - other.point[1];
        double z = this.point[2] - other.point[2];
        return (x*x) + (y*y) + (z*z);
    }

    @Override
    protected double axisSquaredDistance(GeoName other, int axis) {
        double distance = point[axis] - other.point[axis];
        return distance * distance;
    }

    @Override
    protected Comparator<GeoName> getComparator(int axis) {
        return GeoNameComparator.values()[axis];
    }

    protected static enum GeoNameComparator implements Comparator<GeoName> {
        x {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[0], b.point[0]);
            }
        },
        y {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[1], b.point[1]);
            }
        },
        z {
            @Override
            public int compare(GeoName a, GeoName b) {
                return Double.compare(a.point[2], b.point[2]);
            }
        };
    }


    public String getCityNameByLanguageCode(String countryCode) {
        Log.d(TAG, countryCode);
        switch (countryCode) {
            case Constants.CODE_LANGUAGE_JA:
                return getJaName();
            case Constants.CODE_LANGUAGE_ZH:
                return getZhName();
            case Constants.CODE_LANGUAGE_KO:
                return getKoName();
            case Constants.CODE_LANGUAGE_IT:
                return getItName();
            case Constants.CODE_LANGUAGE_DE:
                return getDeName();
            case Constants.CODE_LANGUAGE_ES:
                return getEsName();
            case Constants.CODE_LANGUAGE_FR:
                return getFrName();
            default:
                return getName();
        }
    }
}
