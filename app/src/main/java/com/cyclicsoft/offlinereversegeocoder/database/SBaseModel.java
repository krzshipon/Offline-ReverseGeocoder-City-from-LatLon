/**
 * @author Shipon
 * Base class for all adapters as well as models, all models/adapters should extends this class.
 */
package com.cyclicsoft.offlinereversegeocoder.database;

import android.content.Context;

import com.cyclicsoft.offlinereversegeocoder.database.model.SObject;
import com.cyclicsoft.offlinereversegeocoder.database.observer.NotifyObserver;
import com.cyclicsoft.offlinereversegeocoder.database.observer.ResponseObject;

import java.util.ArrayList;


public abstract class SBaseModel extends Object {
    // Unique name of the adapter/model
    protected String mName = "SBaseModel";
    // The resulting values of this adapter
    protected ArrayList<SObject> mListItem = new ArrayList<SObject>();
    // This is for invalid item id
    protected long INVALID_ITEM_ID = Long.MIN_VALUE;
    // When we do call executeAsyn() we must have to pass to get the result by this observer
    protected NotifyObserver mNotifyObserver;
    protected Context mContext;
    protected SBaseModel mInstance;

    /**
     * Constructor function that takes the name of the adapter/model
     * @param context
     */
    public SBaseModel(Context context) {
        this.mContext = context;
        mInstance = this;
    }

    public ArrayList<SObject> getListItem() {
        return mListItem;
    }

    /**
     * @return the mNotifyObserver
     */
    public NotifyObserver getNotifyObserver() {
        return mNotifyObserver;
    }

    /**
     * @param mNotifyObserver the mNotifyObserver to set
     */
    public void setNotifyObserver(NotifyObserver mNotifyObserver) {
        this.mNotifyObserver = mNotifyObserver;
    }

    /**
     * Notify the observer using a handler.
     * @param response
     */
    public synchronized void notifyObserver(ResponseObject response) {
        final ResponseObject tmpResponse = response;
        if (mNotifyObserver != null) {
            mNotifyObserver.update(tmpResponse);
        }
    }

    /**
     * @return the mName
     */
    public String getName() {
        return mName;
    }

    /**
     * @param mName the mName to set
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Execute the operation after setting the Parameter. Never call this before calling setParameter().
     * If this is AbsWsAdapter must call the executeAsyn().
     * @return
     */
    public synchronized ResponseObject execute() {
        // Remove all from previous result..
        // DONT DO OVER TASK HERE...MAY BE THIS ITEMS WOULD BE REQUIRED IN IMPLEMENTED CLASS AS WELL.
        // this.clear(false);
        return this.doExecute();
    }

    public synchronized ResponseObject executeAsyn(NotifyObserver observer) {
        mNotifyObserver = observer;
        // Remove all from previous result..
        // DONT DO OVER TASK HERE...MAY BE THIS ITEMS WOULD BE REQUIRED IN IMPLEMENTED CLASS AS WELL.
        // this.clear(false);
        return this.doExecute();
    }

    /**
     * Add an item to the model/adapter
     * @param item
     */
    public void add(SObject item) {
        if (item != null) {
            this.mListItem.add(item);
        }
    }

    /**
     * Add an item to the model/adapter
     * @param item
     */
    public void addAtPosition(int index, SObject item) {
        if (item != null) {
            this.mListItem.add(index, item);
        }
    }

    /**
     * Removes the object at the specified location from this list.
     * @param position
     * @return
     */
    public SObject remove(int position) {
        return this.mListItem.remove(position);
    }

    /**
     * Removes one instance of the specified object from this Collection if one is contained (optional).
     * @param object
     */
    public void remove(SObject object) {
        this.mListItem.remove(object);
    }

    /**
     * Remove all items from the adapter, For Garbage collector if you have some bitmap then those needs to be
     * recycle first, and also put null to the objects. like Bitmap b; b.recyle(); b = null;
     * @param callGarbageCollector Whether system garbage collector will be called
     */
    public void clear(boolean callGarbageCollector) {
        mListItem.clear();
        if (callGarbageCollector) {
            System.gc();
        }
    }

    /**
     * Returns the number of elements in this ArrayList.
     */
    public int getCount() {
        return this.mListItem.size();
    }

    /**
     * Returns the element at the specified location in this list.
     * @param position
     */
    public Object getItem(int position) {
        if (position > -1 && position < this.mListItem.size()) {
            return this.mListItem.get(position);
        }

        return null;
    }

    /**
     * The operation that will be performed by this adapter/model
     * @return
     */
    protected abstract ResponseObject doExecute();
}

