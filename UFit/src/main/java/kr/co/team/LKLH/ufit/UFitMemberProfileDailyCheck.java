package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-08-02.
 */
public class UFitMemberProfileDailyCheck extends DialogFragment {

    CircleImageView cardio, rope, chest, back, lowbody, shoulder, arm, abs, stretch, attendance;
    ImageView send;
    ArrayList<Integer> data = new ArrayList<>();
    JSONObject jsonObject;

    int _attendance = 0;

    static UFitMemberProfileDailyCheck newInstance(String _date, int _mid) {
        UFitMemberProfileDailyCheck f = new UFitMemberProfileDailyCheck();
        Bundle b = new Bundle();
        b.putString("_date", _date);
        b.putInt("_mid", _mid);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int workpart = 0;
    }

    View.OnClickListener getListener(final int num, final CircleImageView image) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.contains(new Integer(num))) {
                    data.add(num);
                    switch (num) {
                        case 1   : image.setImageResource(R.drawable.btn_chest_on_15); break;
                        case 2   : image.setImageResource(R.drawable.btn_back_on_15); break;
                        case 4   : image.setImageResource(R.drawable.btn_shoulder_on_15); break;
                        case 8   : image.setImageResource(R.drawable.btn_arm_on_15); break;
                        case 16  : image.setImageResource(R.drawable.btn_leg_on_15); break;
                        case 32  : image.setImageResource(R.drawable.btn_abs_on_15); break;
                        case 64  : image.setImageResource(R.drawable.btn_run_on_15); break;
                        case 128 : image.setImageResource(R.drawable.btn_stretch_on_15); break;
                        case 256 : image.setImageResource(R.drawable.btn_rope_on_15); break;
                    }
                } else {
                        data.remove(new Integer(num));
                        switch (num) {
                            case 1   : image.setImageResource(R.drawable.btn_chest_off_15); break;
                            case 2   : image.setImageResource(R.drawable.btn_back_off_15); break;
                            case 4   : image.setImageResource(R.drawable.btn_shoulder_off_15); break;
                            case 8   : image.setImageResource(R.drawable.btn_arm_off_15); break;
                            case 16  : image.setImageResource(R.drawable.btn_leg_off_15); break;
                            case 32  : image.setImageResource(R.drawable.btn_abs_off_15); break;
                            case 64  : image.setImageResource(R.drawable.btn_run_off_15); break;
                            case 128 : image.setImageResource(R.drawable.btn_stretch_off_15); break;
                            case 256 : image.setImageResource(R.drawable.btn_rope_off_15); break;

                        }
                }
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.ufit_member_dailycheck, container, false);
        arm     = (CircleImageView) view.findViewById(R.id.dc1);
        cardio  = (CircleImageView) view.findViewById(R.id.dc2);
        rope    = (CircleImageView) view.findViewById(R.id.dc3);
        lowbody = (CircleImageView) view.findViewById(R.id.dc4);
        back    = (CircleImageView) view.findViewById(R.id.dc5);
        stretch = (CircleImageView) view.findViewById(R.id.dc6);
        shoulder= (CircleImageView) view.findViewById(R.id.dc7);
        abs     = (CircleImageView) view.findViewById(R.id.dc8);
        chest   = (CircleImageView) view.findViewById(R.id.dc9);
        send    = (ImageView)view.findViewById(R.id.workparts_send);
        attendance=(CircleImageView)view.findViewById(R.id.workparts_attendance);
        jsonObject = new JSONObject();
        arm.setOnClickListener(getListener(1, arm));
        cardio.setOnClickListener(getListener(2, cardio));
        rope.setOnClickListener(getListener(4, rope));
        lowbody.setOnClickListener(getListener(8, lowbody));
        back.setOnClickListener(getListener(16, back));
        stretch.setOnClickListener(getListener(32, stretch));
        shoulder.setOnClickListener(getListener(64, shoulder));
        abs.setOnClickListener(getListener(128, abs));
        chest.setOnClickListener(getListener(256, chest));
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_attendance == 0) {
                    attendance.setImageResource(R.drawable.btn_culsuck_14);
                    _attendance = 1;
                } else {
                    attendance.setImageResource(R.drawable.btn_miculsuck_14);
                    _attendance = 0;
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONArray jsonArray = new JSONArray();
                for (int i = 0 ; i < data.size(); i++){
                    try {
                        jsonArray.put(i, data.get(i).intValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    jsonObject.put("_part", jsonArray);
                    jsonObject.put("_attendance", _attendance);
                    jsonObject.put("_date", getArguments().getString("_date"));
                    Log.e("목소리", getArguments().getString("_date"));
                    jsonObject.put("_mid", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new AsynDailyCheckMember().execute(jsonObject);
                dismiss();
            }
        });

        return view;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    private class AsynDailyCheckMember extends AsyncTask<JSONObject, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(JSONObject... jsonObjects) {
            return new LosDatosDeLaRed_JSON().LosDatosDeLaRed_POST_JSON
                    (jsonObjects, UFitNetworkConstantDefinition.URL_UFit_Member_Monthly_Schedule, 7, 0);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
        }
    }

}
