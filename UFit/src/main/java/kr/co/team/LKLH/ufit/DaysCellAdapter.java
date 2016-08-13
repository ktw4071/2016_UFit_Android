package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Admin on 2016-08-01.
 */
public class DaysCellAdapter extends RecyclerView.Adapter<DaysCellAdapter.ViewHolder>{
    Calendar calendar = Calendar.getInstance(Locale.getDefault());
    int today = calendar.get(Calendar.DAY_OF_MONTH);
    int maximumDay;
    int day_counter;
    int startDay;

    Integer[] monthlySchedule = new Integer[31];
//    int[] array = new int[31];
    Context mContext;

    public DaysCellAdapter(Context mContext, int maximumDay, int startDay, int[] monthlySchedule){
        this.mContext = mContext;
        this.maximumDay = maximumDay;
        this.startDay = startDay;
//        Log.e("DayCellAdapter_monthly", "" + monthlySchedule[0]);
//        this.monthlySchedule = new monthlySchedule[monthlySchedule.length];
        if(monthlySchedule != null){
            for(int i=0; i < monthlySchedule.length; i++){
                this.monthlySchedule[i] = monthlySchedule[i];
            }
        }

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
            if(monthlySchedule != null && Arrays.asList(monthlySchedule).contains(position - startDay + 2)){
                holder.attend.setImageResource(R.drawable.schedule_circle);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView day;
        public ImageView attend;
        public int _cid;
        public int _attendance;
        public ViewHolder(View itemView) {
            super(itemView);
            day = (TextView)itemView.findViewById(R.id.day);
            attend = (ImageView)itemView.findViewById(R.id.attend);
        }
    }


}