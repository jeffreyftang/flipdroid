package com.bonkler.flipdroid;

import java.util.*;
import android.util.Log;

import android.database.Cursor;
import android.content.ContentValues;

// TODO: implement the current position function.

public class FlipDeck {

    private String mName;
    private ArrayList<FlipCard> mCards;
    private ArrayList<Long> mCardIds;
    private int mCurrentPos;
    private long mId;

    public FlipDeck(Cursor c, int position) {
        c.moveToPosition(position);
        mName = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_NAME));
        mId = c.getLong(c.getColumnIndex(FlipDroidContract.MyDecks._ID));
        String theContents = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS));
        mCardIds = new ArrayList<Long>();
        if (theContents != null && theContents.length() > 0) {
            String[] s = theContents.split(",");
            for(int i = 0; i < s.length; i++) {
                mCardIds.add(Long.parseLong(s[i]));
            }
        }
    }

    public FlipDeck(String name, String contents, long id) {
        mName = name;
        mId = id;
        mCardIds = new ArrayList<Long>();
        String[] s = contents.split(",");
        for(int i = 0; i < s.length; i++) {
            mCardIds.add(Long.parseLong(s[i]));
        }
    }

    public FlipDeck(String theName) {
        mName = theName;
    }

    public ArrayList<FlipCard> fillCards(Cursor c) {
        mCards = new ArrayList<FlipCard>(mCardIds.size());
        for (int i = 0; i < mCardIds.size(); i++) {
            mCards.add(null);
        }

        // TODO rewrite this to use the new FlipCard constructor
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            long theId = c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
            String question = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            String answer = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_ANSWER));
            String hint = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_HINT));
            FlipCard fc = new FlipCard(question, answer, hint, theId);
            mCards.set(mCardIds.indexOf(theId), fc);
            c.moveToNext();
        }

        return mCards;
    }

    public String getContentsAsString() {
        String result = "";
        if (!(mCardIds == null)) { 
            for (int i = 0; i < mCardIds.size(); i++) {
                result += (mCardIds.get(i) + ",");
            }
        // remove trailing comma 
        if (result.endsWith(",")) 
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_NAME, mName);
        values.put(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS, getContentsAsString());

        return values;
    }

    public String getName() {
        return mName;
    }

    public void setName(String s) {
        mName = s;
    }

    public ArrayList<Long> getCardIds() {
        return mCardIds;
    }

    public long getId() {
        return mId;
    }

    public void shuffleSelf() {
        if (mCardIds != null && !mCardIds.isEmpty())
            Collections.shuffle(mCardIds);
        mCurrentPos = 0;
        Log.i("SHUFFLE", getContentsAsString());
    } 
}