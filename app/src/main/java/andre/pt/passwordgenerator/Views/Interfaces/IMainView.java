package andre.pt.passwordgenerator.Views.Interfaces;

import java.util.List;

import andre.pt.passwordgenerator.Data.Option;

public interface IMainView {
    void prepareRecyclerView(List<Option> options);

    void changeGenerateButtonState(boolean visibility);
}
