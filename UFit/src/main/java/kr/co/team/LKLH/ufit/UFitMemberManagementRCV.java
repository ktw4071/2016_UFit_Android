package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-07-26.
 */
public class UFitMemberManagementRCV extends Fragment {

    RecyclerView rv;

    public static UFitMemberManagementRCV newInstance(){
        UFitMemberManagementRCV f = new UFitMemberManagementRCV();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        rv.setLayoutManager(new LinearLayoutManager(UFitApplication.getUFitContext()));
        (new AsyncMemberManagement()).execute();

        return rv;
    }

    public static class UFitMemberManagementRCVAdapter
            extends RecyclerView.Adapter<UFitMemberManagementRCVAdapter.ViewHolder> {
        ArrayList<UFitEntityObject> items = new ArrayList<>();

        public UFitMemberManagementRCVAdapter(ArrayList<UFitEntityObject> arrayList) {
            items = arrayList;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final CircleImageView memberImg;
            public final TextView memName;
            public final TextView memberBirth;
            public final TextView memberCall;
            public final TextView memClassDate;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                memberImg = (CircleImageView)view.findViewById(R.id.uf_mem_mng_img);
                memName = (TextView) view.findViewById(R.id.uf_mem_mng_name);
                memberBirth = (TextView)view.findViewById(R.id.uf_mem_mng_birth);
                memberCall = (TextView)view.findViewById(R.id.uf_mem_mng_call);
                memClassDate = (TextView)view.findViewById(R.id.uf_mem_mng_date);
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.ufit_mem_manage_carditem, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (items.get(position)._thumbnail != null) {
                Glide.with(UFitApplication.getUFitContext()).load(items.get(position)._thumbnail).into(holder.memberImg);
            } else {
                holder.memberImg.setImageResource(R.drawable.avatar_m);
            }
            holder.memName.setText(items.get(position)._name);
            holder.memberBirth.setText(items.get(position)._birth);
            holder.memberCall.setText(items.get(position)._number);
            holder.memClassDate.setText(items.get(position)._dayOfTheWeek);
            /*holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(UFitApplication.getUFitContext(), UFitUserProfile.class);
                    intent.putExtra("_mid", items.get(position)._mid);

                }
            });*/
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    private class AsyncMemberManagement extends AsyncTask<Void, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(Void... voids) {
            return (new LosDatosDeLaRed_JSON())
                    .LosDatosDeLaRed_GET_JSON(UFitNetworkConstantDefinition.URL_UFIT_TRAINER_MEMBER_LIST,"data", 1);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
            rv.setAdapter(new UFitMemberManagementRCVAdapter(arrayList));
        }
    }
}
