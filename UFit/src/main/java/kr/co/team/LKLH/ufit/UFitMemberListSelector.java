package kr.co.team.LKLH.ufit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-08-04.
 */
public class UFitMemberListSelector extends Fragment {

    Bundle bundle;
    UFitMemberManagementRegister owner;
    RecyclerView rv;

    static UFitMemberListSelector newInstance() {
        UFitMemberListSelector f = new UFitMemberListSelector();
        return f;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        owner = (UFitMemberManagementRegister)getActivity();
        LinearLayoutManager llm = new LinearLayoutManager(UFitApplication.getUFitContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        (new AsyncMemberListSelector()).execute();

        return rv;
    }

    public class MemberSelectorAdapter extends RecyclerView.Adapter<MemberSelectorAdapter.ViewHolder> {
        ArrayList<UFitEntityObject> items = new ArrayList<>();

        public MemberSelectorAdapter(ArrayList<UFitEntityObject> items) {
            this.items = items;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View rView;
            public final CircleImageView profilImg;
            public final TextView profilName;

            public ViewHolder(View itemView) {
                super(itemView);
                rView = itemView;
                profilImg = (CircleImageView)rView.findViewById(R.id.memselect_img);
                profilName= (TextView)rView.findViewById(R.id.memselect_name);

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_mem_select_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MemberSelectorAdapter.ViewHolder holder, final int position) {
            if (items.get(position)._thumbnail != null) {
                Glide.with(UFitApplication.getUFitContext()).load(items.get(position)._thumbnail).into(holder.profilImg);
            } else {
                holder.profilImg.setImageResource(R.drawable.avatar_m);
            }
            holder.profilName.setText(items.get(position)._name);
            holder.rView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    owner.setMember(items.get(position)._thumbnail, items.get(position)._name);
                    bundle.putInt("_mid", items.get(position)._mid);
                    bundle.putString("_thumbnail", items.get(position)._thumbnail);
                    bundle.putString("_name", items.get(position)._name);
                    Log.e("TATAGGG", bundle.toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    private class AsyncMemberListSelector extends AsyncTask<Void, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(Void... voids) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_GET_JSON
                    (UFitNetworkConstantDefinition.URL_UFIT_TRAINER_MEMBER_LIST, "data", 0);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
            rv.setAdapter(new MemberSelectorAdapter(arrayList));
        }
    }
}
