package kr.co.team.LKLH.ufit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-03.
 */
public class UFitJSONParseHandler {

    public static ArrayList<UFitCalendarCellEntityObject> getMemberMonthlySchedule(StringBuilder buf){
        ArrayList<UFitCalendarCellEntityObject> monthly_schedule= null;
        String result = null;

        JSONObject mainobj = null;
        JSONArray mainjsonarray = null;

        int[] monthlySchedule_Integer_Array = null;
        Log.e("어레이를받자", "" + buf);
        try{
            mainobj = new JSONObject(buf.toString());
            //result = mainobj.getString("data");
            mainjsonarray = mainobj.getJSONArray("data");

            monthly_schedule = new ArrayList<UFitCalendarCellEntityObject>(mainjsonarray.length());

            for(int i = 0;  i < mainjsonarray.length(); i++){
                Log.i("memmonth schedulelength", "" + mainjsonarray.length());
                Log.i("memmonth schedule", "" + mainjsonarray);
                UFitCalendarCellEntityObject entity = new UFitCalendarCellEntityObject();
                JSONObject listOB = mainjsonarray.getJSONObject(i);
                Log.i("memmonthschedule-single", "" + listOB);

                entity._cid = listOB.getInt("_cid");
                Log.i("memmonthschedule-_cid", "" + entity._cid);

                entity._date = listOB.getInt("_date");
                Log.i("memmonthschedule-_date", "" + entity._date);

                entity._attendance = listOB.getInt("_attendance");
                Log.i("memmonthschedule-_atten", "" + entity._attendance);

                JSONArray part = listOB.optJSONArray("_part");
                Log.i("memmonthschedulepart배열", "" + listOB.get("_part"));
//                Log.i("memmonthschedulepart0번", "" + listOB.getJSONArray("_part").getInt(0));
//                Log.i("memmonthschedulepart개수", "" + listOB.getJSONArray("_part").length());


                if(part != null ){
                    Log.e("오셧음1", "와따");
                    int size = part.length();
                    if( size > 0) {
                        entity._part = new int[size];
                        for (int j = 0; j < size ; j++) {
                            entity._part[j] = part.getInt(j);
                        }
                    }
                }

//                else{
//                    entity._part = null;
//                }

                monthly_schedule.add(entity);

            }


        } catch(Exception e){
            Log.e("Parse/Mem/MonthSched", e.toString());
        }
        Log.i("Parse/mem/month", "" + monthly_schedule);

        return monthly_schedule;
    }

    public static int[] getTrainerMonthlySchedule(StringBuilder buf){
        JSONArray monthly_schedule = null;
        String result = null;

        JSONObject mainobj = null;

        int[] monthlySchedule_Integer_Array = null;
        Log.e("어레이를받자", "" + buf);
        try{
            mainobj = new JSONObject(buf.toString());
            //result = mainobj.getString("data");
            monthly_schedule = mainobj.getJSONArray("data");

            monthlySchedule_Integer_Array = new int[monthly_schedule.length()];
            for(int i = 0; i < monthlySchedule_Integer_Array.length; i++){
                monthlySchedule_Integer_Array[i] = monthly_schedule.optInt(i);
            }

            Log.i("파스핸들러&monthlyInt_Array", "" + monthlySchedule_Integer_Array[0]);
            Log.i("leelog", monthly_schedule.get(0) + "");
            Log.i("ㅋ아ㅓㄹ카어", monthly_schedule+"");
            Log.e("먼술뤼스퀘줄", mainobj.toString() + "");

            //어레이만 받기위해선, JSONObject의 getString("키값")을 사용하자. key : [1,2,3,4,5] 의 값은 스트링(어레이)이다.
            Log.e("먼술뤼스퀘줄 날짜만~~222", mainobj.getString("data") + "");


        } catch(Exception e){
            Log.e("ParseHandler/MonthSched", e.toString());
        }
        Log.i("파스핸들러& json_main_list", "" + monthly_schedule);

        return monthlySchedule_Integer_Array;
    }

    public static ArrayList<UFitEntityObject> getMemberList(StringBuilder buf){
        ArrayList<UFitEntityObject> json_main_list = null;
        JSONObject mainobj = null;
        JSONArray mainlist = null;

        try{
            mainobj = new JSONObject(buf.toString());
            mainlist = mainobj.getJSONArray("data");
            json_main_list = new ArrayList<UFitEntityObject>(mainlist.length());

            for(int i = 0;  i < mainlist.length(); i++){
                Log.i("array 길이", "" + mainlist.length());
                Log.i("파스핸들러 mainlist", "" + mainlist);
                UFitEntityObject entity = new UFitEntityObject();
                JSONObject listOB = mainlist.getJSONObject(i);
                Log.i("파스핸들러&listOB", "" + listOB);

                entity._mid = listOB.getInt("_mid");
                Log.i("파스핸들러&listOB&mid", "" + entity._mid);

                entity._name = listOB.getString("_name");
                Log.i("파스핸들러&listOB&name", "" + entity._name);

                entity._time = listOB.getString("_time");
                Log.i("파스핸들러&listOB&time", "" + entity._time);

                entity._thumbnail = listOB.getString("_thumbnail");
                json_main_list.add(entity);

            }

        } catch(Exception e){
            Log.e("ParseHandler/MemberList", e.toString());
        }
        Log.i("파스핸들러& json_main_list", "" + json_main_list);

        return json_main_list;
    }
}
