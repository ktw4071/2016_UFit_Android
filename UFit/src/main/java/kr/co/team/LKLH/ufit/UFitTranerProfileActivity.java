package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class UFitTranerProfileActivity extends AppCompatActivity
    implements CalendarDatePickerDialogFragment.OnDateSetListener{

    boolean PROFIL_EDIT_FLAG;
    Toolbar toolbar;
    ImageView toolbarLeft, toolbarRight;
    TextView toolbarHead;
    TextView trName, trBirth, trCareer, trGetMem, trLocation;
    public CircleImageView trProFileImg;
    JSONObject jsonTrainerData, history;
    JSONArray f;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_trainer_profile);
        (new AsyncTrainerProfile()).execute();
        jsonTrainerData = new JSONObject();

        toolbar = (Toolbar) findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        toolbarLeft = (ImageView) findViewById(R.id.uf_toolbar_left);
        toolbarRight = (ImageView) findViewById(R.id.uf_toolbar_right);
        toolbarHead = (TextView) findViewById(R.id.uf_toolbar_head);
        trName = (TextView) findViewById(R.id.trainer_name);
        trBirth = (TextView) findViewById(R.id.trainer_birth);
        trCareer = (TextView) findViewById(R.id.trainer_career);
        trGetMem = (TextView) findViewById(R.id.trainer_getmem);
        trLocation = (TextView) findViewById(R.id.trainer_location);
        trProFileImg = (CircleImageView) findViewById(R.id.uf_tr_profile_img);

        toolbarHead.setText(R.string.uf_profile);
        toolbarLeft.setImageResource(R.drawable.btn_back);
        toolbarRight.setImageResource(R.drawable.btn_setting_tp);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.uf_trainer_history, UFitTrainerHistoryRCV.newInstance())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ufit_trainer_image, UFitTrainerImageRCV.newInstance())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프로필 수정을 끝냄
                if (!PROFIL_EDIT_FLAG) {
                    trName.setEnabled(true);
                    trName.setBackgroundResource(R.drawable.rounded_edittext_black);
                    trBirth.setEnabled(true);
                    trBirth.setText(trBirth.getText().toString().substring(0, 4)
                            + trBirth.getText().toString().substring(6, 8)
                            + trBirth.getText().toString().substring(10, 12));
                    trBirth.setBackgroundResource(R.drawable.rounded_edittext_black);
                    PROFIL_EDIT_FLAG = true;
                //프로필 수정으로 진입
                } else {
                    try {
                        history = new JSONObject();
                        history.put("_from", 20140000);
                        history.put("_to", 20160000);
                        history.put("_content", "성공해라 서버야");
                        f = new JSONArray();
                        f.put(history);
                        jsonTrainerData.put("_name", trName.getText());
                        jsonTrainerData.put("_birth", trBirth.getText());
                        jsonTrainerData.put("_career", 3);
                        jsonTrainerData.put("_location1", "대전광역시");
                        jsonTrainerData.put("_location2", "여러분");
                        jsonTrainerData.put("_history", f);
                        Log.e("샬롬 샬롬", jsonTrainerData + "");
                    } catch (JSONException e) {
                        Log.e("TATA", e.toString());
                    }
                    (new AsyncTPEdit()).execute(jsonTrainerData);
                    trName.setEnabled(false);
                    trBirth.setEnabled(false);
                    PROFIL_EDIT_FLAG = false;
                }
            }
        });
        // 생년월일 입력 달력 다이알로그 호출
        trBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(UFitTranerProfileActivity.this)
                        .setPreselectedDate(1990, 1 ,1);
                cdp.show(getSupportFragmentManager(), null);
            }
        });
        // 프로필 이미지 수정 버튼, 이미지업로드로 진입함
        findViewById(R.id.uf_trainer_image_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aintent = new Intent(UFitTranerProfileActivity.this, UFitImageUploadHelper.class);
                aintent.putExtra("code", 0);
                aintent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_TRAINER_IMAGE_UPLOAD);
                startActivityForResult(aintent, 0);
            }
        });
        // 앨범 이미지 추가 버튼
        findViewById(R.id.uf_trainer_album_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UFitTranerProfileActivity.this, UFitImageUploadHelper.class);
                intent.putExtra("code", 1);
                intent.putExtra("url", UFitNetworkConstantDefinition.URL_UFIT_TRAINER_ALBUM_UPLOAD);
                startActivityForResult(intent, 0);
            }
        });
        // 프로필 이미지 확대
        findViewById(R.id.uf_tr_profile_img).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mySerializableData serializableData = new mySerializableData();
                serializableData.mCircleImageView = trProFileImg;
                getSupportFragmentManager().beginTransaction()
                        .add(UFitImageViewer.newInstance
                                (serializableData, jsonTrainerData, UFitNetworkConstantDefinition.URL_UFIT_TRAINER_IMAGE_UPLOAD, 0), "tpimage")
                        .addToBackStack("tpimage").commit();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode== RESULT_OK) {
                try {
                    (new AsyncTrainerProfile()).execute();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ufit_trainer_image, UFitTrainerImageRCV.newInstance())
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        trBirth.setText(year+""+String.format("%02d",monthOfYear+1)+""+String.format("%02d",dayOfMonth));
    }

    private class AsyncTrainerProfile extends AsyncTask<Void, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(Void... voids) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_GET_JSON
                    (UFitNetworkConstantDefinition.URL_UFIT_TRAINER_PROFILE, "data", 2);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
            jsonTrainerData = new JSONObject();
            try {
                jsonTrainerData.put("_image", arrayList.get(0)._image);
                jsonTrainerData.put("_thumbnail", arrayList.get(0)._thumbnail);
                Log.e("MYMY", jsonTrainerData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            trName.setText(arrayList.get(0)._name);
            trBirth.setText(arrayList.get(0)._birth.substring(0, 4) + "년 " +
                    arrayList.get(0)._birth.substring(4, 6) + "월 " +
                    arrayList.get(0)._birth.substring(6, 8) + "일");
            trCareer.setText(arrayList.get(0)._career + "년");
            trGetMem.setText(arrayList.get(0)._memberTotal + "명");
            trLocation.setText(arrayList.get(0)._location1 + " " + arrayList.get(0)._location2);
            if (arrayList.get(0)._thumbnail == null) {
                trProFileImg.setImageResource(R.drawable.iiii);
            } else {
                Glide.with(UFitApplication.getUFitContext()).load(arrayList.get(0)._thumbnail).into(trProFileImg);
            }
        }
    }

    private class AsyncTPEdit extends AsyncTask<JSONObject, Void, ArrayList<UFitEntityObject>> {
        @Override
        protected ArrayList<UFitEntityObject> doInBackground(JSONObject... jsonObjects) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_POST_JSON
                    (jsonObjects, UFitNetworkConstantDefinition.URL_UFIT_TRAINER_PROFILE, 4, 0);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> arrayList) {
            super.onPostExecute(arrayList);
            trName.setText(arrayList.get(0)._name);
            trBirth.setText(arrayList.get(0)._birth.substring(0, 4) + "년 " +
                    arrayList.get(0)._birth.substring(4, 6) + "월 " +
                    arrayList.get(0)._birth.substring(6, 8) + "일");
        }
    }
}