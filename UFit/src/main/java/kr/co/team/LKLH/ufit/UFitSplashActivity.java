package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

public class UFitSplashActivity extends AppCompatActivity {

    Handler splashHander;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_splash);

        final String token = PropertyManager.getInstance().getFieldFacebookTokenKey();

        splashHander = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Intent intent;
                if (!TextUtils.isEmpty(token)){
                    intent = new Intent(UFitSplashActivity.this, UFitMainActivity.class);
                    intent.putExtra("token", token);
                } else {
                    intent = new Intent(UFitSplashActivity.this, UFitLoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashHander.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
