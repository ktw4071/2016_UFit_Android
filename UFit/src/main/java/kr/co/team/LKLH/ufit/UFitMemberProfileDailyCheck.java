package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

    CircleImageView cardio, rope, chest, back, lowbody, shoulder, arm, abs, stretch;
    ImageView send;
    ArrayList<Integer> data = new ArrayList<>();
    JSONObject jsonObject;
    JSONArray jsonArray;

    static UFitMemberProfileDailyCheck newInstance() {
        UFitMemberProfileDailyCheck f = new UFitMemberProfileDailyCheck();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    View.OnClickListener getListener(final int num, final CircleImageView image) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.contains(new Integer(num))) {
                    data.add(num);
                    switch (num) {
                        case 1   : image.setImageResource(R.drawable.iiii);
                        case 2   : image.setImageResource(R.drawable.iiii);
                        case 4   : image.setImageResource(R.drawable.iiii);
                        case 8   : image.setImageResource(R.drawable.iiii);
                        case 16  : image.setImageResource(R.drawable.iiii);
                        case 32  : image.setImageResource(R.drawable.iiii);
                        case 64  : image.setImageResource(R.drawable.iiii);
                        case 128 : image.setImageResource(R.drawable.iiii);
                        case 256 : image.setImageResource(R.drawable.iiii);
                    }
                } else {
                    data.remove(new Integer(num));
                    switch (num) {
                        case 1   : image.setImageResource(R.drawable.pic);
                        case 2   : image.setImageResource(R.drawable.pic);
                        case 4   : image.setImageResource(R.drawable.pic);
                        case 8   : image.setImageResource(R.drawable.pic);
                        case 16  : image.setImageResource(R.drawable.pic);
                        case 32  : image.setImageResource(R.drawable.pic);
                        case 64  : image.setImageResource(R.drawable.pic);
                        case 128 : image.setImageResource(R.drawable.pic);
                        case 256 : image.setImageResource(R.drawable.pic);
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
        jsonObject = new JSONObject();
        jsonArray  = new JSONArray();
        arm.setOnClickListener(getListener(1, arm));
        cardio.setOnClickListener(getListener(2, cardio));
        rope.setOnClickListener(getListener(4, rope));
        lowbody.setOnClickListener(getListener(8, lowbody));
        back.setOnClickListener(getListener(16, back));
        stretch.setOnClickListener(getListener(32, stretch));
        shoulder.setOnClickListener(getListener(64, shoulder));
        abs.setOnClickListener(getListener(128, abs));
        chest.setOnClickListener(getListener(256, chest));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0 ; i < data.size(); i++) {
                    try {
                        jsonArray.put(i, data.get(i));
                    } catch (Exception e) {
                    }
                } try {
                    jsonObject.put("_part", jsonArray);
                } catch (Exception e) {
                }
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
}
