package iview.wsienski.androidbattliefield.activites;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iview.wsienski.androidbattliefield.R;
import iview.wsienski.androidbattliefield.adapters.CardAdapter;
import iview.wsienski.androidbattliefield.services.MsgService;
import iview.wsienski.androidbattliefield.services.OdometerService;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textview)
    TextView textView;

    String[] names = {"one", "two", "three"};
    int[] images =  { R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};

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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        CardAdapter cardAdapter = new CardAdapter(names, images);
        recyclerView.setAdapter(cardAdapter);
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
