package andre.pt.passwordgenerator;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.Presentes.MainPresenter;
import andre.pt.passwordgenerator.Presentes.SettingsPresenter;
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import static andre.pt.passwordgenerator.Constants.NOTIFICATION_KEY;

public class Generator extends Application implements IPreferencesManager {
    private IMainPresenter mainPresenter;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public IMainPresenter getMainPresenter() {
        if(mainPresenter == null)
            mainPresenter = new MainPresenter();

        return mainPresenter;
    }

    @Override
    public boolean pullValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void pushValues(Option... options) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();

        for (Option option : options)
            edit.putBoolean(option.getId(), option.isActive());

        edit.apply();
    }

    public ISettingsPresenter getSettingsPresenter() {
        return new SettingsPresenter(pullValue(NOTIFICATION_KEY));
    }

    public IPreferencesManager getPreferencesManager() {
        return this;
    }
}
