package kr.co.team.LKLH.ufit;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codetroopers.betterpickers.timepicker.TimePickerBuilder;
import com.codetroopers.betterpickers.timepicker.TimePickerDialogFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UFitMemberManagementRegister extends AppCompatActivity {


    TextView dateTime,selectedName;
    DrawerLayout memSelectDraw;
    LinearLayout selectedMember;
    ImageView selectedImg;
    TextView selectedTime;
    int year, month, day, hour, minute;
    Intent addForList;
    String _date, _time;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    public void setMember(String memberImg, String memberName){
        if (memberImg != null) {
            Glide.with(UFitApplication.getUFitContext()).load(memberImg).into(selectedImg);
        } else {
            selectedImg.setImageResource(R.drawable.avatar_m);
        }
        selectedName.setText(memberName);
        memSelectDraw.closeDrawers();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ufit_member_draw);

        //시간선택을 위한 그레고리안 캐린더 시간과 분
        GregorianCalendar calendar = new GregorianCalendar();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute= calendar.get(Calendar.MINUTE);

        //Dialog 화면을 꽉차게
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(DrawerLayout.LayoutParams.MATCH_PARENT, DrawerLayout.LayoutParams.MATCH_PARENT);

        //맨위의 현재 날짜 표시
        final Intent intent = getIntent();
        _date = intent.getStringExtra("date").substring(0, 4) + intent.getStringExtra("date").substring(6, 8) + intent.getStringExtra("date").substring(10, 12);
        dateTime = (TextView)findViewById(R.id.explain_reg);
        dateTime.setText(intent.getStringExtra("date").substring(0,4)+"년 "
                        + intent.getStringExtra("date").substring(6, 8)+"월 "
                        + intent.getStringExtra("date").substring(10, 12)+"일" );
        Log.e("JOB FOR ME", intent.getStringExtra("date"));
        memSelectDraw = (DrawerLayout)findViewById(R.id.uf_mem_draw);

        //닫기버튼
        findViewById(R.id.uf_schedule_reg_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //DrawLayout 리사이클러뷰
        final Bundle bundle = new Bundle();;
        if(savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            UFitMemberListSelector uFitMemberListSelector = UFitMemberListSelector.newInstance();
            uFitMemberListSelector.setBundle(bundle);
            ft.replace(R.id.memberlist_rcv, uFitMemberListSelector);
            ft.commit();
        }

        selectedName = (TextView)findViewById(R.id.selected_member_name);
        selectedImg = (CircleImageView)findViewById(R.id.selected_member_img);
        selectedMember = (LinearLayout)findViewById(R.id.selected_member);
        selectedMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memSelectDraw.openDrawer(GravityCompat.END);
                Log.e("TTAAGG", bundle.toString());
            }
        });
        selectedTime = (TextView)findViewById(R.id.selected_time);
        selectedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*TimePickerBuilder tpb = new TimePickerBuilder()
                                            .setFragmentManager(getSupportFragmentManager())
                                            .setStyleResId(R.style.BetterPickersDialogFragment);
                tpb.show();
                if (hour == 0) {
                    bundle.putString("_time", ""+minute);
                    Log.e("TAG", bundle.toString());
                } else {
                    bundle.putString("_time", hour+""+String.format("%02d", minute));
                    Log.e("TAG", bundle.toString());
                }*/
                TimePickerDialog timePickerDialog =
                        new TimePickerDialog(UFitMemberManagementRegister.this, AlertDialog.THEME_HOLO_LIGHT
                                , setListener, hour, minute, false);
                timePickerDialog.show();
                if (hour == 0) {
                    bundle.putString("_time", ""+minute);
                    Log.e("TAG", bundle.toString());
                } else {
                    bundle.putString("_time", hour+""+String.format("%02d", minute));
                    Log.e("TAG", bundle.toString());
                }
            }
        });

        // 최종 스케줄 입력
        findViewById(R.id.schedule_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bundle.containsKey("_time") || !bundle.containsKey("_mid")){
                    Toast.makeText(UFitMemberManagementRegister.this, R.string.uf_schedule_add_fail, Toast.LENGTH_SHORT).show();
                } else {
                    new AddSchedule().execute("1", Integer.toString(bundle.getInt("_mid")), _date, bundle.getString("_time"));
                    Toast.makeText(UFitMemberManagementRegister.this, R.string.uf_schedule_add_success, Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }


    private TimePickerDialog.OnTimeSetListener setListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minuteq) {
            final String PM = getResources().getString(R.string.uf_pm);
            final String AM = getResources().getString(R.string.uf_am);
            if (hourOfDay > 12) {
                selectedTime.setText(PM +" " + String.valueOf(hourOfDay-12) + ":" + String.format("%02d",minute));
            } else if (hourOfDay == 12) {
                selectedTime.setText(PM + " " +  (String.format( "%02d", hourOfDay )+ ":" + String.format("%02d",minute)));
            } else if (hourOfDay == 0) {
                selectedTime.setText(AM +" "+ ("12" + ":" + String.format("%02d",minute)));
            } else {
                selectedTime.setText(AM +" "+ String.valueOf(hourOfDay)+ ":" + String.format("%02d",minute));
            }

            hour = hourOfDay;
            minute = minuteq;
        }
    };
    /*@Override
    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
        final String PM = getResources().getString(R.string.uf_pm);
        final String AM = getResources().getString(R.string.uf_am);
        if (hourOfDay > 12) {
            selectedTime.setText(PM +" " + String.valueOf(hourOfDay-12) + ":" + String.format("%02d",minute));
        } else if (hourOfDay == 12) {
            selectedTime.setText(PM + " " +  (String.format( "%02d", hourOfDay )+ ":" + String.format("%02d",minute)));
        } else if (hourOfDay == 0) {
            selectedTime.setText(AM +" "+ ("12" + ":" + String.format("%02d",minute)));
        } else {
            selectedTime.setText(AM +" "+ String.valueOf(hourOfDay)+ ":" + String.format("%02d",minute));
        }

        hour = hourOfDay;
        this.minute = minute;
    }*/

    class AddSchedule extends AsyncTask<String, Integer, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            Log.i("strings[0]", "" + strings[0]);
            Log.i("strings[1]", "" + strings[1]);
            Log.i("strings[2]", "" + strings[2]);
            UFitHttpConnectionHandler.AddMember(strings[0], strings[1], strings[2], strings[3]);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    @Override
    public void onBackPressed() {
        if (memSelectDraw.isDrawerOpen(GravityCompat.END)) {
            memSelectDraw.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}
