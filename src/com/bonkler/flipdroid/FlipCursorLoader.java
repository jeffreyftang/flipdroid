package com.bonkler.flipdroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

public class FlipCursorLoader extends SQLCursorLoader {

    SQLiteOpenHelper mHelper;
    String[] mProjection;
    String[] mSelectionArgs;
    String mSelection;
    String mTableName;
    String mSortOrder;

    public FlipCursorLoader(Context context, SQLiteOpenHelper helper, String tableName, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context);
        mHelper = helper;
        mTableName = tableName;
        mProjection = projection;
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        mSortOrder = sortOrder;
    }

    public void insert(FlipDeck deck) {
        InsertTask task = new InsertTask(this);
        ContentValues values = new ContentValues();
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, deck.getName());
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS, "");
        task.execute(
            mHelper,
            FlipDroidContract.MyDecks.TABLE_NAME,
            null,
            values
            );
    }

    public void update(FlipDeck deck) {
        UpdateTask task = new UpdateTask(this);
        ContentValues values = new ContentValues();
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, deck.getName());
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS, deck.getContentsAsString());
        String whereClause = FlipDroidContract.MyDecks._ID + "=" + deck.getId();
        task.execute(
            mHelper,
            FlipDroidContract.MyDecks.TABLE_NAME,
            values,
            whereClause,
            null
            );   
    }

    private class InsertTask extends AsyncTask<Object, Void, Void> {
        private FlipCursorLoader mLoader;

        public InsertTask(FlipCursorLoader loader) {
            mLoader = loader;
        }

        @Override
        protected Void doInBackground(Object... args) {
            SQLiteOpenHelper db = (SQLiteOpenHelper) args[0];
            String table = (String) args[1];
            String nullColumnHack = (String) args[2];
            ContentValues values = (ContentValues) args[3];

            db.getWritableDatabase().insert(table, nullColumnHack, values);

            return(null);
        }

        @Override
        protected void onPostExecute(Void v) {
            mLoader.onContentChanged();
        }
    }

    private class UpdateTask extends AsyncTask<Object, Void, Void> {
        private FlipCursorLoader mLoader;

        public UpdateTask(FlipCursorLoader loader) {
            mLoader = loader;
        }

        @Override
        protected Void doInBackground(Object... args) {
            SQLiteOpenHelper db = (SQLiteOpenHelper) args[0];
            String table = (String) args[1];
            ContentValues values = (ContentValues) args[2];
            String whereClause = (String) args[3];

            db.getWritableDatabase().update(table, values, whereClause, null);

            return(null);
        }

        @Override
        protected void onPostExecute(Void v) {
            mLoader.onContentChanged();
        }
    }

    @Override
    protected Cursor createCursor() {
        Cursor c = mHelper.getReadableDatabase().query(
            mTableName,
            mProjection,
            mSelection,
            mSelectionArgs,
            null,
            null,
            mSortOrder);

        return c;
    }
}