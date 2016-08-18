package kr.co.team.LKLH.ufit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class new_UFitImageViewer extends AppCompatActivity {

    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ufit_image_viewer);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        getWindow().setLayout(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("data"));
            Glide.with(UFitApplication.getUFitContext()).load(jsonObject.getString("_image"))
                    .into((ImageView) findViewById(R.id.uf_image_viewer));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //뷰어 닫기버튼
        findViewById(R.id.viewer_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 뷰어 삭제버튼
        findViewById(R.id.viewer_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = null;
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                            dialogInterface.dismiss();
                    }
                };
                DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        (new AsyncAlbumDelete()).execute(jsonObject);
//                            dialogInterface.dismiss();
                    }
                };
                dialog = new AlertDialog.Builder(new_UFitImageViewer.this)
                        .setTitle(R.string.uf_wanna_delete)
                        .setPositiveButton(R.string.uf_check, deleteListener)
                        .setNegativeButton(R.string.uf_cancel, cancelListener).create();
                dialog.show();

            }
        });




    }
    private class AsyncAlbumDelete extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_DELETE_JSON
                    (getIntent().getStringExtra("url"), jsonObjects);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = getIntent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
