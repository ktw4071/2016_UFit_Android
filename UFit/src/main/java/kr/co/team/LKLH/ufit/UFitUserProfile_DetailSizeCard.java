package kr.co.team.LKLH.ufit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Encho on 2016-08-01.
 */
public class UFitUserProfile_DetailSizeCard extends Fragment {
    static UFitUserProfile_DetailSizeCard newInstance(){
        UFitUserProfile_DetailSizeCard f = new UFitUserProfile_DetailSizeCard();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        UFitUserProfile_DetailSizeCardAdapter rcv = new UFitUserProfile_DetailSizeCardAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(UFitApplication.getUFitContext());
        /*{
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };*/
        rv.setLayoutManager(llm);
        rv.setAdapter(rcv);
        DummyDatePoooool data1 = new DummyDatePoooool();
        DummyDatePoooool data2 = new DummyDatePoooool();
        DummyDatePoooool data3 = new DummyDatePoooool();
        data1.setData("95", "40", "30", "25", "70");
        data2.setData("95", "40", "30", "25", "70");
        data3.setData("105", "60", "50", "35", "90");
        rcv.add(data1);
        rcv.add(data2);
        rcv.add(data3);

        return rv;
    }
    public static class UFitUserProfile_DetailSizeCardAdapter extends RecyclerView.Adapter<UFitUserProfile_DetailSizeCardAdapter.ViewHolder>{
        ArrayList<DummyDatePoooool> items = new ArrayList<DummyDatePoooool>();

        public void add(DummyDatePoooool t){
            items.add(t);
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View rView;
            public TextView chest, armfit, thigh, calf, waist;

            public ViewHolder(View itemView) {
                super(itemView);
                rView = itemView;
                chest = (TextView)rView.findViewById(R.id.detail_size_chest);
                armfit= (TextView)rView.findViewById(R.id.detail_size_armfit);
                thigh = (TextView)rView.findViewById(R.id.detail_size_thigh);
                calf  = (TextView)rView.findViewById(R.id.detail_size_calf);
                waist = (TextView)rView.findViewById(R.id.detail_size_waist);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_user_profile_detail_cartitem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DummyDatePoooool t = items.get(position);
            holder.chest.setText(t.getChest());
            holder.thigh.setText(t.getThigh());
            holder.calf.setText(t.getCalf());
            holder.armfit.setText(t.getArmfit());
            holder.waist.setText(t.getWaist());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}
