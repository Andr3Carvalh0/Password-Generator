package andre.pt.passwordgenerator;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.IGeneratorPresenter;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.Presentes.GeneratorPresenter;
import andre.pt.passwordgenerator.Presentes.SettingsPresenter;
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import static andre.pt.passwordgenerator.Constants.NOTIFICATION_KEY;

public class Generator extends Application implements IPreferencesManager {
    private IGeneratorPresenter mainPresenter;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public IGeneratorPresenter getMainPresenter() {
        if(mainPresenter == null)
            mainPresenter = new GeneratorPresenter();

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
