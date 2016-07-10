package iview.wsienski.androidbattliefield;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Witold Sienski on 10.07.2016.
 */
public class MsgService extends IntentService {

    public final static String TAG = MsgService.class.getSimpleName();
    public final static String EXTRA_MSG = "extra_message";

    private long time = 2000;
    private Handler handler;


    public MsgService() {
        super(TAG);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler = new Handler();
        return super.onStartCommand(intent, flags, startId);
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
        Log.d(TAG, text);
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
