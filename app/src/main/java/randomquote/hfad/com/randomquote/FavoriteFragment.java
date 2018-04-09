package randomquote.hfad.com.randomquote;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    ////////////////////////////// Variables ///////////////////////////////////////////////////////


    private boolean size;
    private String selectedQuote;
    private Fragment fragment = this;
    DatabaseHelper mDatabaseHelper;


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        this.setHasOptionsMenu(true);

        //gets the string that was passed in a bundle
        Bundle openBundle = getArguments();
        selectedQuote = openBundle.getString("quote", "empty");
        mDatabaseHelper = new DatabaseHelper(getActivity());

        TextView savedQuoteView = view.findViewById(R.id.savedQuote);
        savedQuoteView.setText(selectedQuote);

        Button deleteBtn = view.findViewById(R.id.deleteButton);

        //delteBtn OnClickListener
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.deleteButton) {
                    size = openBundle.getBoolean("size", false);
                    if (!size) {
                        new DeleteQuoteTask().execute(selectedQuote);
                        FavoriteListFragment newFragment = new FavoriteListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("size", false);
                        newFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(container.getId(), newFragment);
                        fragmentTransaction.commit();
                    } else {
                        new DeleteQuoteTask().execute(selectedQuote);
                        FavoriteListFragment newFragment = new FavoriteListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("size", true);
                        newFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.favorite_Container, newFragment);
                        fragmentTransaction.remove(fragment);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return view;
    }


    ////////////////////////////////// ActionBar Methods ///////////////////////////////////////////


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.favorite_selected_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! check out this quote \n\n\n        " + selectedQuote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;


            default:
        }
        return super.onOptionsItemSelected(item);

    }

    /////////////////////////////////// DeleteQuoteTask ////////////////////////////////////////////


    private class DeleteQuoteTask extends AsyncTask<String, Void, Boolean> {
        private SQLiteOpenHelper helper;

        protected void onPreExecute() {
            helper = new DatabaseHelper(getContext());
        }

        protected Boolean doInBackground(String... quotes) {

            try {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.delete("QUOTESAVED", "quote=?", new String[]{quotes[0]});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(getActivity(),
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
