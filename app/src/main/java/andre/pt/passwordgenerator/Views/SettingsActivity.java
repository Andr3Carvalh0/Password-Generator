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
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import andre.pt.passwordgenerator.Utilities.NotificationUtilities;
import andre.pt.passwordgenerator.Views.Interfaces.ISettingsView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static andre.pt.passwordgenerator.Constants.NOTIFICATION_CHANNEL_ID;

public class SettingsActivity extends AppCompatActivity implements ISettingsView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private ISettingsPresenter presenter;
    private IPreferencesManager preferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.settings);

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
    protected void onPause() {
        super.onPause();
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

        try {
            NotificationUtilities.notify(this, id)
                                 .setChannelID(NOTIFICATION_CHANNEL_ID)
                                 .setTitle(getString(R.string.notificationTitle))
                                 .setDescription(getString(R.string.notificationBody))
                                 .setOnNotificationClick(it)
                                 .setIcon(R.drawable.ic_lock_outline)
                                 .dismissOnTouch(false)
                                 .dismissable(false)
                                 .setChannelLowPriority()
                                 .setChannelName("General")
                                 .setNotificationLowPriority()
                                 .show();
        } catch (Exception e) { }
    }

    @Override
    public void hideNotification(int id) {
        NotificationUtilities.dismiss(this, id);
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
