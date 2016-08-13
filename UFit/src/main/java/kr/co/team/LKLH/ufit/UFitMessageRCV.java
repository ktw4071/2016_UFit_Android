package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UFitMessageRCV extends Fragment {
    Context context;
    static UFitMessageRCV newInstance() {
        UFitMessageRCV f = new UFitMessageRCV();
        Bundle b = new Bundle();
        f.setArguments(b);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        UFitMessageRCVAdapter rcv = new UFitMessageRCVAdapter();
        context = getContext();
        rv.setLayoutManager(new LinearLayoutManager(UFitApplication.getUFitContext()));
        rv.setAdapter(rcv);
        DummyDatePoooool data1 = new DummyDatePoooool();
        DummyDatePoooool data2 = new DummyDatePoooool();
        DummyDatePoooool data3 = new DummyDatePoooool();

        data1.setData(R.drawable.angry_bird_red, "황비만", "2016.06.01 오후 02:00");
        rcv.add(data1);
        data2.setData(R.drawable.alf, "김허약", "2016.06.03 오후 06:00");
        rcv.add(data2);
        data3.setData(R.drawable.ic_launcher, "독고비만", "2016.06.08 오후 10:00");
        rcv.add(data3);

        return rv;
    }
    private class UFitMessageRCVAdapter extends RecyclerView.Adapter<UFitMessageRCVAdapter.ViewHolder>{
        ArrayList<DummyDatePoooool> items = new ArrayList<DummyDatePoooool>();

        public void add(DummyDatePoooool t){
            items.add(t);
            notifyDataSetChanged();
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rView;
            public CircleImageView messageImg;
            public TextView messageName, messageTime;
            public LinearLayout messageItem;


            public ViewHolder(View view){
                super(view);
                rView = view;
                messageImg = (CircleImageView)rView.findViewById(R.id.message_profileimg);
                messageName= (TextView)rView.findViewById(R.id.message_name);
                messageTime= (TextView)rView.findViewById(R.id.message_time);
                messageItem= (LinearLayout)rView.findViewById(R.id.uf_message_list);
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_message_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DummyDatePoooool t = items.get(position);
//            holder.messageImg.setImageResource(t.img);
            holder.messageName.setText(t.getName());
            holder.messageTime.setText(t.getTime());
            holder.messageItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, UFitMessageChatList.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}