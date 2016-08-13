package kr.co.team.LKLH.ufit;

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
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 2016-08-01.
 */
public class MemberCalendarFragment extends Fragment {
    private String title;
    private int page;

    /////프래그먼트가 리싸이클러뷰를 감싸는 코드 변수
    private RecyclerView mRecycler;
    private GridLayoutManager mGM;
    private int columnCount = 7;
    private MemberDaysCellAdapter mAdapter;
    private View OldView;


    public static RecyclerView workoutPartRecyclerView;
    ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObject;

    public static MemberCalendarFragment newInstance(int year, int  month, int maximumDay, int startDay) {
        MemberCalendarFragment memberCalendarFragment = new MemberCalendarFragment();
        Bundle args = new Bundle();
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisMaximumDay", maximumDay);
        args.putInt("ThisStartDay", startDay);
        memberCalendarFragment.setArguments(args);
        return memberCalendarFragment;
    }

    public static MemberCalendarFragment newInstance(int year, int  month, int maximumDay, int startDay, int today) {
        MemberCalendarFragment memberCalendarFragment = new MemberCalendarFragment();
        Bundle args = new Bundle();
        args.putInt("today", today);
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisMaximumDay", maximumDay);
        args.putInt("ThisStartDay", startDay);
        memberCalendarFragment.setArguments(args);
        return memberCalendarFragment;
    }

    int maximumDay;
    int year;
    int month;
    int startDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        maximumDay = super.getArguments().getInt("ThisMaximumDay");
        year = super.getArguments().getInt("ThisYear");
        month = super.getArguments().getInt("ThisMonth");
        startDay = super.getArguments().getInt("ThisStartDay");
        Log.i("leelog date max : ", maximumDay + "");
        Log.i("leelog date yr: ", year + "");
        Log.i("leelog date mt: ", month+ "");
        Log.i("leelog date sd: ", startDay+ "");
        String _month = "" + (month + 1);
        if((month + 1)< 10){
            _month = "0" + (month + 1);
        }


        try {
            Log.i("leelog date121211: ", "1" + year + _month + "01" + year + _month + maximumDay);

            uFitCalendarCellEntityObject = new MemberMonthlySchedule().execute("1","" + year + _month + "01", "" + year + _month + maximumDay).get();
            Log.e("어씽크 리턴",  "" + uFitCalendarCellEntityObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mRecycler = (RecyclerView)view.findViewById(R.id.recycler);
        workoutPartRecyclerView = (RecyclerView)getActivity().findViewById(R.id.workout_part_recyclerview);




        Log.e("Cal_Frag_OnCreate", "" + uFitCalendarCellEntityObject);
        mGM = new GridLayoutManager(super.getContext(), columnCount, 1, false);
        mRecycler.setLayoutManager(new GridLayoutManager(super.getContext(), columnCount, 1, false));
        mRecycler.addItemDecoration(new DividerItemDecoration(super.getContext(), 1));




        final GestureDetector mGestureDetector = new GestureDetector(super.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = mRecycler.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e) && rv.getChildAdapterPosition(child) > startDay - 2) {
                    Log.i("클릭한 날짜", "" + (rv.getChildAdapterPosition(child) - startDay + 2));

                    if(OldView != null){

                        OldView.findViewById(R.id.todayCircle).setBackgroundResource(0);
                    }

                    //큰 오늘 선택 써클 표시
                    child.findViewById(R.id.todayCircle).setBackgroundResource(R.drawable.today_circle);

                    TextView schedule_date = (TextView)getActivity().findViewById(R.id.today);

                    schedule_date.setText(year + ". " + String.format("%02d", (month + 1)) + ". " + String.format("%02d", (rv.getChildAdapterPosition(child) - startDay + 2)));


                    UFitCalendarCellEntityObject object = null;
                    UFitUserWorkoutPartAdapter_RecyclerView workoutPartRecyclerViewAdapter = null;
                    RecyclerView workoutPartRecyclerView = (RecyclerView)getActivity().findViewById(R.id.workout_part_recyclerview);

                    for (UFitCalendarCellEntityObject buf : mAdapter.uFitCalendarCellEntityObject) {
                        if (buf._date == (rv.getChildAdapterPosition(child) - startDay + 2) && buf._part != null){
                            object = buf;
                            workoutPartRecyclerViewAdapter = new UFitUserWorkoutPartAdapter_RecyclerView(object._part);
                            workoutPartRecyclerView.setAdapter(workoutPartRecyclerViewAdapter);
                            break;
                        }
                        else{
                            workoutPartRecyclerView.removeAllViewsInLayout();
                        }
                    }
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
//                    new SelectedDayMemberList().execute(_tid, _date);
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

    class MemberMonthlySchedule extends AsyncTask<String, Integer, ArrayList<UFitCalendarCellEntityObject>>{
        @Override
        protected ArrayList<UFitCalendarCellEntityObject> doInBackground(String... strings) {
            uFitCalendarCellEntityObject = UFitHttpConnectionHandler.memberMonthlySchedule(strings[0], strings[1], strings[2]);
            return uFitCalendarCellEntityObject;
        }

        @Override
        protected void onPostExecute(ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObjects) {
            super.onPostExecute(uFitCalendarCellEntityObjects);
            if (uFitCalendarCellEntityObjects == null)
                Log.i("leelog", "null Object@@@@");
            mAdapter = new MemberDaysCellAdapter(getContext(), maximumDay, startDay, uFitCalendarCellEntityObjects);
            mRecycler.setAdapter(mAdapter);
            //super.onPostExecute(uFitCalendarCellEntityObjects);
        }
    }
}