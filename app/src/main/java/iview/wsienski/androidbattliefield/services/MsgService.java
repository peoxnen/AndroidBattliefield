package iview.wsienski.androidbattliefield.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.TaskStackBuilder;

import iview.wsienski.androidbattliefield.R;
import iview.wsienski.androidbattliefield.activites.MainActivity;

/**
 * Created by Witold Sienski on 10.07.2016.
 */
public class MsgService extends IntentService {

    public final static String TAG = MsgService.class.getSimpleName();
    public final static String EXTRA_MSG = "extra_message";
    public final static int NOTIFICATION_ID = 5555;

    private long time = 2000;
    private Handler handler;


    public MsgService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
                wait(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MSG);
        showText(text);
    }

    private void showText(final String text) {
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(MainActivity.class);
        taskStackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setContentIntent(pendingIntent)
                .setContentText(text);

        Notification notification = builder.getNotification();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
