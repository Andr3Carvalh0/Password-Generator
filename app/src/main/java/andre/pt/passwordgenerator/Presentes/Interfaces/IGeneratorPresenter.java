package andre.pt.passwordgenerator.Presentes.Interfaces;

import andre.pt.passwordgenerator.Views.Fragments.Interfaces.IGenerateView;

public interface IGeneratorPresenter {
    void onCreate(IGenerateView view);
    void onPause();
    void onResume(IGenerateView view);
    void handleOnClick(String id, boolean active);
    void generatePassword();
}
