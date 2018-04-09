package randomquote.hfad.com.randomquote;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment implements View.OnClickListener {


    ////////////////////////////////////// Methods /////////////////////////////////////////////////


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Customization.SetTheme(getActivity());
        View view = inflater.inflate(R.layout.fragment_settings, container, false);


        Button darkBtn = view.findViewById(R.id.DarkThemeButton);
        Button lightBtn = view.findViewById(R.id.LightThemeButton);
        Button casualBtn = view.findViewById(R.id.CasualFontButton);
        Button monoBtn = view.findViewById(R.id.MonoFontButton);

        darkBtn.setOnClickListener(this);
        lightBtn.setOnClickListener(this);
        casualBtn.setOnClickListener(this);
        monoBtn.setOnClickListener(this);


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.DarkThemeButton: {
                Customization.changeToTheme(getActivity(), Customization.THEME_DARK);
            }
            break;

            case R.id.LightThemeButton: {
                Customization.changeToTheme(getActivity(), Customization.THEME_LIGHT);
            }
            break;

            case R.id.CasualFontButton: {
                Customization.changeToTheme(getActivity(), Customization.FONT_CASUAL);
            }
            break;

            case R.id.MonoFontButton: {
                Customization.changeToTheme(getActivity(), Customization.FONT_MANO);

            }
            break;
        }
    }
}
