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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuoteFragment extends Fragment {


    ///////////////////////////////////// Variables ///////////////////////////////////////////////////////


    final static String url1 = "https://andruxnet-random-famous-quotes.p.mashape.com/?cat=";
    final static String url2 = "&count=1&mashape-key=4U2zJVYcolmshgYM4RCZiw861hoXp1wiaYzjsn9BF1VW7wx349";
    private final String LOG_TAG = QuoteFragment.class.getSimpleName();
    private TextView quoteView;
    public String quoteType;
    public String quote;


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote, container, false);
        TextView titleView = view.findViewById(R.id.QuoteTitle);
        quoteView = view.findViewById(R.id.QuoteAPI);
        if (quoteType.equals("movies")) {
            titleView.setText(getString(R.string.MovieSelection));
        } else {
            titleView.setText(getString(R.string.FamousSelection));
        }
        new QuoteHandler().execute(quoteType);

        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getActivity());
        return view;
    }

    //this determines the type of quote generated
    public void setQuoteType(int id) {
        if (id == R.id.MovieButton) {
            quoteType = "movies";
        } else if (id == R.id.FamousButton) {
            quoteType = "famous";
        }
    }


    ////////////////////////////////// ActionBar Methods ///////////////////////////////////////////


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.quote_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! check out this quote \n\n\n        " + quote);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            case R.id.action_respin:
                new QuoteHandler().execute(quoteType);
                return true;

            case R.id.action_favorite:
                new AddQuoteTask().execute(quote);
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);

    }


    /////////////////////////////////// AddQuoteTask ///////////////////////////////////////////////


    private class AddQuoteTask extends AsyncTask<String, Void, Boolean> {

        private ContentValues values;
        private SQLiteOpenHelper helper;

        protected void onPreExecute() {
            helper = new DatabaseHelper(getContext());
            values = new ContentValues();
            values.put("FAVORITE", true);
        }

        //this puts the quote in the table
        protected Boolean doInBackground(String... quotes) {
            values.put("QUOTE", quotes[0]);
            try {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.insert("QUOTESAVED", null, values);
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }

        // If the addition, fails then it shows a toast
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(getActivity(),
                        "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /////////////////////////////////// QuoteHandler ////////////////////////////////////////////////


    @SuppressLint("StaticFieldLeak")
    public class QuoteHandler extends AsyncTask<String, Void, String> {


        //Methods
        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String quoteFormatted = "";

            try {
                //This combines the first part of the url, the category, and the mashapekey
                URL url = new URL(url1 + strings[0] + url2);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream in = urlConnection.getInputStream();
                if (in == null)
                    return null;

                reader = new BufferedReader(new InputStreamReader(in));
                String quoteJsonString = getJsonStringFromBuffer(reader);
                quoteFormatted = getFormattedQuote(quoteJsonString);


            } catch (Exception e) {
                Log.e(LOG_TAG + "Error", e.getMessage());
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error" + e.getMessage());
                    }
            }
            return quoteFormatted;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            if (result != null)
                Log.d(LOG_TAG, "ERROR" + " " + result);
            quoteView.setText(result);
            quote = result;
        }
    }


    //Retrieves the objects from the api link
    public String getFormattedQuote(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        String quote = jsonObject.getString("quote");
        String author = jsonObject.getString("author");
        return quote + " \n - " + author;
    }


    //Formats the json
    public String getJsonStringFromBuffer(BufferedReader br) throws Exception {
        StringBuilder buffer = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            buffer.append(line).append('\n');
        }
        if (buffer.length() == 0)
            return null;

        return buffer.toString();
    }

}
