package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class UFitUserProfile extends AppCompatActivity {

    Toolbar toolbar;
    TextView tbHead;
    ImageView tbLeft, tbRight;
    CircleImageView profileImg;
    Intent intent;
    String name;
    String thumbnail;

    Calendar calender = Calendar.getInstance(Locale.getDefault());
    int this_year = calender.get(Calendar.YEAR);
    int this_month = calender.get(Calendar.MONTH);

    //달력부분
    GridView gridView;
    ViewPager ufitMemberCalendar;
    MemberCalendarFragmentAdapter mFragmentAdapter = new MemberCalendarFragmentAdapter(getSupportFragmentManager(), this_year, this_month);
    TextView textView;
    TextView clickedDate;

    //운동부위 표현 리싸이클러
    RecyclerView workoutPartRecyclerView;

    private MemberItemAdapter memberRecyclerViewAdapter;


    static final String[] monday_to_sunday = new String[]{"S", "M", "T", "W", "T", "F", "S"};

    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_user_profile);

        gridView = (GridView) findViewById(R.id.grid_view_monday_to_sunday);
        ufitMemberCalendar = (ViewPager) findViewById(R.id.uf_member_calendar_viewpager);
        textView = (TextView) findViewById(R.id.uf_member_calendar_date);
        clickedDate = (TextView) findViewById(R.id.today);
        workoutPartRecyclerView = (RecyclerView)findViewById(R.id.workout_part_recyclerview);

        textView.setText(this_year + " . " + (this_month + 1));
        clickedDate.setText(this_year + ". " + String.format("%02d", (this_month + 1)) + ". " + String.format("%02d", (calender.get(Calendar.DAY_OF_MONTH) + 1)));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monday_to_sunday);
        gridView.setAdapter(adapter);

        ufitMemberCalendar.setAdapter(mFragmentAdapter);
        ufitMemberCalendar.setCurrentItem(this_month + 20736);
        currentPosition = ufitMemberCalendar.getCurrentItem();
        Log.e("메인에서의 포지션", "" + currentPosition);
        ufitMemberCalendar.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageSelected(int position) {
                if (currentPosition < position) {
                    if (this_month == 11) {
                        this_month = 0;
                        this_year++;
                    } else
                        this_month++;
                } else if (currentPosition > position) {
                    if (this_month == 0) {
                        this_month = 11;
                        this_year--;
                    } else
                        this_month--;
                }
                currentPosition = position;

                textView.setText(this_year + " . " + (this_month + 1));

            }

        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UFitApplication.getUFitContext(), LinearLayoutManager.HORIZONTAL, false);
        workoutPartRecyclerView.setLayoutManager(mLayoutManager);


////////////////// 신경쓰지말자///////////
        intent = getIntent();
        name = intent.getExtras().getString("_name");
        thumbnail = intent.getExtras().getString("_thumbnail");

        toolbar = (Toolbar) findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView) findViewById(R.id.uf_toolbar_head);
        tbHead.setText(name);
        tbLeft = (ImageView) findViewById(R.id.uf_toolbar_left);
        tbLeft.setImageResource(R.drawable.btn_topleft);
        tbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tbRight = (ImageView) findViewById(R.id.uf_toolbar_right);
        tbRight.setVisibility(View.INVISIBLE);

        profileImg = (CircleImageView) findViewById(R.id.uf_user_profile_img);
        Glide.with(UFitApplication.getUFitContext()).load(thumbnail).into(profileImg);

        final LinearLayout userCircle = (LinearLayout) findViewById(R.id.uf_user_circleprogress);
        userCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitUserProfile.this, UFitUserProfile_Detail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    ////////////////////신경쓰지말자
}
