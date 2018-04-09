package randomquote.hfad.com.randomquote;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FavoriteActivity extends AppCompatActivity {


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sets the theme to the activity
        Customization.SetTheme(this);
        setContentView(R.layout.activity_favorite);

        //if the device is in portrait or in landscape, then it will replace the current
        //fragment with a new FavoriteListFragment
        if ((findViewById(R.id.portrait) != null) || (findViewById(R.id.landscape) != null)) {
            FavoriteListFragment newFragment = new FavoriteListFragment();
            //since the device insnt a tablet, it passes false in a bundle
            Bundle bundle = new Bundle();
            bundle.putBoolean("size", false);
            newFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.favorite_Container, newFragment);
            // Commit the transaction
            transaction.commit();
        } else {
            //this detects that its a tablet and it replaces the other fragment with the
            // FavoriteListFragment
            FavoriteListFragment newFragment = new FavoriteListFragment();
            //since the device in a tablet, it passes true in a bundle
            Bundle bundle = new Bundle();
            bundle.putBoolean("size", true);
            newFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.favorite_Container, newFragment);
            transaction.commit();
        }
    }


    ////////////////////////////////// ActionBar Methods ///////////////////////////////////////////


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {

            case R.id.action_home:
                // settings action
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.favorite_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

