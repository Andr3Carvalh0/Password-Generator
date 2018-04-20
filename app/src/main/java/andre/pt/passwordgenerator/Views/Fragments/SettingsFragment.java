package andre.pt.passwordgenerator.Views.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import java.util.List;
import java.util.Objects;
import andre.pt.passwordgenerator.Adapters.OptionAdapter;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import andre.pt.passwordgenerator.Utilities.NotificationUtilities;
import andre.pt.passwordgenerator.Views.Activities.GeneratorActivity;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.ISettingsActivity;
import andre.pt.passwordgenerator.Views.Fragments.Interfaces.ISettingsView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static andre.pt.passwordgenerator.Constants.NOTIFICATION_CHANNEL_ID;

public class SettingsFragment extends Fragment implements ISettingsView, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ISettingsPresenter presenter;
    private IPreferencesManager preferencesManager;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        presenter.onCreate(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onStop();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof ISettingsActivity){
            preferencesManager = ((ISettingsActivity)context).getGenerator().getPreferencesManager();
            presenter = ((ISettingsActivity)context).getGenerator().getSettingsPresenter();
        }
    }

    @Override
    public void prepareRecyclerView(List<Option> options) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OptionAdapter(options, getContext(), this));
    }

    @Override
    public void showNotification(int id) {
        try {
            Intent it = new Intent(Objects.requireNonNull(getContext()).getApplicationContext(), GeneratorActivity.class);
            it.setFlags(it.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

            NotificationUtilities.notify(getContext(), id)
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
        NotificationUtilities.dismiss(getContext(), id);
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
