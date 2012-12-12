package com.bonkler.flipdroid;

import android.database.Cursor;
import android.content.ContentValues;

public class FlipCard {

    private long mId;
    private String mQuestion;
    private String mAnswer;
    private String mHint;

    public FlipCard(String question, String answer, String hint, long id) {
        mQuestion = question;
        mAnswer = answer;
        mHint = hint;
        mId = id;
    }

    public FlipCard(String question, String answer) {
        this(question, answer, "Sorry, no hints for this question.", -1);
    }

    public FlipCard(Cursor c, int position) {
        c.moveToPosition(position);
        mId = c.getLong(c.getColumnIndex(FlipDroidContract.MyCards._ID));
        mQuestion = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION));
        mAnswer = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_ANSWER));
        mHint = c.getString(c.getColumnIndex(FlipDroidContract.MyCards.COLUMN_CARD_HINT));
    }

    public FlipCard(FlipCard card) {
        mId = card.getId();
        mQuestion = card.getQuestion();
        mAnswer = card.getAnswer();
        mHint = card.getHint();
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_QUESTION, mQuestion);
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_ANSWER, mAnswer);
        values.put(FlipDroidContract.MyCards.COLUMN_CARD_HINT, mHint);

        return values;
    }

    // Accessors
    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String s) {
        mQuestion = s;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String s) {
        mAnswer = s;
    }

    public String getHint() {
        return mHint;
    }

    public void setHint(String s) {
        mHint = s;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }
}