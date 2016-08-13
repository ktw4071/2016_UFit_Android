package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Admin on 2016-07-27.
 */
public class UFitScheduleRCVFragm extends Fragment {


    static UFitScheduleRCVFragm newInstance() {
        UFitScheduleRCVFragm f = new UFitScheduleRCVFragm();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
// 판사님

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView rv = (RecyclerView) inflater.inflate
                (R.layout.ufit_fragment_recycler_view, container, false);
        rv.setLayoutManager(new LinearLayoutManager(UFitApplication.getUFitContext()));
        UFitScheduleRCVAdapter rcv = new UFitScheduleRCVAdapter();
        rv.setAdapter(rcv);
        DummyDatePoooool data1 = new DummyDatePoooool();
        DummyDatePoooool data2 = new DummyDatePoooool();
        DummyDatePoooool data3 = new DummyDatePoooool();
        data1.setData(R.drawable.ic_launcher, "김비만", "09:00");
        rcv.add(data1);
        data2.setData(R.drawable.alf, "황병만", "13:00");
        rcv.add(data2);
        data3.setData(R.drawable.angry_bird_red, "정동만", "17:00");
        rcv.add(data3);
        return rv;

    }

    public static class UFitSchedulePagerAdapter extends FragmentPagerAdapter {
        public UFitSchedulePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            return UFitScheduleRCVFragm.newInstance();
        }

        @Override
        public int getCount() {
            return 50000;
        }
    }

    public class UFitScheduleRCVAdapter extends RecyclerView.Adapter<UFitScheduleRCVAdapter.ViewHolder> {
        ArrayList<DummyDatePoooool> items = new ArrayList<DummyDatePoooool>();
//        UFitMainActivity activity = (UFitMainActivity)getActivity();

        public void add(DummyDatePoooool t) {
            items.add(t);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_schedule_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final DummyDatePoooool t = items.get(position);
//            holder.memberImg.setImageResource(t.img);
            holder.memberName.setText(t.getName());
            holder.workTime.setText(t.getTime());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), UFitUserProfile.class);
                    intent.putExtra("name", t.getName());
                    intent.putExtra("img", t.getImg());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final CircleImageView memberImg;
            public final TextView memberName;
            public final TextView workTime;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                memberImg = (CircleImageView) view.findViewById(R.id.uf_schedule_rcv_img);
                memberName = (TextView) view.findViewById(R.id.uf_schedule_rcv_name);
                workTime = (TextView) view.findViewById(R.id.uf_schedule_rcv_time);
            }
        }
    }
}

