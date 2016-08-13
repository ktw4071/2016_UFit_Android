package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
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
import org.json.JSONObject;

/**
 * Created by Admin on 2016-08-12.
 */
public class UFitImageViewer extends DialogFragment {

    JSONObject albumObj;


    static UFitImageViewer newInstance(JSONObject jsonObject, String url){
        UFitImageViewer f = new UFitImageViewer();
        Bundle b = new Bundle();
        b.putString("album", jsonObject.toString());
        b.putString("url", url);
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
            view.findViewById(R.id.viewer_check).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().beginTransaction().remove(UFitImageViewer.this).commit();
                }
            });
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
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        return dialog;
    }
    private class AsyncAlbumDelete extends AsyncTask<JSONObject, Void, String> {
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            return (new LosDatosDeLaRed_JSON()).LosDatosDeLaRed_DELETE_JSON
                    (getArguments().getString("url"), jsonObjects);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getActivity().getSupportFragmentManager().beginTransaction().remove(UFitImageViewer.this).commit();
            getActivity().finish();
            startActivity(new Intent(getActivity(), UFitTranerProfileActivity.class));
        }
    }
}
