package kr.co.team.LKLH.ufit;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-07.
 */
public class UFitMainFragment extends Fragment {
    public static RecyclerView memberListRecycler;
    public MemberItemAdapter memberItemAdapter;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(UFitApplication.getUFitContext());

    public static UFitMainFragment newInstance(int year, int month, int day){
        UFitMainFragment uFitmainFragment = new UFitMainFragment();
        Bundle args = new Bundle();
        args.putInt("ThisYear", year);
        args.putInt("ThisMonth", month);
        args.putInt("ThisDay", day);
        uFitmainFragment.setArguments(args);
        Log.e("날짜좀보자", year + " --" + month + " -- " + day);
        return uFitmainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("크리에티느 날짜", "" + super.getArguments().getInt("ThisYear") + String.format("%02d", super.getArguments().getInt("ThisMonth")) + String.format("%2d",super.getArguments().getInt("ThisDay")));

        Log.e("요청 날짜 !", super.getArguments().getInt("ThisYear") + String.format("%02d", super.getArguments().getInt("ThisMonth") + 1) + String.format("%02d",super.getArguments().getInt("ThisDay")));
        new TodayMemberList().execute("1", "" +  super.getArguments().getInt("ThisYear") + String.format("%02d", super.getArguments().getInt("ThisMonth") + 1) + String.format("%02d",super.getArguments().getInt("ThisDay")));
        super.onCreate(savedInstanceState);
    }

    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.ufit_main_fragment, container, false);
        context = getContext();
        memberListRecycler = (RecyclerView)view.findViewById(R.id.main_recycler);
        memberListRecycler.setLayoutManager(mLayoutManager);
        memberListRecycler.setAdapter(memberItemAdapter);
//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public class TodayMemberList extends AsyncTask<String, Integer, ArrayList<UFitEntityObject>> {


        @Override
        protected ArrayList doInBackground(String... strings) {
            Log.i("who am i?","" + UFitHttpConnectionHandler.mainlist(strings[0], strings[1]));
            return UFitHttpConnectionHandler.mainlist(strings[0], strings[1]);
        }

        @Override
        protected void onPostExecute(ArrayList<UFitEntityObject> memberList) {
            ArrayList<UFitEntityObject> memberListContainer = new ArrayList<>();
            if (memberList != null && memberList.size() > 0) {
                for (int i = 0; i < memberList.size(); i++) {
                    Log.i("안녕&main 전부다나와라", memberList + "");
                    Log.i("안녕 !!!", memberList.get(i) + "");
                    UFitEntityObject d = new UFitEntityObject();
                    d._mid = memberList.get(i)._mid;
                    Log.i("안녕 ! mid", memberList.get(i)._mid + "");
                    d._name = memberList.get(i)._name;
                    Log.i("안녕 ! name", memberList.get(i)._name + "");
                    d._time = memberList.get(i)._time;
                    Log.i("안녕 ! time", memberList.get(i)._time + "");
                    memberListContainer.add(d);
                }
            }

            MemberItemAdapter memberRecyclerViewAdapter = new MemberItemAdapter(context, memberList, "UFitMainActivity");
            memberListRecycler.setAdapter(memberRecyclerViewAdapter);
            super.onPostExecute(memberList);
        }
    }
}
