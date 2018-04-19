package andre.pt.passwordgenerator;

import android.app.Application;

import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.Presentes.MainPresenter;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;

public class Generator extends Application {
    private IMainPresenter mainPresenter;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public IMainPresenter getMainPresenter() {
        if(mainPresenter == null)
            mainPresenter = new MainPresenter();

        return mainPresenter;
    }
}
