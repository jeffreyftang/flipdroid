package com.bonkler.flipdroid;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.*;
import android.support.v4.view.ViewPager;

import android.database.Cursor;
import android.content.Intent;
import android.view.View;
import android.util.Log;

public class BrowseDeckActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private FlipDeck mDeck;

    private ViewPager mViewPager;
    private FlipPagerAdapter mPagerAdapter;
    private FlipCursorLoader mLoader;

    private String contents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_deck);

        // Intent intent = getIntent();
        // int id = intent.getIntExtra(MainActivity.CLICKED_DECK_ID, -1);
        // contents = intent.getStringExtra(MainActivity.CLICKED_DECK_CARD_IDS);
        // String name = intent.getStringExtra(MainActivity.CLICKED_DECK_NAME);
        // mDeck = new FlipDeck(name, contents, id);

        mDeck = MainActivity.DeckListFragment.getActiveDeck();
        contents = mDeck.getContentsAsString();

        mPagerAdapter = new FlipPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mLoader = (FlipCursorLoader)getSupportLoaderManager().initLoader(2, null, this);
    }

    // LOADER CALLBACKS

    // Called when a new loader is needed.
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new FlipCursorLoader(
                this,
                FlipDroidDBHelper.getInstance(this),
                FlipDroidContract.MyCards.TABLE_NAME,
                null,
                FlipDroidContract.MyCards._ID + " IN (" + contents + ")",
                null,
                null);
        }

        // Called when an existing loader finishes its loading task.
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
            mPagerAdapter.changeCards(mDeck.fillCards(c));
            if (mViewPager.getAdapter() == null)
                mViewPager.setAdapter(mPagerAdapter);
        }

        // Called when an existing loader is reset.
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
        }

}