package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memberData = new JSONObject();
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
                intent.putExtra("code", 1);
                intent.putExtra("_mid", 0);
                startActivityForResult(intent, 0);
            }
        });

        view.findViewById(R.id.add_member_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                memberData = new JSONObject();
                try {
                    memberData.put("_name", memName.getText());
                    memberData.put("_birth", memBirth.getText().toString());
                    memberData.put("_number", memNumber.getText());
                    memberData.put("_dayOfTheWeek", memDate.getText());
                    memberData.put("_height", memHeight.getText().toString());
                    memberData.put("_initial", memWeight.getText().toString());
                    memberData.put("_goal", memGoal.getText().toString());
                    memberData.put("_memo", memMemo.getText());
                    Log.e("QR", memberData.toString());
                    new AsyncMemberAdd().execute(memberData);

                    dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    Uri uri = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        uri = (Uri)data.getExtras().get("image");
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == getActivity().RESULT_OK) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), (Uri) data.getExtras().get("image"));
                        memImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FrameLayout root = new FrameLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        return dialog;
    }

    private class AsyncMemberAdd extends AsyncTask<JSONObject, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(JSONObject... jsonObjects) {
            ArrayList<UFitEntityObject> items = new ArrayList<>();
            OkHttpClient client = new OkHttpClient();
            final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObjects[0].toString());
            Request request = new Request.Builder()
                                        .url(UFitNetworkConstantDefinition.URL_Ufit_MEMBER_ADD)
                                        .post(body)
                                        .build();


            int mid = 0;
            try {
                Response response = client.newCall(request).execute();
                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                mid = jsonObject1.getInt("_mid");

                items.add(new UFitEntityObject(jsonObjects[0], 5));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                MultipartBody.Builder builder = new MultipartBody.Builder();
                builder.setType(MultipartBody.FORM);
                File file = new File(getPath(uri));
                builder.addFormDataPart("_mid", mid+"");
                builder.addFormDataPart("_image", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
                body = builder.build();
                Request request1 = new Request.Builder()
                                            .url(UFitNetworkConstantDefinition.URL_UFIT_MEMBER_IMAGE)
                                            .put(body)
                                            .build();
                Response response = client.newCall(request1).execute();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex("_data"));
        cursor.close();
        return path;
    }
}
