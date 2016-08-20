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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 2016-07-29.
 */
public class UFitTrainerHistoryRCV extends Fragment {

    RecyclerView rv;

    UFitTrainerHistoryRCVAdapter history = new UFitTrainerHistoryRCVAdapter();

    static UFitTrainerHistoryRCV newInstance() {
        UFitTrainerHistoryRCV f = new UFitTrainerHistoryRCV();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        rv.setLayoutManager(new LinearLayoutManager(UFitApplication.getUFitContext()));
        (new AsyncTrainerProfile()).execute();
        rv.setAdapter(history);

        return rv;
    }
    ArrayList<JSONObject> items = new ArrayList<>();
    public class UFitTrainerHistoryRCVAdapter extends RecyclerView.Adapter<UFitTrainerHistoryRCVAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rView;
            public TextView thYear, thTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                rView = itemView;
                thYear = (TextView)rView.findViewById(R.id.uf_trhistory_year);
                thTitle= (TextView)rView.findViewById(R.id.uf_trhistory_title);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.ufit_trainer_history_item, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                holder.thYear.setText(items.get(position).getString("_from").substring(0, 4)+ "~"+
                                      items.get(position).getString("_to").substring(0, 4));
                holder.thTitle.setText(items.get(position).getString("_content"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    private class AsyncTrainerProfile extends AsyncTask<Void, Void, ArrayList<JSONObject>> {
        @Override
        protected ArrayList<JSONObject> doInBackground(Void... voids) {
            JSONArray historyObj;
            ArrayList<JSONObject> items = new ArrayList<>();
            LosDatosDeLaRed_JSON LGJ = new LosDatosDeLaRed_JSON();
            LGJ.LosDatosDeLaRed_GET_JSON(UFitNetworkConstantDefinition.URL_UFIT_TRAINER_PROFILE, "data", 2);
            for (int i = 0 ; i < LGJ.jsonArray.length() ; i++) {
                try {
                    historyObj = LGJ.jsonArray.getJSONObject(i).getJSONArray("_history");
                    for (int j = 0 ; j < historyObj.length() ; j++) {
                        items.add(historyObj.getJSONObject(j));
                    }
                } catch (Exception e) {
                    Log.e("트레이너 프로필 리사이클러뷰 오류", e.toString());
                    return null;
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONObject> arrayList) {
            super.onPostExecute(arrayList);
            for (JSONObject obj : arrayList) {
                items.add(obj);
            }
            history.notifyDataSetChanged();
        }
    }
}
