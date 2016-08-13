package kr.co.team.LKLH.ufit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ccei on 2016-08-08.
 */
public class UFitUserWorkoutPartAdapter_RecyclerView extends RecyclerView.Adapter<UFitUserWorkoutPartAdapter_RecyclerView.ViewHolder>{

    private int[] part;
    private int counter;

    public UFitUserWorkoutPartAdapter_RecyclerView(int[] part){
        this.part = new int[part.length];
        System.arraycopy(part, 0, this.part, 0, part.length);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_user_workout_part_recyclerview_item, parent, false);
        ViewHolder holder = new ViewHolder(itemRoot);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.workout_part_image.setImageResource(R.drawable.pic);
        holder.workout_part_text.setText(String.valueOf(part[counter++]));
    }

    @Override
    public int getItemCount() {
        return part.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView workout_part_image;
        public TextView workout_part_text;

        public ViewHolder(View itemView) {
            super(itemView);
            workout_part_image = (ImageView)itemView.findViewById(R.id.workout_part_image);
            workout_part_text = (TextView)itemView.findViewById(R.id.workout_part_text);
        }
    }
}
