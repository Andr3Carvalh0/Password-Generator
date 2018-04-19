package andre.pt.passwordgenerator.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;

import java.util.List;
import andre.pt.passwordgenerator.Adapters.OptionAdapter;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Generator;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Utilities.INotificationManager;
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import andre.pt.passwordgenerator.Views.Interfaces.ISettingsView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends AppCompatActivity implements ISettingsView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ISettingsPresenter presenter;
    private INotificationManager notificationManager;
    private IPreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.settings);

        notificationManager = ((Generator)getApplication()).getNotificationManager();
        preferencesManager = ((Generator)getApplication()).getPreferencesManager();

        presenter = ((Generator)getApplication()).getSettingsPresenter();
        presenter.onCreate(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void prepareRecyclerView(List<Option> options) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OptionAdapter(options, this, this));
    }

    @Override
    public void showNotification(int id) {
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        it.setFlags(it.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        notificationManager.show(id, getString(R.string.notificationTitle), getString(R.string.notificationBody), it);
    }

    @Override
    public void hideNotification(int id) {
        notificationManager.hide(id);
    }

    @Override
    public void savePreferences(Option... options) {
        preferencesManager.pushValues(options);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final String id = (String) buttonView.getTag();
        presenter.handleOnClick(id, isChecked);
    }
}
