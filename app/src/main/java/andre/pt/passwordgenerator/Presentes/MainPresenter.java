package andre.pt.passwordgenerator.Presentes;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.LinkedList;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Modal.Interfaces.IPasswordGenerator;
import andre.pt.passwordgenerator.Modal.PasswordGenerator;
import andre.pt.passwordgenerator.Presentes.Interfaces.BasePresenter;
import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;
import static andre.pt.passwordgenerator.Constants.LOWER_CASE_KEY;
import static andre.pt.passwordgenerator.Constants.NUMBERS_KEY;
import static andre.pt.passwordgenerator.Constants.UPPER_CASE_KEY;

public class MainPresenter extends BasePresenter implements IMainPresenter {
    private IMainView view;
    private IPasswordGenerator passwordGenerator;

    public MainPresenter() {
        passwordGenerator = new PasswordGenerator();

        optionList = new LinkedList<>();
        optionList.add(new Option(UPPER_CASE_KEY, R.drawable.ic_letter_size, R.string.Uppercase, true));
        optionList.add(new Option(LOWER_CASE_KEY, R.drawable.ic_letter_size, R.string.Lowercase, true));
        optionList.add(new Option(NUMBERS_KEY, R.drawable.ic_numbers, R.string.Numbers, true));

        onClickRouter = new HashMap<>();
        onClickRouter.put(UPPER_CASE_KEY, (active) -> {
            getOptionWithID(UPPER_CASE_KEY).setActive(active);

            if(active)
                passwordGenerator.addUpperCase();
            else
                passwordGenerator.removeUpperCase();
        });
        onClickRouter.put(LOWER_CASE_KEY, (active) -> {
            getOptionWithID(LOWER_CASE_KEY).setActive(active);

            if(active)
                passwordGenerator.addLowerCase();
            else
                passwordGenerator.removeLowerCase();
        });
        onClickRouter.put(NUMBERS_KEY, (active) -> {
            getOptionWithID(NUMBERS_KEY).setActive(active);

            if(active)
                passwordGenerator.addNumbers();
            else
                passwordGenerator.removeNumbers();
        });
    }

    @Override
    public void onCreate(@NonNull IMainView view) {
        this.view = view;

        view.prepareRecyclerView(optionList);
        view.changeGenerateButtonState(passwordGenerator.canGeneratePassword());
    }

    @Override
    public void onPause() {
        view = null;
    }

    @Override
    public void onResume(@NonNull IMainView view) {
        onCreate(view);
    }

    @Override
    public void handleOnClick(String id, boolean active) {
        if(super.handleClick(id, active) && view != null)
            view.changeGenerateButtonState(passwordGenerator.canGeneratePassword());
    }

    @Override
    public void generatePassword() {
        final String pass = passwordGenerator.generatePassword();

        if(view != null)
            view.acceptPassword(pass);
    }
}
