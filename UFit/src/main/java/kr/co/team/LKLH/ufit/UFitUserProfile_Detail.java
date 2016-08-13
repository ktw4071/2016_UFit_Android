package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class UFitUserProfile_Detail extends AppCompatActivity {

    Toolbar toolbar;
    ImageView tbLeft, tbRight;
    TextView tbHead;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_user_profile_detail);

        toolbar = (Toolbar)findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView) findViewById(R.id.uf_toolbar_head);
        tbHead.setText("상세정보");
        tbLeft = (ImageView)findViewById(R.id.uf_toolbar_left);
        tbLeft.setImageResource(R.drawable.btn_topleft);
        tbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tbRight= (ImageView)findViewById(R.id.uf_toolbar_right);
        tbRight.setVisibility(View.INVISIBLE);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.detail_size, UFitUserProfile_DetailSizeCard.newInstance());
        ft.commit();
    }
}
