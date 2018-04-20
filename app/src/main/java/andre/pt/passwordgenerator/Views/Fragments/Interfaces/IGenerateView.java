package andre.pt.passwordgenerator.Views.Fragments.Interfaces;

import java.util.List;

import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.IGeneratorActivity;

public interface IGenerateView {

    void prepareRecyclerView(List<Option> options);
    void changeGenerateButtonState(boolean visibility);
    void acceptPassword(String password);
}
