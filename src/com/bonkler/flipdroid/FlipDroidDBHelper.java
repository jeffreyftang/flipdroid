package com.bonkler.flipdroid;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;

public class FlipDroidDBHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FlipDroid.db";

    private ContentValues deck1;
    private ContentValues deck2;
    private ContentValues deck3;

    private ContentValues card1;
    private ContentValues card2;
    private ContentValues card3;

    private static FlipDroidDBHelper mInstance;

    private FlipDroidDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        deck1 = new ContentValues();
        deck2 = new ContentValues();
        deck3 = new ContentValues();
        deck1.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, "Deck 1");
        deck1.put(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS, "1,2,3");
        deck2.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, "Deck 2");
        deck3.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, "Deck 3");

        card1 = new ContentValues();
        card2 = new ContentValues();
        card3 = new ContentValues();
        card1.put(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION, "The question 1?");
        card2.put(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION, "The question 2?");
        card3.put(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION, "The question 3?");
    }

    public static FlipDroidDBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new FlipDroidDBHelper(context.getApplicationContext());
        }

        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_MYDECKS);
        db.execSQL(SQL_CREATE_MYCARDS);
        db.insert(FlipDroidContract.MyDecks.TABLE_NAME, null, deck1);
        db.insert(FlipDroidContract.MyDecks.TABLE_NAME, null, deck2);
        db.insert(FlipDroidContract.MyDecks.TABLE_NAME, null, deck3);

        db.insert(FlipDroidContract.MyCards.TABLE_NAME, null, card1);
        db.insert(FlipDroidContract.MyCards.TABLE_NAME, null, card2);
        db.insert(FlipDroidContract.MyCards.TABLE_NAME, null, card3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {

    }


    private static final String SQL_CREATE_MYDECKS = 
        "CREATE TABLE " + FlipDroidContract.MyDecks.TABLE_NAME + " (" 
        + FlipDroidContract.MyDecks._ID + " INTEGER PRIMARY KEY,"
        + FlipDroidContract.MyDecks.COLUMN_DECK_NAME + " TEXT,"
        + FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS + " TEXT);";

    private static final String SQL_CREATE_MYCARDS = 
        "CREATE TABLE " + FlipDroidContract.MyCards.TABLE_NAME + " (" 
        + FlipDroidContract.MyCards._ID + " INTEGER PRIMARY KEY," 
        + FlipDroidContract.MyCards.COLUMN_CARD_QUESTION + " TEXT," 
        + FlipDroidContract.MyCards.COLUMN_CARD_ANSWER + " TEXT," 
        + FlipDroidContract.MyCards.COLUMN_CARD_HINT + " TEXT);";


}