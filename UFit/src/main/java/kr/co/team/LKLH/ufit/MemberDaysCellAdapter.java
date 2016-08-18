package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-08.
 */
public class MemberDaysCellAdapter extends RecyclerView.Adapter<MemberDaysCellAdapter.ViewHolder>{
    int maximumDay;
    int day_counter;
    int startDay;
    ArrayList<UFitCalendarCellEntityObject> uFitCalendarCellEntityObject;
    int scheduleSize;

    Integer[] monthlySchedule = new Integer[31];
    //    int[] array = new int[31];
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
//            if(monthlySchedule != null && Arrays.asList(monthlySchedule).contains(position - startDay + 2)){
//                holder.attend.setImageResource(R.drawable.schedule_circle);
//            }
            if(position == 10){
                Log.e("눌렀씁니다", " 23232" );
                holder.itemView.performClick();
            }
            for(int i = 0; i < scheduleSize; i++){
                if (uFitCalendarCellEntityObject.get(i)._date == (position - startDay + 2)){
                    if (uFitCalendarCellEntityObject.get(i)._attendance == 1) {
//                        holder.attendance = 1;
                        holder.attend.setImageResource(R.drawable.schedule_circle_attend);
                    } else {
//                        holder.attendance = 0;
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
//        public Integer attendance = null;
        public TextView day;
        public ImageView attend;
        public int[] part;
        public ViewHolder(View itemView) {
            super(itemView);
            day = (TextView)itemView.findViewById(R.id.day);
            attend = (ImageView)itemView.findViewById(R.id.attend);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("눌렀씁니다3333", " 23232" );
                }
            });
        }
    }


}