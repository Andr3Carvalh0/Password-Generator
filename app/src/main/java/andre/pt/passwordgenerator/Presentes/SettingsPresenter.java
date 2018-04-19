package andre.pt.passwordgenerator.Presentes;

import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.LinkedList;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.BasePresenter;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.ISettingsView;
import static andre.pt.passwordgenerator.Constants.NOTIFICATION_KEY;

public class SettingsPresenter extends BasePresenter implements ISettingsPresenter {

    private ISettingsView view;
    private final int NOTIFICATION_ID = 27;

    public SettingsPresenter(boolean showNotification){
        optionList = new LinkedList<>();
        optionList.add(new Option(NOTIFICATION_KEY, R.drawable.ic_notifications, R.string.Notifications, showNotification));

        onClickRouter = new HashMap<>();
        onClickRouter.put(NOTIFICATION_KEY, (active) -> {

            getOptionWithID(NOTIFICATION_KEY).setActive(active);

            if(active)
                showNotification();
            else
                hideNotification();
        });
    }

    private void showNotification(){
        if(view != null)
            view.showNotification(NOTIFICATION_ID);
    }

    private void hideNotification(){
        if(view != null)
            view.hideNotification(NOTIFICATION_ID);
    }

    @Override
    public void onCreate(@NonNull ISettingsView view) {
        this.view = view;
        Option notification = getOptionWithID(NOTIFICATION_KEY);

        hideNotification();

        if(notification.isActive())
            showNotification();

        view.prepareRecyclerView(optionList);
    }

    @Override
    public void handleOnClick(String id, boolean active) {
        super.handleClick(id, active);
    }

    @Override
    public void onResume(ISettingsView view) {
        this.view = view;
    }

    @Override
    public void onStop() {
        if(view != null)
            view.savePreferences(getOptionWithID(NOTIFICATION_KEY));

        view = null;
    }
}
