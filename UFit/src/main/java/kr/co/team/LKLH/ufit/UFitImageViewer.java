package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 2016-08-12.
 */
public class UFitImageViewer extends DialogFragment {

    JSONObject albumObj;
    public UFitImageViewer() {}

    static UFitImageViewer newInstance(JSONObject jsonObject, String url, int code){
        UFitImageViewer f = new UFitImageViewer();
        Bundle b = new Bundle();
        b.putString("album", jsonObject.toString());
        b.putString("url", url);
        b.putInt("code", code);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ufit_image_viewer, container, false);
        try {
            albumObj = new JSONObject(getArguments().getString("album"));
            Glide.with(UFitApplication.getUFitContext())
                    .load(albumObj.getString("_image"))
                    .into((ImageView) view.findViewById(R.id.uf_image_viewer));

            // 뷰어 확인
            view.findViewById(R.id.viewer_check).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(UFitImageViewer.this).commit();
                }
            });

            // 뷰어 삭제버튼 리스너
            view.findViewById(R.id.viewer_delete).setOnClickListener(new View.OnClickListener() {
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
                            (new AsyncAlbumDelete()).execute(albumObj);
//                            dialogInterface.dismiss();
                        }
                    };
                    dialog = new AlertDialog.Builder(getContext())
                                            .setTitle(R.string.uf_wanna_delete)
                                            .setPositiveButton(R.string.uf_check, deleteListener)
                                            .setNegativeButton(R.string.uf_cancel, cancelListener).create();
                    dialog.show();

                }
            });
        } catch (Exception e) {
            Log.e("이미지뷰어", e.toString());
        }
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    private class AsyncAlbumDelete extends AsyncTask<JSONObject, Void, String> {
        mySerializableData serializableData = (mySerializableData)getArguments().getSerializable("view");
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_DELETE_JSON
                    (getArguments().getString("url"), jsonObjects);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getActivity().getSupportFragmentManager().beginTransaction().remove(UFitImageViewer.this).commit();

            /*if (getArguments().getInt("code") == 0 || getArguments().getInt("code") == 2) {
                ((CircleImageView)serializableData.mCircleImageView).setImageResource(R.drawable.iiii);
            } else {
                // TODO: 2016-08-16 이거 어떻게 해야되죠
                getActivity().finish();
                startActivity(new Intent(getActivity(), UFitTranerProfileActivity.class));
            }*/
        }
    }
}
