package com.bonkler.flipdroid;

import android.provider.BaseColumns;

public class FlipDroidContract
{
    public static abstract class MyDecks implements BaseColumns
    {
        public static final String TABLE_NAME = "mydecks";
        public static final String COLUMN_DECK_NAME = "name";
        public static final String COLUMN_DECK_CONTENTS = "contents";
    }

    public static abstract class MyCards implements BaseColumns
    {
        public static final String TABLE_NAME = "mycards";
        public static final String COLUMN_CARD_QUESTION = "question";
        public static final String COLUMN_CARD_ANSWER = "answer";
        public static final String COLUMN_CARD_HINT = "hint";
    }
}