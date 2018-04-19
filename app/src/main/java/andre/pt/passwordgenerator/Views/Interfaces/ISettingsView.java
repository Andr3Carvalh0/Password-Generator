package andre.pt.passwordgenerator.Views.Interfaces;

import java.util.List;

import andre.pt.passwordgenerator.Data.Option;

public interface ISettingsView {
    void prepareRecyclerView(List<Option> optionList);
    void showNotification(int id);
    void hideNotification(int id);

    void savePreferences(Option... options);
}
