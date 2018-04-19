package andre.pt.passwordgenerator.Presentes.Interfaces;

import andre.pt.passwordgenerator.Views.Interfaces.IMainView;

public interface IMainPresenter {
    void onCreate(IMainView view);
    void onPause();
    void onResume();
    void onStop();
    void handleClick(String id, boolean active);
}
