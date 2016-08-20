package kr.co.team.LKLH.ufit;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Admin on 2016-08-19.
 */
public class UFitTrainerCareer extends DialogFragment {
    ImageView sendCareer;
    EditText careerYear, careerTitle;

    static UFitTrainerCareer newInstance() {
        return new UFitTrainerCareer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.ufit_trainer_career_add, container, false);
        sendCareer = (ImageView) view.findViewById(R.id.uf_send_career);
        careerYear = (EditText) view.findViewById(R.id.uf_career_year);
        careerTitle = (EditText) view.findViewById(R.id.uf_career_title);
        sendCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UFitTrainerHistoryRCV historyRCV = new UFitTrainerHistoryRCV();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.windowAnimations = R.style.BottomDialogAnimation;
        wlp.gravity = Gravity.BOTTOM;
        wlp.height = (int) (150 * 1.5);
        window.setAttributes(wlp);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final LinearLayout root = new LinearLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Dialog dialog = new Dialog(getActivity());
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // TODO: 2016-08-18 다이얼로그가 키보드에 밀려서 같이 올라가는 문제
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        return dialog;
    }
    /*private class AsyncTrainerCareer extends AsyncTask<JSONObject, Void, String>*/
}
