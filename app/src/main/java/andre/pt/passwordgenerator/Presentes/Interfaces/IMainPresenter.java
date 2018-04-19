package andre.pt.passwordgenerator.Presentes.Interfaces;

import andre.pt.passwordgenerator.Views.Interfaces.IMainView;

public interface IMainPresenter {
    void onCreate(IMainView view);
    void onPause();
    void onResume(IMainView view);
    void handleOnClick(String id, boolean active);
}
