package com.bonkler.flipdroid;

import android.os.AsyncTask;

public abstract class CrudTask<A, B, C> extends AsyncTask<A, B, C> {
    private FlipCursorLoader mLoader;
    private boolean notifyChange;
    long id;

    public CrudTask(FlipCursorLoader loader, boolean notify) {
        mLoader = loader;
        notifyChange = notify;
    }

    @Override
    protected void onPostExecute(C v) {
        if (notifyChange) 
            mLoader.onContentChanged();
    }
}