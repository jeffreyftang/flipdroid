package com.bonkler.flipdroid;

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

    // Attributes
    private long id; // the ID of this card's database entry
    private String question;
    private String answer;
    private String hint;

    private int mState; // 0 = hint, 1 = question, 2 = answer
}