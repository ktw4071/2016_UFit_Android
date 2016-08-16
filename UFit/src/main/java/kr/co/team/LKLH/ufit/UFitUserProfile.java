package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.github.lzyzsd.circleprogress.DonutProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class UFitUserProfile extends AppCompatActivity {

    Toolbar toolbar;
    TextView tbHead, member_name, member_birthdate, member_phonenumber, uf_member_profile_weight_value, uf_member_profile_workout_level_value, uf_member_profile_size_value;
    ImageView tbLeft, tbRight;
    CircleImageView profileImg;
    Intent intent;
    Timer timer;
    DonutProgress donut_process_weight, donut_process_workout_level, donut_process_size;
    int maximum_progress_value;
    int minimum_progress_value;
    int case_integer;
    int _mid;
    int weight_process_value;
    int workout_level_value;
    int size_value;
    int goal;
    int level;
    int achieve;

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
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_user_profile);

        intent = getIntent();
        _mid = intent.getExtras().getInt("_mid");
        new MemberProfile().execute(_mid);

        jsonObject = new JSONObject();


//        name = intent.getExtras().getString("_mid");
//        thumbnail = intent.getExtras().getString("_thumbnail");

        //profile information after asynctask
        member_name = (TextView)findViewById(R.id.member_profile_name);
        member_birthdate = (TextView)findViewById(R.id.member_profile_birthdate);
        member_phonenumber = (TextView)findViewById(R.id.member_profile_phonenumber);
        profileImg = (CircleImageView) findViewById(R.id.uf_user_profile_img);

        //donut process
        donut_process_weight = (DonutProgress)findViewById(R.id.donut_process_weight);
        donut_process_weight.setRotation(270);
        donut_process_workout_level = (DonutProgress)findViewById(R.id.donut_process_workout_level);
        donut_process_workout_level.setRotation(270);
        donut_process_size = (DonutProgress)findViewById(R.id.donut_process_size);
        donut_process_size.setRotation(270);
        uf_member_profile_weight_value = (TextView)findViewById(R.id.uf_member_profile_weight_value);
        uf_member_profile_workout_level_value = (TextView)findViewById(R.id.uf_member_profile_workout_level_value);
        uf_member_profile_size_value = (TextView)findViewById(R.id.uf_member_profile_size_value);



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


        toolbar = (Toolbar) findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView) findViewById(R.id.uf_toolbar_head);
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



        final LinearLayout userCircle = (LinearLayout) findViewById(R.id.uf_user_circleprogress);
        userCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(UFitUserProfile.this, UFitUserProfile_Detail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("maximum_progress_value", maximum_progress_value);
                intent.putExtra("minimum_progress_value", minimum_progress_value);
                intent.putExtra("case_integer", case_integer);
                intent.putExtra("_mid", _mid);
                intent.putExtra("weight_process_value", weight_process_value);
                intent.putExtra("workout_level_value", workout_level_value);
                intent.putExtra("size_value", size_value);
                intent.putExtra("goal", goal);
                intent.putExtra("level", level);
                intent.putExtra("achieve", achieve);


                startActivity(intent);
            }
        });
    }
    ////////////////////신경쓰지말자




    @Override
    protected void onResume() {
        super.onResume();
        // 유저 프로필 확대 사진
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySerializableData serializableData = new mySerializableData();
                serializableData.mCircleImageView = profileImg;
                getSupportFragmentManager().beginTransaction()
                                           .add(UFitImageViewer.newInstance(serializableData, jsonObject,
                                                   UFitNetworkConstantDefinition.URL_UFIT_MEMBER_IMAGE, 2), "memImage")
                                           .addToBackStack("memImage")
                                           .commit();
            }
        });
        // 유저 프로필 사진 수정
        findViewById(R.id.uf_user_profile_img_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UFitUserProfile.this, UFitImageUploadHelper.class);
                intent.putExtra("code", 0);
                intent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_MEMBER_IMAGE);
                intent.putExtra("_mid", _mid);
                startActivityForResult(intent, 0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        (new MemberProfile()).execute(_mid);
    }

    class MemberProfile extends AsyncTask<Integer, Integer, UFitEntityObject>{
        @Override
        protected UFitEntityObject doInBackground(Integer... integers) {
            UFitEntityObject uFitEntityObject = UFitHttpConnectionHandler.memberProfile(_mid);
            try {
                jsonObject.put("_image", uFitEntityObject._image);
                jsonObject.put("_mid", _mid);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return uFitEntityObject;
        }

        @Override
        protected void onPostExecute(UFitEntityObject uFitEntityObject) {
            //SET main UI - member profile information
            tbHead.setText(uFitEntityObject._name);
            member_name.setText(uFitEntityObject._name);
            member_birthdate.setText(uFitEntityObject._birth);
            member_phonenumber.setText(uFitEntityObject._number);
            if (uFitEntityObject._thumbnail == null) {
                profileImg.setImageResource(R.drawable.iiii);
            } else {
                Glide.with(UFitApplication.getUFitContext()).load(uFitEntityObject._thumbnail).into(profileImg);
            }

            //SET main UI  - DonutProcess
            Log.e("_weight", "" + uFitEntityObject._weight);
            Log.e("_initial", "" + uFitEntityObject._initial);
            Log.e("_goal", "" + uFitEntityObject._goal);

            goal = uFitEntityObject._goal;
            level = uFitEntityObject._level;
            achieve = uFitEntityObject._achieve;
            int initial = uFitEntityObject._initial;
            int weight = uFitEntityObject._weight;
            int diff_initial_goal;
            int diff_initial_weight;


            if(initial > goal){
                diff_initial_goal = initial - goal;
                diff_initial_weight = initial - weight;
                if(weight > initial){
                    weight_process_value = 0;
                }
                else{
                    weight_process_value = (diff_initial_weight*100) / diff_initial_goal;
                    if(weight_process_value > 100){
                        weight_process_value = 100;
                    }
                }
            }

            else if(goal > initial){
                diff_initial_goal = goal - initial; // 5
                diff_initial_weight = weight - initial; // 1
                if(initial > weight){
                    weight_process_value = 0;
                }
                else{
                    weight_process_value = (diff_initial_weight*100) / diff_initial_goal;
                    if(weight_process_value > 100){
                        weight_process_value = 100;
                    }
                }
            }

            workout_level_value = (level*100)/5;
            size_value = (achieve*100)/5;

            maximum_progress_value = Math.max(Math.max(weight_process_value, workout_level_value), size_value);
            minimum_progress_value = Math.min(Math.min(weight_process_value, workout_level_value), size_value);
            if(maximum_progress_value == weight_process_value){
                if(minimum_progress_value == workout_level_value)
                    case_integer = 1;
                else
                    case_integer = 2;
            }
            else if(maximum_progress_value == workout_level_value){
                if(minimum_progress_value == weight_process_value)
                    case_integer = 3;
                else
                    case_integer = 4;
            }
            else if(maximum_progress_value == size_value){
                if(minimum_progress_value == weight_process_value)
                    case_integer = 5;
                else
                    case_integer = 6;
            }


//            donut_process_weight.setProgress(weight_process_value);
            uf_member_profile_weight_value.setText(goal + " KG");
            uf_member_profile_workout_level_value.setText("LV " + level);
            uf_member_profile_size_value.setText(achieve + "/5");
//            donut_process_workout_level.setProgress(workout_level_value);
//            donut_process_size.setProgress(size_value);

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (case_integer){
                                case 1:
                                    if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_size.getProgress() != size_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                case 2:
                                    if(donut_process_size.getProgress() != size_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                case 3:
                                    if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_size.getProgress() != size_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    }
                                    else if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                case 4:
                                    if(donut_process_size.getProgress() != size_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                case 5:
                                    if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    }
                                    else if(donut_process_size.getProgress() != size_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                case 6:
                                    if(donut_process_workout_level.getProgress() != workout_level_value){
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                        donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else if(donut_process_weight.getProgress() != weight_process_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                        donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    }
                                    else if(donut_process_size.getProgress() != size_value){
                                        donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    }
                                    else
                                        timer.cancel();

                                    break;

                                default:
                                    timer.cancel();
                            }
                        }
                    });
                }
            }, 700, 10);

            super.onPostExecute(uFitEntityObject);
        }
    }

}
