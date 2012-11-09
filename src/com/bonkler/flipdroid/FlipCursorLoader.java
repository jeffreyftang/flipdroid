package com.bonkler.flipdroid;

import android.util.Log;

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
        InsertTask task = new InsertTask(this, true);
        ContentValues values = FlipDeck.contentValuesFromDeck(deck);
        task.execute(
            mHelper,
            FlipDroidContract.MyDecks.TABLE_NAME,
            null,
            values
            );
    }

    public void insert(FlipCard card, FlipDeck deck) {
        InsertTask task1 = new InsertTask(this, true);

        // Value mapping for the new card
        ContentValues values = FlipCard.contentValuesFromCard(card);
        task1.execute(
            mHelper,
            FlipDroidContract.MyCards.TABLE_NAME,
            null,
            values
            );

        deck.getCardIds().add(task1.id);

        UpdateTask task2 = new UpdateTask(this, false);
        
        ContentValues deckValues = FlipDeck.contentValuesFromDeck(deck);
        String whereClause = FlipDroidContract.MyDecks._ID + "=" + deck.getId();
        task2.execute(
            mHelper,
            FlipDroidContract.MyDecks.TABLE_NAME,
            deckValues,
            whereClause,
            null
            );   

    }

    public void update(FlipDeck deck) {
        UpdateTask task = new UpdateTask(this, true);
        ContentValues values = FlipDeck.contentValuesFromDeck(deck);
        String whereClause = FlipDroidContract.MyDecks._ID + "=" + deck.getId();
        task.execute(
            mHelper,
            FlipDroidContract.MyDecks.TABLE_NAME,
            values,
            whereClause,
            null
            );   
    }

    public void update(FlipCard card) {
        UpdateTask task = new UpdateTask(this, true);
        ContentValues values = FlipCard.contentValuesFromCard(card);
        String whereClause = FlipDroidContract.MyCards._ID + "=" + card.getId();
        task.execute(
            mHelper,
            FlipDroidContract.MyCards.TABLE_NAME,
            values,
            whereClause,
            null
            );    
    }

    private class InsertTask extends CrudTask<Object, Void, Void> {
        public InsertTask(FlipCursorLoader loader, boolean notify) {
            super(loader, notify);
        }

        @Override
        protected Void doInBackground(Object... args) {
            SQLiteOpenHelper db = (SQLiteOpenHelper) args[0];
            String table = (String) args[1];
            String nullColumnHack = (String) args[2];
            ContentValues values = (ContentValues) args[3];

            id = db.getWritableDatabase().insert(table, nullColumnHack, values);

            return(null);
        }
    }

    private class UpdateTask extends CrudTask<Object, Void, Void> {
        public UpdateTask(FlipCursorLoader loader, boolean notify) {
            super(loader, notify);
        }

        @Override
        protected Void doInBackground(Object... args) {
            SQLiteOpenHelper db = (SQLiteOpenHelper) args[0];
            String table = (String) args[1];
            ContentValues values = (ContentValues) args[2];
            String whereClause = (String) args[3];

            id = db.getWritableDatabase().update(table, values, whereClause, null);

            return(null);
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