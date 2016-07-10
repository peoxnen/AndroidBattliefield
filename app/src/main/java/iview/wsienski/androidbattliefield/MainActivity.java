package iview.wsienski.androidbattliefield;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textView;

    private OdometerService odometerService;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) iBinder;
            odometerService = odometerBinder.getOdomete();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, OdometerService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(bound){
            unbindService(connection);
            bound = false;
        }
    }

    private void getDistance(){
        final Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        double distance = 0.0;
                        if(odometerService!=null){
                            distance = odometerService.getDistance();
                        }
                        String distanceString = String.format("%1$,.2f metrów",distance);
                        textView.setText(distanceString);
                        handler.postDelayed(this, 1000);
                    }
                });
    }

    @OnClick(R.id.btn_startService)
    public void pressBtn(View view) {
        Intent intent = new Intent(this, MsgService.class);
        intent.putExtra(MsgService.EXTRA_MSG, getString(R.string.btn_resp));
        startService(intent);
        getDistance();
    }
}
