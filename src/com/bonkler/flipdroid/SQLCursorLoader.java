package com.bonkler.flipdroid;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

public abstract class SQLCursorLoader extends AsyncTaskLoader<Cursor> {

    Cursor mCursor;

    public SQLCursorLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor c = createCursor(); // Delegates creating a correctly specified cursor to subclass
        return c;
    }

    @Override
    public void deliverResult(Cursor c) {
        if (isReset()) {
            if (c != null) {
                c.close();
            }

            return;
        }

        Cursor oldCursor = mCursor;
        mCursor = c;

        if (isStarted()) {
            super.deliverResult(c);
        }

        if (oldCursor != null && oldCursor != c && !oldCursor.isClosed()) {
            oldCursor.close();
        }
    }

    @Override
    protected void onStartLoading() {
        if (mCursor != null) {
            deliverResult(mCursor);
        }

        if (takeContentChanged() || mCursor==null) {
            forceLoad();
        }
    }

    /**
     * Must be called from the UI thread, triggered by a
     * call to stopLoading().
     */
    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
    * Must be called from the UI thread, triggered by a
    * call to cancel(). Here, we make sure our Cursor
    * is closed, if it still exists and is not already closed.
    */
    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    /**
    * Must be called from the UI thread, triggered by a
    * call to reset(). Here, we make sure our Cursor
    * is closed, if it still exists and is not already closed.
    */
    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }

        mCursor=null;
    }

    protected abstract Cursor createCursor();
}