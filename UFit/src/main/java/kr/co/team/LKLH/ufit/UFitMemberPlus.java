package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-08-04.
 */
public class UFitMemberPlus extends DialogFragment {
// 회원 추가 프래그먼트

    JSONObject memberData;
    EditText memName, memBirth, memNumber, memDate, memHeight, memWeight, memGoal, memMemo;
    CircleImageView memImage;

    static UFitMemberPlus newInstance() {
        UFitMemberPlus f = new UFitMemberPlus();
        return f;
        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ufit_mem_manage_reg, container, false);
        memImage  = (CircleImageView)view.findViewById(R.id.member_add_img);
        memName   = (EditText) view.findViewById(R.id.add_member_name);
        memBirth  = (EditText) view.findViewById(R.id.add_member_birth);
        memNumber = (EditText) view.findViewById(R.id.add_member_phone);
        memDate   = (EditText) view.findViewById(R.id.add_member_date);
        memHeight = (EditText) view.findViewById(R.id.add_member_height);
        memWeight = (EditText) view.findViewById(R.id.add_member_weight);
        memGoal   = (EditText) view.findViewById(R.id.add_member_weight_target);
        memMemo   = (EditText) view.findViewById(R.id.add_member_memo);

        memImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(new Intent(getActivity(), UFitImageUploadHelper.class));
                intent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_MEMBER_IMAGE);
                intent.putExtra("code", 0);
                intent.putExtra("from", String.valueOf(getActivity()));
                startActivity(intent);
            }
        });

        view.findViewById(R.id.add_member_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberData = new JSONObject();
                try {
                    memberData.put("_name", memName.getText());
                    memberData.put("_birth", Integer.parseInt(memBirth.getText().toString()));
                    memberData.put("_number", memNumber.getText());
                    memberData.put("_dayOfTheWeek", memDate.getText());
                    memberData.put("_height", Integer.parseInt(memHeight.getText().toString()));
                    memberData.put("_initial", Integer.parseInt(memWeight.getText().toString()));
                    memberData.put("_goal", Integer.parseInt(memGoal.getText().toString()));
                    memberData.put("_memo", memMemo.getText());
                    Log.e("QR", memberData.toString());
                    new AsyncMemberAdd().execute(memberData);
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private class AsyncMemberAdd extends AsyncTask<JSONObject, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(JSONObject... jsonObjects) {
            Log.e("하하", jsonObjects.length+"");
            return new LosDatosDeLaRed_JSON().LosDatosDeLaRed_POST_JSON
                    (jsonObjects, UFitNetworkConstantDefinition.URL_Ufit_MEMBER_ADD, 5, 1);
        }
    }
}
