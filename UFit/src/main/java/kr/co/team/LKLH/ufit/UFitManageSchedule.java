package kr.co.team.LKLH.ufit;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UFitManageSchedule extends AppCompatActivity implements MemberItemAdapter.DeleteMemberCallback {

    private static final int SCHEDULE_ADD_IN_LIST = 0;

    //날짜계산을 해보자
    Calendar calender = Calendar.getInstance(Locale.getDefault());
    Toolbar toolbar;
    ImageView tbLeft, tbRight;
    TextView tbHead;

    int this_year = calender.get(Calendar.YEAR);
    int this_month = calender.get(Calendar.MONTH);
    int this_maximum_day = calender.getActualMaximum(calender.DAY_OF_MONTH);

    int currentPosition;

    TextView textView;
    GridView gridView;
    TextView clickedDate;

    ViewPager pager;
    CalendarFragmentPagerAdapter mFragmentAdapter = new CalendarFragmentPagerAdapter(getSupportFragmentManager(), this_year, this_month);

    static final String[] monday_to_sunday = new String[]{"S", "M", "T", "W", "T", "F", "S"};

    //선택날짜의 회원리스트를 리싸이클러뷰로 표현해보자
    private List<DummyDatePoooool> memberList = new ArrayList<>();
    private MemberItemAdapter memberRecyclerViewAdapter;
    boolean member_deleted;
    @Override
    public void deleteMethodCallBack(final ArrayList<UFitEntityObject> list, final UFitEntityObject object, final MemberItemAdapter adapter) {
        AlertDialog dialog = null;
        DialogInterface.OnClickListener dialog_schedule_member_delete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new MainDeleteMemberSchedule().execute(object._sid);
                list.remove(object);
                adapter.notifyDataSetChanged();
            }
        };
        DialogInterface.OnClickListener dialog_schedule_member_cancel = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        dialog = new AlertDialog.Builder(this)
                .setTitle("스케쥴 삭제")
                .setPositiveButton("삭제", dialog_schedule_member_delete)
                .setNegativeButton("취소", dialog_schedule_member_cancel)
                .setCancelable(true).create();

        dialog.show();

        Log.i("leelog", member_deleted + "");
    }

    public class MainDeleteMemberSchedule extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            UFitHttpConnectionHandler.deleteMemberSchedule(integers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_manage_schedule_sub);

        textView = (TextView) findViewById(R.id.tv_date);
        gridView = (GridView) findViewById(R.id.grid_view_monday_to_sunday);
        clickedDate = (TextView) findViewById(R.id.today);
        final ImageView regSchedule = (ImageView)findViewById(R.id.schedule_regist);
        regSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(UFitSchedulePlus.newInstance(clickedDate.getText().toString()), "schedule_add").addToBackStack("schedule_add").commit();*/
                Intent intent = new Intent(UFitManageSchedule.this, UFitMemberManagementRegister.class);
                intent.putExtra("date", clickedDate.getText().toString());
                startActivityForResult(intent, SCHEDULE_ADD_IN_LIST);
            }
        });



        textView.setText(this_year + " . " + (this_month + 1));
        clickedDate.setText(this_year + ". " + String.format("%02d", (this_month + 1)) + ". " + String.format("%02d", (calender.get(Calendar.DAY_OF_MONTH) + 1)));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, monday_to_sunday);
        gridView.setAdapter(adapter);


        pager = (ViewPager) findViewById(R.id.viewpager1);
        pager.setAdapter(mFragmentAdapter);
        pager.setCurrentItem(this_month + 20736);
        currentPosition = pager.getCurrentItem();
        Log.e("메인에서의 포지션", "" + currentPosition);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

        toolbar = (Toolbar)findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView)findViewById(R.id.uf_toolbar_head);
        tbHead.setText(R.string.uf_schedule);
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


//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.manage_schedule_rcv, UFitRightDrawRCV.newInstance());
//        ft.commit();
        RecyclerView rv = (RecyclerView)findViewById(R.id.ufit_manage_schedule_recycleview);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UFitApplication.getUFitContext());
        rv.setLayoutManager(mLayoutManager);

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UFitApplication.getUFitContext(), "눌렀어요", Toast.LENGTH_SHORT).show();
            }
        });

    }


}