package andre.pt.passwordgenerator.Presentes.Interfaces;

import andre.pt.passwordgenerator.Views.Interfaces.ISettingsView;

public interface ISettingsPresenter {
    void onCreate(ISettingsView view);
    void handleOnClick(String id, boolean active);
    void onResume(ISettingsView view);
    void onStop();
}
