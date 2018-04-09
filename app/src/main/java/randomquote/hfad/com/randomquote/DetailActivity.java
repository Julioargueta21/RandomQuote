package randomquote.hfad.com.randomquote;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class DetailActivity extends AppCompatActivity {


    /////////////////////////////////////// Methods ////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //makes the theme stay on the activity
        Customization.SetTheme(this);

        Intent intent = getIntent();
        //gives the quoteFragment the button clicked id
        QuoteFragment quoteFragment = new QuoteFragment();
        quoteFragment.setQuoteType(intent.getIntExtra("buttonClicked", R.id.MovieButton));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_container, quoteFragment);
        ft.commit();
    }

    ////////////////////////////////// ActionBar Methods ///////////////////////////////////////////

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:
                // settings action
                Intent intent = new Intent(DetailActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_home:
                // home action
                intent = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            // favoriteList action
            case R.id.action_favoriteList:
                intent = new Intent(DetailActivity.this, FavoriteActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}