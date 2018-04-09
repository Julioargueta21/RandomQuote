package randomquote.hfad.com.randomquote;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class FavoriteListFragment extends ListFragment {


    ///////////////////////////////////// Variables ////////////////////////////////////////////////


    private static final String LOG_TAG = FavoriteListFragment.class.getSimpleName();
    private DatabaseHelper mDatabaseHelper;
    private Cursor cursor;
    private SQLiteDatabase database;
    private ViewGroup container;
    private boolean size;


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        database = mDatabaseHelper.getWritableDatabase();
        this.container = container;
        cursor = database.query("QuoteSaved", new String[]{"_id", "QUOTE"}, "FAVORITE = 1", null, null, null, null);
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getContext(),
                android.R.layout.simple_list_item_1,
                cursor,
                new String[]{"QUOTE"},
                new int[]{android.R.id.text1},
                0);
        setListAdapter(listAdapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDatabaseHelper = new DatabaseHelper(context);
    }

    //onClickListener for the listView
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        super.onListItemClick(l, v, position, id);
        Bundle openBundle = getArguments();

        size = openBundle.getBoolean("size", false);
        {
            //if Size it false then it will replace the current fragment with a new FavoriteFragment
            if (!size) {
                String quote = cursor.getString(cursor.getColumnIndexOrThrow("QUOTE"));
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                Bundle bundle = new Bundle();
                //it just passes the quote that was selected in a bundle
                bundle.putString("quote", quote);
                //since the device is a tablet, it passes false in a bundle
                bundle.putBoolean("size", false);
                favoriteFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(container.getId(), favoriteFragment);
                fragmentTransaction.commit();
            } else {
                //if Size it true then it will replace the current fragment with a new FavoriteFragment
                String quote = cursor.getString(cursor.getColumnIndexOrThrow("QUOTE"));
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                Bundle bundle = new Bundle();
                //it just passes the quote that was selected in a bundle
                bundle.putString("quote", quote);
                //since the device is a tablet, it passes true in a bundle
                bundle.putBoolean("size", true);
                favoriteFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.favorite_selected_Container, favoriteFragment);
                fragmentTransaction.commit();
            }
        }
    }
}
