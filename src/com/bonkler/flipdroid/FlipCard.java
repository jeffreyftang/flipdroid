package com.bonkler.flipdroid;

import android.database.Cursor;
import android.content.ContentValues;

public class FlipCard
{
    public FlipCard(String theQuestion, String theAnswer, String theHint)
    {
        question = theQuestion;
        answer = theAnswer;
        hint = theHint;
        mState = 1;
    }

    public FlipCard(String theQuestion, String theAnswer)
    {
        this(theQuestion, theAnswer, "Sorry, no hints for this question.");
    }

    public FlipCard(Cursor c, int position) {
        c.moveToPosition(position);
        id = c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
        question = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
        answer = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_ANSWER));
        hint = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_HINT));
        mState = 1;
    }

    public static ContentValues contentValuesFromCard(FlipCard card) {
        ContentValues values = new ContentValues();
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION, card.getQuestion());
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_ANSWER, card.getAnswer());
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_HINT, card.getHint());

        return values;
    }

    // Accessors
    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String s)
    {
        question = s;
    }

    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String s)
    {
        answer = s;
    }

    public String getHint()
    {
        return hint;
    }

    public void setHint(String s)
    {
        hint = s;
    }

    public int getState() {
        return mState;
    }

    public void setState(int i) {
        mState = i;
    }

    public long getId() {
        return id;
    }

    // Attributes
    private long id; // the ID of this card's database entry
    private String question;
    private String answer;
    private String hint;

    private int mState; // 0 = hint, 1 = question, 2 = answer
}