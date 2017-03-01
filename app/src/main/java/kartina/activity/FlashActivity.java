package kartina.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import kartina.R;
import kartina.util.SharedpreferncesUtil;

public class FlashActivity extends AppCompatActivity {
    boolean isNormalStart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);

        isNormalStart = true;

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    public void run() {
                        if (SharedpreferncesUtil.getGuided(getApplicationContext())) {
                            Intent intent = new Intent(getApplicationContext(),
                                    BasicActivity.class);
                            startActivity(intent);
                            FlashActivity.this.finish();
                        } else {
                            Intent intent = new Intent(FlashActivity.this,
                                    WelcomeActivity.class);
                            FlashActivity.this.startActivity(intent);
                            FlashActivity.this.finish();
                        }

                    }
                });
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
