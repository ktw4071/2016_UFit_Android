package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class UFitSettingActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView tbLeft, tbRight, notifyToggle;
    TextView tbHead;
    boolean NotifyState;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_setting);

        toolbar = (Toolbar)findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);

        tbLeft = (ImageView)findViewById(R.id.uf_toolbar_left);
        tbRight= (ImageView)findViewById(R.id.uf_toolbar_right);
        tbHead = (TextView) findViewById(R.id.uf_toolbar_head);
        tbLeft.setImageResource(R.drawable.btn_back);
        tbHead.setText(R.string.uf_setting);
        tbRight.setVisibility(View.INVISIBLE);
        tbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UFitSettingActivity.this, UFitMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        notifyToggle = (ImageView)findViewById(R.id.setting_notify);
        notifyToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NotifyState) {
                    notifyToggle.setImageResource(R.drawable.btn_on);
                    NotifyState = true;
                } else {
                    notifyToggle.setImageResource(R.drawable.btn_off);
                    NotifyState = false;
                }
            }
        });
        findViewById(R.id.setting_mail_to_dev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2016-08-16 개발자 메일 진짜 메일주소로 수정하기
                startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:sdsd4564@gmail.com")));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
}
