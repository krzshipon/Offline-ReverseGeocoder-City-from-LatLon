/**
 * @author Shipon
 * A Common Object can be used
 * universaly no need to use any other model
 */
package com.cyclicsoft.offlinereversegeocoder.database.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class SObject extends JSONObject {

    private String ObjectID = "ObjectID";
    private String TAG = getClass().getSimpleName();

    /**
     * The id of this object
     * @param id
     */
    public SObject(long id) {
        this.setValue(ObjectID, id);
    }

    public SObject(String json) throws JSONException {
        super(json);
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param id the long value of the name.
     */
    public void setValue(String name, long id) {
        try {
            this.put(name, id);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param value the string value of the name.
     */
    public void setValue(String name, String value) {
        try {
            this.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * The value to be set for name.
     * @param name the key.
     * @param value the string value of the name.
     */
    public void setValue(String name, Object value) {
        try {
            this.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }
    }

    /**
     * Return the value for given name, return null if no key found.
     * @param name
     * @return
     */
    public Object getValue(String name) {
        try {
            return this.get(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }

        return null;
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public String getString(String name) {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }

        return null;
    }

    /**
     * @return the ObjectID of this object.
     */
    public long getId() {
        long id = (Long) this.getValue(ObjectID);

        return id;
    }

    /**
     * Set the id of this object.
     * @param id
     */
    public void setId(long id) {
        this.setValue(ObjectID, id);
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public long getLong(String name) {
        try {
            return super.getLong(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }

        // Invalid value
        return -999999999;
    }

    /**
     * Return the value for given name, return null if no key found.
     */
    public int getInt(String name) {
        try {
            return super.getInt(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }

        // Invalid value
        return -999999999;
    }

    /**
     * Return the boolean value for given name, return false if no key found.
     */
    public boolean getBoolean(String name) {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.d(TAG, e.getMessage());
        }

        return false;
    }
}

