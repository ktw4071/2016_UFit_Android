package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ccei on 2016-08-02.
 */
public class MemberItemAdapter extends RecyclerView.Adapter<MemberItemAdapter.ViewHolder> {
    private List<DummyDatePoooool> memberList;
    private ArrayList<UFitEntityObject> memberListObject;
    private UFitManageSchedule owner_manageschedule;
    private UFitMainActivity owner_mainactivity;

    public void dateRefresh() {
        notifyDataSetChanged();
    }

    public MemberItemAdapter(List<DummyDatePoooool> memberList) {
        this.memberList = memberList;
    }

    public MemberItemAdapter(ArrayList<UFitEntityObject> memberListObject) {
        this.memberListObject = memberListObject;

    }public MemberItemAdapter(Context context, ArrayList<UFitEntityObject> memberListObject, String activityname) {
        this.memberListObject = memberListObject;
        if(activityname == "UFitManageSchedule"){
            owner_manageschedule = (UFitManageSchedule)context;
        }
        else if(activityname == "UFitMainActivity"){
            owner_mainactivity = (UFitMainActivity)context;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRoot = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_manage_schedule_memberlist_item, parent, false);
        final ViewHolder holder = new ViewHolder(itemRoot);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(memberList != null){
            DummyDatePoooool member = memberList.get(position);
//            holder.profile_pic.setImageResource(member.getImg());
            holder.name.setText(member.getName());
            holder.time.setText(member.getTime());
        }
        else{
            final UFitEntityObject member = memberListObject.get(position);
            Glide.with(UFitApplication.getUFitContext()).load(member._thumbnail).into(holder.profile_pic);
            holder.name.setText(member._name);
            holder.time.setText(member._time);
            holder.jump_to_user_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UFitApplication.getUFitContext(), UFitUserProfile.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("_mid", member._mid);
                    intent.putExtra("_name", member._name);
                    intent.putExtra("_thumbnail", member._thumbnail);
                    UFitApplication.getUFitContext().startActivity(intent);
                }
            });

            holder.schedule_member_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{

//                        owner.onMethodCallBack();
                        if(owner_mainactivity != null){
                            owner_mainactivity.deleteMethodCallBack(memberListObject, member, mem);
                        }
                        else{
                            Log.i("leelog", "else");
                            owner_manageschedule.deleteMethodCallBack(memberListObject, member, mem);
                        }
                    }catch(ClassCastException exception){
                        Log.e("멤버삭제", "실패!");
                    }
                }
            });
        }
    }
    MemberItemAdapter mem = this;
    public interface DeleteMemberCallback{
        void deleteMethodCallBack(ArrayList<UFitEntityObject> list, UFitEntityObject object, MemberItemAdapter adapter);
    }


    @Override
    public int getItemCount() {
        if(memberList != null){
            return memberList.size();
        }
        else{
            if(memberListObject != null){
                return memberListObject.size();
            }
            else{
                return 0;
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View mView;
        public TextView name, time;
        public ImageView delete_button;
        public CircleImageView profile_pic;
        public LinearLayout jump_to_user_profile, schedule_member_delete;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            profile_pic = (CircleImageView)itemView.findViewById(R.id.manage_schedule_member_profile_pic);
            name = (TextView)itemView.findViewById(R.id.manage_schedule_member_name);
            time = (TextView)itemView.findViewById(R.id.manage_schedule_training_time);
            delete_button = (ImageView)itemView.findViewById(R.id.manage_schedule_delete_button);
            jump_to_user_profile = (LinearLayout)itemView.findViewById(R.id.jump_to_user_profile);
            schedule_member_delete = (LinearLayout)itemView.findViewById(R.id.schedule_member_delete);
        }
    }
}
