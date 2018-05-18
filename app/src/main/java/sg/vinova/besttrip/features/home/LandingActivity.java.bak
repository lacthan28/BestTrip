package sg.vinova.besttrip.features.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import sg.vinova.besttrip.R;

public class LandingActivity extends Activity {
    static int a = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                a = 1;
                int localvalue = a;
                a = ++localvalue;
            }
        }).start();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("LandingActivity",a+"");
            }
        },5000);
    }
}
