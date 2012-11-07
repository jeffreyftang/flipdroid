package com.bonkler.flipdroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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