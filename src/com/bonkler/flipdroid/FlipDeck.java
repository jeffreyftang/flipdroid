package com.bonkler.flipdroid;

import java.util.*;

import android.database.Cursor;

public class FlipDeck {

    public FlipDeck(Cursor c, int position) {
        c.moveToPosition(position);
        name = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_NAME));
        id = c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
        String theContents = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS));
        cardIds = new ArrayList<Long>();
        if (!(theContents == null)) {
            String[] s = theContents.split(",");
            for(int i = 0; i < s.length; i++) {
                cardIds.add(Long.parseLong(s[i]));
            }
        }
    }

    public FlipDeck(String theName, String theContents, long theId) {
        name = theName;
        id = theId;
        cardIds = new ArrayList<Long>();
        String[] s = theContents.split(",");
        for(int i = 0; i < s.length; i++) {
            cardIds.add(Long.parseLong(s[i]));
        }
    }

    public FlipDeck(String theName) {
        name = theName;
    }

    public ArrayList<FlipCard> fillCards(Cursor c) {
        cards = new ArrayList<FlipCard>(cardIds.size());
        for (int i = 0; i < cardIds.size(); i++) {
            cards.add(null);
        }
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            long theId = c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
            String question = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            String answer = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            String hint = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            FlipCard fc = new FlipCard(question, answer, hint);
            cards.set(cardIds.indexOf(theId), fc);
            c.moveToNext();
        }

        return cards;
    }

    public String getContentsAsString() {
        String result = "";
        if (!(cardIds == null)) { 
            for (int i = 0; i < cardIds.size(); i++) {
                result += (cardIds.get(i) + ",");
            }
        // remove trailing comma 
        if (result.endsWith(",")) 
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    public String getName() {
        return name;
    }

    // Iterating
    // public boolean hasNext() {
    //     return itr.hasNext();
    // }

    // public FlipCard getNext() {
    //     return itr.next();
    // }

    // public boolean hasPrevious() {
    //     return itr.hasPrevious();
    // }

    // public FlipCard getPrevious() {
    //     return itr.previous();
    // }

    // public void shuffleSelf() {
    //     if (!cards.isEmpty())
    //         Collections.shuffle(cards);
    //     currentPos = 0;
    // } 

    // Attributes
    private String name;
    private ArrayList<FlipCard> cards;
    private ArrayList<Long> cardIds;
    private int currentPos;
    private long id;
    // private ListIterator<FlipCard> itr;
}