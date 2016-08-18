package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
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
    boolean firstRecyclerViewChecker;



    public RecyclerView workoutPartRecyclerView;
    ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObject;

    public static MemberCalendarFragment newInstance(int year, int  month, int maximumDay, int startDay, int _mid) {
        MemberCalendarFragment memberCalendarFragment = new MemberCalendarFragment();
        Bundle args = new Bundle();
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisMaximumDay", maximumDay);
        args.putInt("ThisStartDay", startDay);
        args.putInt("_mid", _mid);
        memberCalendarFragment.setArguments(args);
        return memberCalendarFragment;
    }

    int maximumDay;
    int year;
    int month;
    int startDay;
    int _mid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        maximumDay = super.getArguments().getInt("ThisMaximumDay");
        year = super.getArguments().getInt("ThisYear");
        month = super.getArguments().getInt("ThisMonth");
        startDay = super.getArguments().getInt("ThisStartDay");
        _mid = getArguments().getInt("_mid");
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

            uFitCalendarCellEntityObject = new MemberMonthlySchedule().execute(String.valueOf(_mid),"" + year + _month + "01", "" + year + _month + maximumDay).get();
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
                    Log.i("클릭한 나의멤버", "" + _mid);
                    if(OldView != null){

                        OldView.findViewById(R.id.todayCircle).setBackgroundResource(0);
                    }
                    child.findViewById(R.id.todayCircle).setBackgroundResource(R.drawable.today_circle);
                    TextView schedule_date = (TextView)getActivity().findViewById(R.id.today);

                    schedule_date.setText(year + ". " + String.format("%02d", (month + 1)) + ". " + String.format("%02d", (rv.getChildAdapterPosition(child) - startDay + 2)));

                    clickPerformed(rv, child);

/* 밖으로 메소드로 뺀 운동목록 리싸이클러뷰
                    UFitCalendarCellEntityObject object = null;
                    UFitUserWorkoutPartAdapter_RecyclerView workoutPartRecyclerViewAdapter = null;
                    workoutPartRecyclerView = (RecyclerView)getActivity().findViewById(R.id.workout_part_recyclerview);

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
                    */
                    OldView = child;

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

        return view;
    }

    public void clickPerformed(RecyclerView rv, View child){
        UFitCalendarCellEntityObject object = null;
        UFitUserWorkoutPartAdapter_RecyclerView workoutPartRecyclerViewAdapter = null;
        workoutPartRecyclerView = (RecyclerView)getActivity().findViewById(R.id.workout_part_recyclerview);

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
        }
    }

    public class MemberDaysCellAdapter extends RecyclerView.Adapter<MemberDaysCellAdapter.ViewHolder>{
        int maximumDay;
        int day_counter;
        int startDay;
        ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObject;
        int scheduleSize;
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int today = calendar.get(Calendar.DAY_OF_MONTH);

        Context mContext;

        public MemberDaysCellAdapter(Context mContext, int maximumDay, int startDay,  ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObject ){
            this.mContext = mContext;
            this.maximumDay = maximumDay;
            this.startDay = startDay;
            this.uFitCalendarCellEntityObject = uFitCalendarCellEntityObject;
            this.scheduleSize = uFitCalendarCellEntityObject.size();
            Log.i("유핏멤버캘린더오브젝트", "" + uFitCalendarCellEntityObject);
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_grid_cell, parent, false);
            ViewHolder holder = new ViewHolder(itemRoot);
            return holder;
        }

        @Override
        public int getItemCount() {
            return maximumDay + startDay - 1;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {//1일 position 1
            if(position >= (startDay - 1)) {
                holder.day.setText(String.valueOf(++day_counter));
                if(!firstRecyclerViewChecker && (position - startDay + 2) == today){
//                    int[] testarray = {2, 4, 8};
//                    UFitUserWorkoutPartAdapter_RecyclerView workoutPartRecyclerViewAdapter = new UFitUserWorkoutPartAdapter_RecyclerView(testarray);
//                    workoutPartRecyclerView.setAdapter(workoutPartRecyclerViewAdapter);
                    firstRecyclerViewChecker = !firstRecyclerViewChecker;
                    holder.itemView.findViewById(R.id.todayCircle).setBackgroundResource(R.drawable.today_circle);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int[] testarray = {2, 4, 8};
                            UFitUserWorkoutPartAdapter_RecyclerView workoutPartRecyclerViewAdapter = new UFitUserWorkoutPartAdapter_RecyclerView(testarray);
                            workoutPartRecyclerView.setAdapter(workoutPartRecyclerViewAdapter);
                        }
                    },0);
                }
                for(int i = 0; i < scheduleSize; i++){
                    if (uFitCalendarCellEntityObject.get(i)._date == (position - startDay + 2)){
                        if (uFitCalendarCellEntityObject.get(i)._attendance == 1) {
                            holder.attend.setImageResource(R.drawable.schedule_circle_attend);
                        } else {
                            holder.attend.setImageResource(R.drawable.schedule_circle);
                        }
                        holder.day.setTextColor(Color.WHITE);
                        if (uFitCalendarCellEntityObject.get(i)._part != null) {
                            holder.part = new int[uFitCalendarCellEntityObject.get(i)._part.length];
                            System.arraycopy(uFitCalendarCellEntityObject.get(i)._part, 0, holder.part, 0, uFitCalendarCellEntityObject.get(i)._part.length);
                        }
                    }
                }
            }
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView day;
            public ImageView attend;
            public int[] part;
            public ViewHolder(final View itemView) {
                super(itemView);
                day = (TextView)itemView.findViewById(R.id.day);
                attend = (ImageView)itemView.findViewById(R.id.attend);
            }
        }
    }
}