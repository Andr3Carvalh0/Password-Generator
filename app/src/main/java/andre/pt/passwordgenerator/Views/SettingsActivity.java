package andre.pt.passwordgenerator.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.ISettingsView;

public class SettingsActivity extends AppCompatActivity implements ISettingsView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
