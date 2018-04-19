package andre.pt.passwordgenerator;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.Presentes.Interfaces.ISettingsPresenter;
import andre.pt.passwordgenerator.Presentes.MainPresenter;
import andre.pt.passwordgenerator.Presentes.SettingsPresenter;
import andre.pt.passwordgenerator.Utilities.INotificationManager;
import andre.pt.passwordgenerator.Utilities.IPreferencesManager;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;

import static andre.pt.passwordgenerator.Constants.NOTIFICATION_KEY;

public class Generator extends Application implements IPreferencesManager, INotificationManager {
    private IMainPresenter mainPresenter;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public IMainPresenter getMainPresenter() {
        if(mainPresenter == null)
            mainPresenter = new MainPresenter();

        return mainPresenter;
    }

    @Override
    public boolean pullValue(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    @Override
    public void pushValues(Option... options) {
        final SharedPreferences.Editor edit = sharedPreferences.edit();

        for (Option option : options)
            edit.putBoolean(option.getId(), option.isActive());

        edit.apply();
    }

    public ISettingsPresenter getSettingsPresenter() {
        return new SettingsPresenter(pullValue(NOTIFICATION_KEY));
    }

    public INotificationManager getNotificationManager() {
        return this;
    }

    public IPreferencesManager getPreferencesManager() {
        return this;
    }

    @Override
    public void hide(int id) {
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel(id);
    }

    @Override
    public void show(int id, String title, String body, Intent onClick) {
        NotificationCompat.Builder notification;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new NotificationCompat.Builder(this, "General");
        }else{
            notification = new NotificationCompat.Builder(this);
        }

        notification
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentText(body)
            .setChannelId("General")
            .setAutoCancel(false)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(PendingIntent.getActivity(this, id, onClick, 0));

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(id, notification.build());
    }
}
