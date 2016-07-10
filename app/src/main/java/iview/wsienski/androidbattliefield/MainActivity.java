package iview.wsienski.androidbattliefield;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_startService)
    public void pressBtn(View view) {
        Intent intent = new Intent(this, MsgService.class);
        intent.putExtra(MsgService.EXTRA_MSG, getString(R.string.btn_resp));
        startService(intent);
    }
}
