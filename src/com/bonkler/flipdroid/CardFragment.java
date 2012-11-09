package com.bonkler.flipdroid;

import android.support.v4.app.Fragment;

import android.view.*;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;
import android.graphics.Typeface;

public class CardFragment extends Fragment implements OnClickListener {

    private FlipCard card;
    private int mState = 1;
    private TextView mTextView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setRetainInstance(true);

        View rootView = inflater.inflate(R.layout.card_fragment, container, false);

        String viewText = !(card == null) ? card.getQuestion() : "No question";
        mTextView = (TextView) rootView.findViewById(R.id.card_text);
        mTextView.setText(viewText);

        rootView.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v) {
        if (mState == 1) {
            mState = 2;
            mTextView.setTypeface(null, Typeface.ITALIC);
            mTextView.setText(!(card == null) ? card.getAnswer() : "No answer");
        }
        else {
            mState = 1;
            mTextView.setTypeface(null, Typeface.NORMAL);
            mTextView.setText(!(card == null) ? card.getQuestion() : "No question");
        }
    }

    public void setCard(FlipCard fc) {
        card = fc;
    }

    public FlipCard getCard() {
        return card;
    }
}