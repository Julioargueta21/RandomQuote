package randomquote.hfad.com.randomquote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by User on 2/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    ////////////////////////////// Variables ///////////////////////////////////////////////////////


    private static final String DB_NAME = "FavoriteQuotes"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    //this is called to insert the quote into the database
    private static void insertQuote(SQLiteDatabase db, String quote, boolean isEmpty) {
        ContentValues QUOTEVALUES = new ContentValues();
        QUOTEVALUES.put("QUOTE", quote);
        QUOTEVALUES.put("FAVORITE", isEmpty);
        db.insert("QUOTESAVED", null, QUOTEVALUES);
    }

    //this is called when a change happens and the table will need to be updated
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE QUOTESAVED (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "QUOTE TEXT,"
                    + "FAVORITE NUMERIC);");
            insertQuote(db, "Don't cry because it's over smile because it happened.  \n Dr.Seuss ", true);
        }
    }

}
























