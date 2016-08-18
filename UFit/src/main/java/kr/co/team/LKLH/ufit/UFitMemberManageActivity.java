package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

public class UFitMemberManageActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView tbLeft, tbRight;
    TextView tbHead;
    EditText SearchMember;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_mem_manage);

        toolbar = (Toolbar)findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView)findViewById(R.id.uf_toolbar_head);
        tbHead.setText("회원관리");
        tbLeft = (ImageView)findViewById(R.id.uf_toolbar_left);
        tbLeft.setImageResource(R.drawable.btn_back);
        tbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tbRight = (ImageView)findViewById(R.id.uf_toolbar_right);
        tbRight.setImageResource(R.drawable.btn_plus_8);
        tbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("왜안켜짐", "SINE");
                getSupportFragmentManager().beginTransaction().add(UFitMemberPlus.newInstance(), "schedule_plus")
                        .addToBackStack("schedule_plus").commit();
            }
        });
        SearchMember = (EditText)findViewById(R.id.mem_manage_search);
        SearchMember.setRawInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.uf_mem_manage_rcv, UFitMemberManagementRCV.newInstance());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.finish();
    }
}
