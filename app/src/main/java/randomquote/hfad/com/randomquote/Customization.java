package randomquote.hfad.com.randomquote;

import android.app.Activity;
import android.content.Intent;

public class Customization {

    ////////////////////////////// Variables ///////////////////////////////////////////////////////


    private static int Theme;
    public final static int THEME_DARK = 0;
    public final static int THEME_LIGHT = 1;
    public final static int FONT_CASUAL = 2;
    public final static int FONT_MANO = 3;


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    // sets the theme and restarts it
    public static void changeToTheme(Activity activity, int theme) {
        Theme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    //this is what the Activity will call in order to change the themes
    public static void SetTheme(Activity activity) {
        switch (Theme) {
            default:

            case THEME_DARK:
                activity.setTheme(R.style.AppTheme);
                break;

            case THEME_LIGHT:
                activity.setTheme(R.style.LightTheme);
                break;

            case FONT_CASUAL:
                activity.setTheme(R.style.FontCasual);
                break;

            case FONT_MANO:
                activity.setTheme(R.style.FontMonospace);
                break;
        }
    }

}