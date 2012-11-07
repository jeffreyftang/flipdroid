package com.bonkler.flipdroid;

import java.util.*;

import android.database.Cursor;

// What happens if a deck's contents are empty/null?
// TODO: make id a long to avoid possible mixups later on.

public class FlipDeck {
    // public FlipDeck(ArrayList<FlipCard> theCards, int theCurrentPosition) {
    //     cards = theCards;
    //     currentPos = theCurrentPosition;
    //     itr = cards.listIterator(currentPos);
    // }

    public FlipDeck(Cursor c, int position) {
        c.moveToPosition(position);
        String theName = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_NAME));
        String theContents = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS));
        int theId = (int) c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
        name = theName;
        id = theId;
        cardIds = new ArrayList<Integer>();
        if (!(theContents == null)) {
            String[] s = theContents.split(",");
            for(int i = 0; i < s.length; i++) {
                cardIds.add(Integer.parseInt(s[i]));
            }
        }
    }

    public FlipDeck(String theName, String theContents, int theId) {
        name = theName;
        id = theId;
        cardIds = new ArrayList<Integer>();
        String[] s = theContents.split(",");
        for(int i = 0; i < s.length; i++) {
            cardIds.add(Integer.parseInt(s[i]));
        }
    }

    public ArrayList<FlipCard> fillCards(Cursor c) {
        cards = new ArrayList<FlipCard>(cardIds.size());
        for (int i = 0; i < cardIds.size(); i++) {
            cards.add(null);
        }
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int theId = (int) c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
            String question = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            String answer = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            String hint = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
            FlipCard fc = new FlipCard(question, answer, hint);
            cards.set(cardIds.indexOf(theId), fc);
            c.moveToNext();
        }

        return cards;
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
    private ArrayList<Integer> cardIds;
    private int currentPos;
    private int id;
    // private ListIterator<FlipCard> itr;
}