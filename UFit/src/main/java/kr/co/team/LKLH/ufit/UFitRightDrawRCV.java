package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.Manifest;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-07-27.
 */
public class UFitRightDrawRCV extends Fragment {

    RecyclerView rv;
    UFitMainActivity owner;

    public static UFitRightDrawRCV newInstance() {
        UFitRightDrawRCV f = new UFitRightDrawRCV();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        owner = (UFitMainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rv = (RecyclerView)inflater.inflate(R.layout.ufit_fragment_recycler_view, container, false);
        rv.setLayoutManager(new LinearLayoutManager(UFitApplication.getUFitContext()));
        (new AsyncRightDraw()).execute();
        return rv;
    }

    public class UFitRightdrawRCVAdapter extends RecyclerView.Adapter<UFitRightdrawRCVAdapter.ViewHolder>{
        ArrayList<UFitEntityObject> items = new ArrayList<>();

        public UFitRightdrawRCVAdapter(ArrayList<UFitEntityObject> arrayList) {
            items = arrayList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final CircleImageView memImg;
            public final TextView memName;
            public final ImageView moreMenu, userHome, userMessage, userCall;
            public final LinearLayout popupLayout;

           public ViewHolder(View itemView) {
               super(itemView);
               mView = itemView;
               memImg = (CircleImageView) mView.findViewById(R.id.uf_right_mem_img);
               memName= (TextView)mView.findViewById(R.id.uf_right_mem_name);
               moreMenu=(ImageView) mView.findViewById(R.id.uf_right_mem_more);
               popupLayout=(LinearLayout)mView.findViewById(R.id.uf_rd_popup);
               userCall = (ImageView)mView.findViewById(R.id.rd_user_call);
               userHome = (ImageView)mView.findViewById(R.id.rd_user_home);
               userMessage = (ImageView)mView.findViewById(R.id.rd_user_message);
           }
       }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.ufit_rightdraw_mem_item, parent, false);
            return new ViewHolder(view);
        }

        View mView = null;
        int lastClickPosition;

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            Glide.with(UFitApplication.getUFitContext()).load(items.get(position)._thumbnail).into(holder.memImg);
            holder.memName.setText(items.get(position)._name);
            holder.moreMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastClickPosition = position;
                    Log.i("Question", lastClickPosition + "");
                    if (mView != null) {
                        if (mView == holder.popupLayout) {
                            mView.setVisibility(View.INVISIBLE);
                            mView = null;
                        } else {
                            mView.setVisibility(View.INVISIBLE);
                            holder.popupLayout.setVisibility(View.VISIBLE);
                            mView = holder.popupLayout;
                        }
                    } else {
                        mView = holder.popupLayout;
                        holder.popupLayout.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder.userCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PermissionListener permissionListener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            if (items.get(position)._number == null) {
                                Toast.makeText(getActivity(), "전화번호가 등록되어있지 않습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+items.get(position)._number)));
                            }
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> arrayList) {
                            Toast.makeText(getActivity(), "접근이 거부되었습니다", Toast.LENGTH_SHORT).show();
                        }
                    };
                    new TedPermission(getActivity())
                            .setPermissionListener(permissionListener)
                            .setDeniedMessage("거부되었습니다")
                            .setPermissions(android.Manifest.permission.CALL_PHONE)
                            .check();
                }
            });
            holder.userHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(getActivity(), UFitUserProfile.class)
                                            .putExtra("_mid", items.get(position)._mid));
                    holder.popupLayout.setVisibility(View.INVISIBLE);
                    owner.drawlayout.closeDrawers();
                }
            });
        }
        @Override
        public int getItemCount() {
            return items.size();
        }
    }
    private class AsyncRightDraw extends AsyncTask<Void, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(Void... voids) {
            return (new LosDatosDeLaRed_JSON())
                    .LosDatosDeLaRed_GET_JSON(UFitNetworkConstantDefinition.URL_UFIT_TRAINER_MEMBER_LIST,"data", 1);
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            UFitRightdrawRCVAdapter adapter = new UFitRightdrawRCVAdapter(arrayList);
            rv.setAdapter(adapter);
        }
    }
}
