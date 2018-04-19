package andre.pt.passwordgenerator.Utilities;

import android.content.Intent;

public interface INotificationManager {
    void hide(int id);
    void show(int id, String title, String body, Intent onClick);
}
