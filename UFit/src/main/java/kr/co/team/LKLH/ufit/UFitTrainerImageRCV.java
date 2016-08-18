package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Admin on 2016-08-09.
 */
public class UFitTrainerImageRCV extends Fragment {

    RecyclerView rv;
    UFitTrainerImageRCVAdapter adapter;
    ArrayList<JSONObject> items;

    static UFitTrainerImageRCV newInstance() {
        UFitTrainerImageRCV f = new UFitTrainerImageRCV();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList<JSONObject>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        rv.setLayoutManager(new GridLayoutManager(UFitApplication.getUFitContext(), 3, 1, false));
        (new AsyncTrainerImage()).execute();

        return rv;
    }
    public class UFitTrainerImageRCVAdapter extends RecyclerView.Adapter<UFitTrainerImageRCVAdapter.ViewHolder> {

        public UFitTrainerImageRCVAdapter(ArrayList<JSONObject> jsonitems) {
            items = jsonitems;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View rView;

            public ViewHolder(View itemView) {
                super(itemView);
                rView = itemView;
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ufit_trainer_image_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            try {
                Glide.with(UFitApplication.getUFitContext()).load(items.get(position).getString("_thumbnail")).into((ImageView) holder.rView);
            } catch (Exception e) {
                Log.e("tata", e.toString());
            }
            holder.rView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent(getActivity(), new_UFitImageViewer.class);
                        intent.putExtra("data", items.get(position).toString());
                        intent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_TRAINER_ALBUM_UPLOAD);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    private class AsyncTrainerImage extends AsyncTask<Void, Void, ArrayList<JSONObject>> {
        @Override
        protected ArrayList<JSONObject> doInBackground(Void... voids) {
            JSONArray albumArray;
            ArrayList<JSONObject> items = new ArrayList<>();
            LosDatosDeLaRed_JSON LGJ = new LosDatosDeLaRed_JSON();
            LGJ.LosDatosDeLaRed_GET_JSON(UFitNetworkConstantDefinition.URL_UFIT_TRAINER_PROFILE, "data", 2);
            for (int i = 0 ; i < LGJ.jsonArray.length() ; i++) {
                try {
                    albumArray = LGJ.jsonArray.getJSONObject(i).getJSONArray("_album");
                    for (int j = 0 ; j < albumArray.length() ; j++) {
                        items.add(albumArray.getJSONObject(j));
                    }
                } catch (Exception e) {
                    Log.e("트레이너 프로필 사진 리사이클러뷰 오류", e.toString());
                    return null;
                }
            }
            return items;
        }

        @Override
        protected void onPostExecute(ArrayList<JSONObject> jsonObjects) {
            super.onPostExecute(jsonObjects);
            adapter = new UFitTrainerImageRCVAdapter(jsonObjects);
            rv.setAdapter(adapter);
        }
    }

}
