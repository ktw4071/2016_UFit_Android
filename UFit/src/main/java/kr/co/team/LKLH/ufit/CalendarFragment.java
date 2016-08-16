package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 2016-08-01.
 */
public class CalendarFragment extends Fragment {
    private String title;
    private int page;

    /////프래그먼트가 리싸이클러뷰를 감싸는 코드 변수
    private RecyclerView mRecycler;
    private GridLayoutManager mGM;
    private int columnCount = 7;
    private DaysCellAdapter mAdapter;
    private View OldView;


    public static RecyclerView memberListRecycler;
    int[] monthlySchedule;



    //    private Calendar today = Calendar.getInstance(Locale.getDefault());


//    public CalendarFragment() {
//    }

    public static CalendarFragment newInstance(int year, int  month, int maximumDay, int startDay) {
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisMaximumDay", maximumDay);
        args.putInt("ThisStartDay", startDay);
        calendarFragment.setArguments(args);
        return calendarFragment;
    }

    public static CalendarFragment newInstance(int year, int  month, int maximumDay, int startDay, int today) {
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt("today", today);
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisMaximumDay", maximumDay);
        args.putInt("ThisStartDay", startDay);
        calendarFragment.setArguments(args);
        return calendarFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        int maximumDay = super.getArguments().getInt("ThisMaximumDay");
        int year = super.getArguments().getInt("ThisYear");
        int month = super.getArguments().getInt("ThisMonth");
        String _month = "" + (month + 1);
        if((month + 1)< 10){
            _month = "0" + (month + 1);
        }
        try {
            monthlySchedule = new TrainerMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximumDay).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
    }

    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        context = getContext();
        mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        memberListRecycler = (RecyclerView)getActivity().findViewById(R.id.ufit_manage_schedule_recycleview);

        final int maximumDay = super.getArguments().getInt("ThisMaximumDay");
        final int year = super.getArguments().getInt("ThisYear");
        final int month = super.getArguments().getInt("ThisMonth");
        final int startDay = super.getArguments().getInt("ThisStartDay");
//        final int[] monthlySchedule = super.getArguments().getIntArray("ThisMonthlySchedule");

        String _month = "" + (month + 1);
        if((month + 1)< 10){
            _month = "0" + (month + 1);
        }

//        new TrainerMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximumDay);
        Log.e("Cal_Frag_OnCreate", "" + monthlySchedule);
        mGM = new GridLayoutManager(super.getContext(), columnCount, 1, false);
        mRecycler.setLayoutManager(new GridLayoutManager(super.getContext(), columnCount, 1, false));
        mRecycler.addItemDecoration(new DividerItemDecoration(super.getContext(), 1));
//        mAdapter = new DaysCellAdapter(super.getContext(), maximumDay, startDay, monthlySchedule);
        mAdapter = new DaysCellAdapter(super.getContext(), maximumDay, startDay, monthlySchedule);
        mRecycler.setAdapter(mAdapter);

        final GestureDetector mGestureDetector = new GestureDetector(super.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

//        mRecycler.findViewHolderForAdapterPosition(10).itemView.performClick();

//        mRecycler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mRecycler.scrollToPosition(10);
//            }
//        },300);

        mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = mRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e) && rv.getChildAdapterPosition(child) > startDay - 2) {
                    Log.i("클릭한 날짜", "" + (rv.getChildAdapterPosition(child) - startDay + 2));

                    //날짜를 클릭하며 이동할때 전에 잡아둔 올드뷰의 써클(날짜선택)을 취소시킬때
                    if(OldView != null){

                        OldView.findViewById(R.id.todayCircle).setBackgroundResource(0);
                    }
//                    child.setBackgroundColor(Color.parseColor("#FFFF00"));
//                    child.setBackgroundResource(R.drawable.today_circle);


                    // 작은 출석체크 점 표시.
//                    ImageView test = (ImageView)child.findViewById(R.id.attend);
//                    test.setImageResource(R.drawable.schedule_circle);

                    //큰 오늘 선택 써클 표시
                    child.findViewById(R.id.todayCircle).setBackgroundResource(R.drawable.today_circle);

                    TextView schedule_date = (TextView)getActivity().findViewById(R.id.today);

                    schedule_date.setText(year + ". " + String.format("%02d", (month + 1)) + ". " + String.format("%02d", (rv.getChildAdapterPosition(child) - startDay + 2)));

                    ArrayList<UFitEntityObject> MemberList = new ArrayList<UFitEntityObject>();


//                  리싸이클러뷰 더미데이타 표현
                    /*
                    RecyclerView manage_schedule_recycleview = (RecyclerView) getActivity().findViewById(R.id.ufit_manage_schedule_recycleview);
                    List<DummyDatePoooool> memberList = new ArrayList<>();
                    for(int i = 0; i < (rv.getChildAdapterPosition(child) - startDay + 2); i++){
                        DummyDatePoooool dummyDatePoooool = new DummyDatePoooool();
                        dummyDatePoooool.setData(R.drawable.pic, "오늘회원님" + (i + 1), "2:" + i*2 + "pm");
                        memberList.add(dummyDatePoooool);
                    }
                    MemberItemAdapter memberRecyclerViewAdapter = new MemberItemAdapter(memberList);
                    manage_schedule_recycleview.setAdapter(memberRecyclerViewAdapter);
                    */

                    //전에 클릭한 날짜를 지우기 위해 올드뷰를 종료전에 설정.

                    OldView = child;

                    //Trainer's ID Value
                    String _tid = "1";

                    //Concatenate 0 to month that is between 1 - 9 ex) 1 -> 01, 9 -> 09
                    String _month = "" + (month + 1);
                    if((month + 1) < 10){
                        _month = "0" + (month + 1);
                    }
                    Log.i("_month",  _month);

                    //Concatenate 0 to day that is between 1 - 9 ex) 1 -> 01, 9 -> 09
                    String _day = "" + (rv.getChildAdapterPosition(child) - startDay + 2);
                    if((rv.getChildAdapterPosition(child) - startDay + 2) < 10){
                        _day = "0" + (rv.getChildAdapterPosition(child) - startDay + 2);
                    }
                    Log.i("_day",  _day);
                    String _date = "" + year + _month + _day;
                    Log.i("_tid, _date", _tid + " --- " + _date);
                    new SelectedDayMemberList().execute(_tid, _date);
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });



//        mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//                return false;
//            }
//
//            @Override
//            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//                Toast.makeText(getContext(), "adfdsf", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
        return view;
    }

    class TrainerMonthlySchedule extends AsyncTask<String, Integer, int[]> {


        @Override
        protected int[] doInBackground(String... strings) {
            return UFitHttpConnectionHandler.mainlist(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(int[] monthlyschedule_async) {
            monthlySchedule = Arrays.copyOf(monthlyschedule_async, monthlyschedule_async.length);
            super.onPostExecute(monthlySchedule);
        }
    }

    public class SelectedDayMemberList extends AsyncTask<String, Integer, ArrayList<UFitEntityObject>> {


        @Override
        protected ArrayList doInBackground(String... strings) {
            Log.i("who am i?","" + UFitHttpConnectionHandler.mainlist(strings[0], strings[1]));
            return UFitHttpConnectionHandler.mainlist(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> memberList) {
            ArrayList<UFitEntityObject> memberListContainer = new ArrayList<>();
            if (memberList != null && memberList.size() > 0) {
                for (int i = 0; i < memberList.size(); i++) {
                    Log.i("안녕&main 전부다나와라", memberList + "");
                    Log.i("안녕 !!!", memberList.get(i) + "");
                    UFitEntityObject d = new UFitEntityObject();
                    d._mid = memberList.get(i)._mid;
                    Log.i("안녕 ! mid", memberList.get(i)._mid + "");
                    d._name = memberList.get(i)._name;
                    Log.i("안녕 ! name", memberList.get(i)._name + "");
                    d._time = memberList.get(i)._time;
                    Log.i("안녕 ! time", memberList.get(i)._time + "");
                    memberListContainer.add(d);
                }
            }

            MemberItemAdapter memberRecyclerViewAdapter = new MemberItemAdapter(context, memberList, "UFitManageSchedule");
            memberListRecycler.setAdapter(memberRecyclerViewAdapter);

            super.onPostExecute(memberList);
        }
    }


}