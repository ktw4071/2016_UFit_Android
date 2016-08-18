package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbSeekbar;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import im.dacer.androidcharts.LineView;
import me.crosswall.lib.coverflow.CoverFlow;
import me.crosswall.lib.coverflow.core.PagerContainer;

public class UFitUserProfile_Detail extends AppCompatActivity {

    Intent intent;
    Toolbar toolbar;
    ImageView tbLeft, tbRight;;
    TextView tbHead, uf_member_profile_weight_value, uf_member_profile_workout_level_value, uf_member_profile_size_value;
    Timer timer;
    DonutProgress donut_process_weight, donut_process_workout_level, donut_process_size;
    ViewPager uf_user_profile_detail_viewpager;


    int maximum_progress_value;
    int minimum_progress_value;
    int case_integer;
    int _mid;
    int weight_process_value;
    int workout_level_value;
    int size_value;

    int goal;
    int level;
    int achieve;

    ViewPager viewPager;
    CoverFlowFragmentPagerAdapter coverFlowFragmentPagerAdapter = null;
    PagerContainer pagerContainer;
    int currentItemSetter = 0;

    // 라인그래프
    LineView lineView;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ufit_user_profile_detail);

        // 라인그래프
        lineView = (LineView)findViewById(R.id.lineView);

        intent = getIntent();
        maximum_progress_value = intent.getIntExtra("maximum_progress_value", 1);
        minimum_progress_value = intent.getIntExtra("minimum_progress_value", 1);
        case_integer = intent.getIntExtra("case_integer", 1);
        _mid = intent.getIntExtra("_mid", 1);
        weight_process_value = intent.getIntExtra("weight_process_value", 1);
        workout_level_value = intent.getIntExtra("workout_level_value", 1);
        size_value = intent.getIntExtra("size_value", 1);
        goal = intent.getIntExtra("goal", 1);
        level = intent.getIntExtra("level", 1);
        achieve = intent.getIntExtra("achieve", 1);


        //donut process
        donut_process_weight = (DonutProgress)findViewById(R.id.donut_process_weight);
        donut_process_weight.setRotation(270);
        donut_process_workout_level = (DonutProgress)findViewById(R.id.donut_process_workout_level);
        donut_process_workout_level.setRotation(270);
        donut_process_size = (DonutProgress)findViewById(R.id.donut_process_size);
        donut_process_size.setRotation(270);
        uf_member_profile_weight_value = (TextView)findViewById(R.id.uf_member_profile_weight_value);
        uf_member_profile_workout_level_value = (TextView)findViewById(R.id.uf_member_profile_workout_level_value);
        uf_member_profile_size_value = (TextView)findViewById(R.id.uf_member_profile_size_value);

        //            donut_process_weight.setProgress(weight_process_value);
        uf_member_profile_weight_value.setText(goal + " KG");
        uf_member_profile_workout_level_value.setText("LV " + level);
        uf_member_profile_size_value.setText(achieve + "/5");
//            donut_process_workout_level.setProgress(workout_level_value);
//            donut_process_size.setProgress(size_value);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (case_integer){
                            case 1:
                                if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_size.getProgress() != size_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            case 2:
                                if(donut_process_size.getProgress() != size_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            case 3:
                                if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_size.getProgress() != size_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                }
                                else if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            case 4:
                                if(donut_process_size.getProgress() != size_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            case 5:
                                if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                }
                                else if(donut_process_size.getProgress() != size_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            case 6:
                                if(donut_process_workout_level.getProgress() != workout_level_value){
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                    donut_process_workout_level.setProgress(donut_process_workout_level.getProgress() + 1);
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else if(donut_process_weight.getProgress() != weight_process_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                    donut_process_weight.setProgress(donut_process_weight.getProgress() + 1);
                                }
                                else if(donut_process_size.getProgress() != size_value){
                                    donut_process_size.setProgress(donut_process_size.getProgress() + 1);
                                }
                                else
                                    timer.cancel();

                                break;

                            default:
                                timer.cancel();
                        }
                    }
                });
            }
        }, 700, 10);





        pagerContainer = (PagerContainer)findViewById(R.id.pager_container);
        pagerContainer.setOverlapEnabled(true);

        viewPager = pagerContainer.getViewPager();
//        CoverFlowFragmentPagerAdapter coverFlowFragmentPagerAdapter = new CoverFlowFragmentPagerAdapter(getSupportFragmentManager());
//        viewPager.setOffscreenPageLimit(coverFlowFragmentPagerAdapter.getCount());
//        viewPager.setAdapter(coverFlowFragmentPagerAdapter);

        new CoverFlow.Builder().with(viewPager)
                .scale(0.3f)
                .pagerMargin(getResources().getDimensionPixelSize(R.dimen.overlap_pager_margin))
                .spaceSize(0f)
                .build();

//        viewPager.post(new Runnable() {
//            @Override public void run() {
//                Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
//                ViewCompat.setElevation(fragment.getView(), 8.0f);
//            }
//        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("몇개나있나", viewPager.getPageMargin() + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        new DetailBodySize().execute(String.valueOf(_mid), "20100101", "20200101");
        new WeightLineGraph().execute(String.valueOf(_mid), "20100101", "20200101");

        //슬라이드바

        final BubbleThumbSeekbar rangeSeekbar = (BubbleThumbSeekbar) findViewById(R.id.rangeSeekbar3);

        // get min and max text view
        final TextView tvMin = (TextView) findViewById(R.id.textMin3);

        // set listener
        rangeSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                tvMin.setText(String.valueOf(minValue));
                Log.e("벨류", rangeSeekbar.getSelectedMinValue() + "입니다");
            }

        });
        rangeSeekbar.setMinStartValue(3).apply();






        //신경쓰지말자
        toolbar = (Toolbar)findViewById(R.id.uf_main_toolbar);
        setSupportActionBar(toolbar);
        tbHead = (TextView) findViewById(R.id.uf_toolbar_head);
        tbHead.setText("상세정보");
        tbLeft = (ImageView)findViewById(R.id.uf_toolbar_left);
        tbLeft.setImageResource(R.drawable.btn_topleft);
        tbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tbRight= (ImageView)findViewById(R.id.uf_toolbar_right);
        tbRight.setVisibility(View.INVISIBLE);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.detail_size, UFitUserProfile_DetailSizeCard.newInstance());
//        ft.commit();
        //신경쓰지말자
    }



    private class CoverFlowFragmentPagerAdapter extends FragmentStatePagerAdapter{
        int total;
        ArrayList<UFitEntityObject> sizeList;

        public CoverFlowFragmentPagerAdapter(FragmentManager fm, int total, ArrayList<UFitEntityObject> sizeList) {
            super(fm);
            if(total != 0 && sizeList != null){
                this.total = total;
                this.sizeList = sizeList;
            }

        }

        @Override
        public Fragment getItem(int position) {
//            if(position == 0){
//                return UFitUserProfile_Detail_CoverFlow_Fragment.newInstance();
//            }
//            else{
//                return UFitUserProfile_Detail_CoverFlow_Fragment.newInstance(sizeList.get(position));
//            }


            if(position == total){
                return UFitUserProfile_Detail_CoverFlow_Fragment.newInstance();
            }
            else
                return UFitUserProfile_Detail_CoverFlow_Fragment.newInstance(sizeList.get(position));
            }


        @Override
        public int getCount() {
            if(total == 0){
                return 1;
            }
            else
                return total + 1;
        }
    }

    public class WeightLineGraph extends AsyncTask<String, Integer, ArrayList<UFitEntityObject>> {


        @Override
        protected ArrayList doInBackground(String... strings) {
            Log.i("who am i?","" + UFitHttpConnectionHandler.memberProfileWeightLineGraph(strings[0], strings[1], strings[2]));
            return UFitHttpConnectionHandler.memberProfileWeightLineGraph(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> weightList) {
            ArrayList<UFitEntityObject> weightListContainer = new ArrayList<>();
            int size = weightList.size();
            ArrayList<String> bottomText = new ArrayList<>();
            ArrayList<Integer> weightValues = new ArrayList<>();
            ArrayList<ArrayList<Integer>> dataLists = new ArrayList<>();
            Log.e("난여기서", weightList + "");
            if (weightList != null && size > 0){

                for(int i = 0; i < size; i++){
                    bottomText.add(weightList.get(i)._date.substring(4));
                    weightValues.add(weightList.get(i)._value);
                }
            }
//            if (weightList != null && size > 0) {
//                for (int i = 0; i < size; i++) {
//                    Log.i("어씽크", weightList + "");
//                    Log.i("어씽크 한가지", weightList.get(i) + "");
//                    UFitEntityObject d = new UFitEntityObject();
//                    d._date = weightList.get(i)._date;
//                    Log.i("안녕 ! _date", weightList.get(i)._date + "");
//                    d._wid = weightList.get(i)._wid;
//                    Log.i("안녕 ! _zid", weightList.get(i)._zid + "");
//                    d._value = weightList.get(i)._value;
//                    Log.i("안녕 ! _chest", weightList.get(i)._chest + "");
//
//                    weightListContainer.add(d);
//                }
//            }
            dataLists.add(weightValues);
            lineView.setDrawDotLine(false);
            lineView.setShowPopup(LineView.SHOW_POPUPS_All);
            lineView.setBottomTextList(bottomText);
            lineView.setDataList(dataLists);

            Log.e("라인그래프1", "" + dataLists);
            Log.e("라인그래프2", "" + bottomText);

            Log.e("2weight values", weightValues + "");
            Log.e("2bottom text", bottomText + "");
            Log.e("2datalists text", dataLists + "");


            super.onPostExecute(weightList);
        }
    }

    public class DetailBodySize extends AsyncTask<String, Integer, ArrayList<UFitEntityObject>> {


        @Override
        protected ArrayList doInBackground(String... strings) {
            Log.i("who am i?","" + UFitHttpConnectionHandler.mainlist(strings[0], strings[1]));
            return UFitHttpConnectionHandler.memberProfileDetailBodySize(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> sizeList) {
            ArrayList<UFitEntityObject> sizeListContainer = new ArrayList<>();
            if (sizeList != null && sizeList.size() > 0) {
                for (int i = 0; i < sizeList.size(); i++) {
                    Log.i("어씽크", sizeList + "");
                    Log.i("어씽크 한가지", sizeList.get(i) + "");
                    UFitEntityObject d = new UFitEntityObject();
                    d._date = sizeList.get(i)._date;
                    Log.i("안녕 ! _date", sizeList.get(i)._date + "");
                    d._zid = sizeList.get(i)._zid;
                    Log.i("안녕 ! _zid", sizeList.get(i)._zid + "");
                    d._chest = sizeList.get(i)._chest;
                    Log.i("안녕 ! _chest", sizeList.get(i)._chest + "");
                    d._thigh = sizeList.get(i)._thigh;
                    Log.i("안녕 ! _thigh", sizeList.get(i)._thigh + "");
                    d._calf = sizeList.get(i)._calf;
                    Log.i("안녕 ! _calf", sizeList.get(i)._calf + "");
                    d._forearm = sizeList.get(i)._forearm;
                    Log.i("안녕 ! _forearm", sizeList.get(i)._forearm + "");
                    d._waist = sizeList.get(i)._waist;
                    Log.i("안녕 ! _waist", sizeList.get(i)._waist + "");
                    sizeListContainer.add(d);
                }
            }
            Log.e("뭐22", "" + sizeListContainer);

            if(sizeList.size() > 0){
                currentItemSetter = sizeList.size();
            }
            Log.e("커런트", currentItemSetter + "");
            coverFlowFragmentPagerAdapter = new CoverFlowFragmentPagerAdapter(getSupportFragmentManager(), currentItemSetter, sizeListContainer);
            viewPager = pagerContainer.getViewPager();
            viewPager.setAdapter(coverFlowFragmentPagerAdapter);
            viewPager.setCurrentItem(0);
            viewPager.setOffscreenPageLimit(5);

            viewPager.post(new Runnable() {
                @Override public void run() {
                    Fragment fragment = (Fragment) viewPager.getAdapter().instantiateItem(viewPager, 0);
                    ViewCompat.setElevation(fragment.getView(), 8.0f);
                }
            });

            super.onPostExecute(sizeList);
        }
    }
}
