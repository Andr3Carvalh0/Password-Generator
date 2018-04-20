package andre.pt.passwordgenerator.Views.Fragments.Interfaces;

import java.util.List;

import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.ISettingsActivity;

public interface ISettingsView {
    void prepareRecyclerView(List<Option> optionList);
    void showNotification(int id);
    void hideNotification(int id);

    void savePreferences(Option... options);
}
