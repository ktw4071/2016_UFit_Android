package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UFitSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_splash);
        Handler handler = new Handler();
        handler.postDelayed(new splashhandler(), 1000);
    }
    private class splashhandler implements Runnable {
        public void run(){
            startActivity(new Intent(getApplication(), UFitMainActivity.class));
            UFitSplashActivity.this.finish();
        }
    }
}
