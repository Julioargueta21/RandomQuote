package randomquote.hfad.com.randomquote;

/***
 * By Julio Argueta
 * Date March 9, 2018
 */

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Customization.SetTheme(this);
        setContentView(R.layout.activity_main);

    }


    ////////////////////////////////// ActionBar Methods ///////////////////////////////////////////


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {

            case R.id.action_settings:
                // settings action
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_favoriteList:
                intent = new Intent(MainActivity.this, FavoriteActivity.class);
                intent.putExtra("list", true);
                startActivity(intent);
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //This checks what layout its on and then generates the necessary fragments when the button is clicked
    public void generateQuote(View view) {
        if ((findViewById(R.id.portrait) != null) || (findViewById(R.id.landscape) != null)) {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("buttonClicked", view.getId());
            startActivity(intent);

        } else {
            //this activates when the device is a tablet
            QuoteFragment quoteFragment = new QuoteFragment();
            quoteFragment.setQuoteType(view.getId());
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.quote_layout, quoteFragment);
            ft.commit();
        }
    }
}
