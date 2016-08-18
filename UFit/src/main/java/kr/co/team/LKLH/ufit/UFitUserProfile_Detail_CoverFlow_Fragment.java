package kr.co.team.LKLH.ufit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-15.
 */
public class UFitUserProfile_Detail_CoverFlow_Fragment extends Fragment {
    EditText detail_size_chest, detail_size_thigh, detail_size_calf, detail_size_waist, detail_size_forearm, detail_size_date;
    public static UFitUserProfile_Detail_CoverFlow_Fragment newInstance(){
        UFitUserProfile_Detail_CoverFlow_Fragment coverFlowFragment = new UFitUserProfile_Detail_CoverFlow_Fragment();
        Bundle args = new Bundle();
        args.putBoolean("DoIExist", false);
        coverFlowFragment.setArguments(args);
        return coverFlowFragment;
    }

    public static UFitUserProfile_Detail_CoverFlow_Fragment newInstance(UFitEntityObject bodysize){
        UFitUserProfile_Detail_CoverFlow_Fragment coverFlowFragment = new UFitUserProfile_Detail_CoverFlow_Fragment();
        Bundle args = new Bundle();
        Log.e("프래그먼트 체스트", bodysize._chest + "");
        args.putBoolean("DoIExist", true);
        args.putInt("_chest", bodysize._chest);
        args.putInt("_thigh", bodysize._thigh);
        args.putInt("_calf", bodysize._calf);
        args.putInt("_waist", bodysize._waist);
        args.putInt("_forearm", bodysize._forearm);
        args.putString("_date", bodysize._date);
        coverFlowFragment.setArguments(args);
        return coverFlowFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ufit_user_profile_detail_coverflow_fragment, container, false);
        detail_size_chest = (EditText)view.findViewById(R.id.detail_size_chest);
        detail_size_thigh = (EditText)view.findViewById(R.id.detail_size_thigh);
        detail_size_calf = (EditText)view.findViewById(R.id.detail_size_calf);
        detail_size_waist = (EditText)view.findViewById(R.id.detail_size_waist);
        detail_size_forearm = (EditText)view.findViewById(R.id.detail_size_forearm);
        detail_size_date = (EditText)view.findViewById(R.id.detail_size_date);

        if(super.getArguments().getBoolean("DoIExist")){
            detail_size_chest.setText(String.valueOf(super.getArguments().getInt("_chest")));
            detail_size_thigh.setText(String.valueOf(super.getArguments().getInt("_thigh")));
            detail_size_calf.setText(String.valueOf(super.getArguments().getInt("_calf")));
            detail_size_waist.setText(String.valueOf(super.getArguments().getInt("_waist")));
            detail_size_forearm.setText(String.valueOf(super.getArguments().getInt("_forearm")));
            detail_size_date.setText(String.valueOf(super.getArguments().getString("_date")));
        }


        return view;
    }
}
