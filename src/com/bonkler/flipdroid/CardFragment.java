package com.bonkler.flipdroid;

import android.support.v4.app.Fragment;

import android.view.*;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

public class CardFragment extends Fragment implements OnClickListener {

    private FlipCard card;
    private TextView mTextView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.card_fragment, container, false);

        String viewText = !(card == null) ? card.getQuestion() : "No question";
        mTextView = (TextView) rootView.findViewById(R.id.card_text);
        mTextView.setText(viewText);

        rootView.setOnClickListener(this);
        return rootView;
    }

    public void onClick(View v) {
        if (card.getState() == 1) {
            card.setState(2);
            mTextView.setText(!(card == null) ? card.getAnswer() : "No answer");
        }
        else {
            card.setState(1);
            mTextView.setText(!(card == null) ? card.getQuestion() : "No question");
        }
    }

    public void setCard(FlipCard fc) {
        card = fc;
    }
}