package com.bonkler.flipdroid;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.*;

import android.database.Cursor;
import android.content.Intent;
import android.view.View;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends FragmentActivity
{
    public final static String CLICKED_DECK_NAME = "com.bonkler.flipdroid.DECK_NAME";
    public final static String CLICKED_DECK_ID = "com.bonkler.flipdroid.DECK_ID";
    public final static String CLICKED_DECK_CARD_IDS = "com.bonkler.flipdroid.CARD_IDS";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        FragmentManager fm = getSupportFragmentManager();

        // if no fragment exists as the root view
        if (fm.findFragmentById(android.R.id.content) == null) {
            DeckListFragment list = new DeckListFragment();
            fm.beginTransaction().add(android.R.id.content, list).commit();
        }
    }

    // A fragment that will hold the list of FlipDecks
    public static class DeckListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

        private FlipCursorLoader mLoader;
        private SimpleCursorAdapter mAdapter;
        private static FlipDeck activeDeck;

        String[] fromColumns = {FlipDroidContract.MyDecks.COLUMN_DECK_NAME};
        int[] toViews = {R.id.text};

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            // Initialize an adapter with a null cursor. We will update this cursor in the onLoadFinished() callback.
            mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.deck_item, null, fromColumns, toViews, 0);
            setEmptyText("No decks found.");
            setListAdapter(mAdapter);
            setListShownNoAnimation(false); // Don't display a loading animation.

            // Initialize the loader, using this fragment as the callback object.
            mLoader = (FlipCursorLoader) getActivity().getSupportLoaderManager().initLoader(1, null, this);

            // Enable context menu on long press for each list item.
            registerForContextMenu(getListView());
        }

        @Override
        public void onListItemClick(ListView I, View v, int position, long id) {

            Cursor c = mAdapter.getCursor();
            activeDeck = new FlipDeck(c, position);

            // // TODO: replace this section; have launched activity query the static activeDeck variable instead.
            
            // // Get the contents as a comma-separated string.
            // int i = c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_CONTENTS);
            // c.moveToPosition(position);
            // String cardIds = c.getString(i);

            // // Get the name of the deck.
            // String theName = c.getString(c.getColumnIndex(FlipDroidContract.MyDecks.COLUMN_DECK_NAME));
            // // Generate the intent and include deck and card ids.
            
            // intent.putExtra(CLICKED_DECK_ID, (int) id);
            // intent.putExtra(CLICKED_DECK_CARD_IDS, cardIds);
            // intent.putExtra(CLICKED_DECK_NAME, theName);

            Intent intent = new Intent(getActivity(), BrowseDeckActivity.class);
            startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
            super.onCreateContextMenu(menu, v, menuInfo);
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.deck_context_menu, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
            activeDeck = new FlipDeck(mAdapter.getCursor(), info.position);
            switch (item.getItemId()) {
                case R.id.edit_deck_option:
                    startEditDeck();
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }

        private void startEditDeck() {
            //Intent intent = new Intent(getActivity(), EditDeckActivity.class);
            //startActivity(intent);
        }

        public static FlipDeck getActiveDeck() {
            return activeDeck;
        }

        // LOADER CALLBACKS

        // Called when a new loader is needed.
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new FlipCursorLoader(
                getActivity(),
                FlipDroidDBHelper.getInstance(getActivity()),
                FlipDroidContract.MyDecks.TABLE_NAME,
                null,
                null,
                null,
                null);
        }

        // Called when an existing loader finishes its loading task.
        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
            mAdapter.swapCursor(c); // Swap in our new cursor.
            setListShownNoAnimation(true);
        }

        // Called when an existing loader is reset.
        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }
    }
}
