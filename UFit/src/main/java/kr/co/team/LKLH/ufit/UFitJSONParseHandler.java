package kr.co.team.LKLH.ufit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-03.
 */
public class UFitJSONParseHandler {


    public static ArrayList<UFitEntityObject> memberProfileWeightLineGraph(StringBuilder buf){
        ArrayList<UFitEntityObject> json_main_list = null;
        JSONObject mainobj = null;
        JSONArray mainlist = null;
        int size;

        try{
            mainobj = new JSONObject(buf.toString());
            mainlist = mainobj.getJSONArray("data");
            size = mainobj.getInt("total");
            json_main_list = new ArrayList<UFitEntityObject>(size);

            for(int i = 0;  i < size; i++){
                Log.i("array 길이", "" + mainlist.length());
                Log.i("파스핸들러 mainlist", "" + mainlist);
                UFitEntityObject entity = new UFitEntityObject();
                JSONObject listOB = mainlist.getJSONObject(i);
                Log.i("파스핸들러&listOB", "" + listOB);

                entity._date = listOB.getString("_date");
                Log.i("파스핸들러&listOB&_date", "" + entity._date);

                entity._wid = listOB.getInt("_wid");
                Log.i("파스핸들러&listOB&_wid", "" + entity._wid);

                entity._value = listOB.getInt("_value");
                Log.i("파스핸들러&listOB&_value", "" + entity._value);

                json_main_list.add(entity);
            }

        } catch(Exception e){
            Log.e("ParseHandler/bodysize", e.toString());
        }
        Log.i("파스핸들러& bodysize", "" + json_main_list);

        return json_main_list;
    }

    public static ArrayList<UFitEntityObject> memberProfileDetailBodySize(StringBuilder buf){
        ArrayList<UFitEntityObject> json_main_list = null;
        JSONObject mainobj = null;
        JSONArray mainlist = null;
        int size;

        try{
            mainobj = new JSONObject(buf.toString());
            mainlist = mainobj.getJSONArray("data");
            size = mainobj.getInt("total");
            json_main_list = new ArrayList<UFitEntityObject>(size);

            for(int i = 0;  i < size; i++){
                Log.i("array 길이", "" + mainlist.length());
                Log.i("파스핸들러 mainlist", "" + mainlist);
                UFitEntityObject entity = new UFitEntityObject();
                JSONObject listOB = mainlist.getJSONObject(i);
                Log.i("파스핸들러&listOB", "" + listOB);

                entity._date = listOB.getString("_date");
                Log.i("파스핸들러&listOB&_date", "" + entity._date);

                entity._zid = listOB.getInt("_zid");
                Log.i("파스핸들러&listOB&_zid", "" + entity._zid);

                entity._chest = listOB.getInt("_chest");
                Log.i("파스핸들러&listOB&_chest", "" + entity._chest);

                entity._thigh = listOB.getInt("_thigh");
                Log.i("파스핸들러&listOB&_thigh", "" + entity._thigh);

                entity._calf = listOB.getInt("_calf");
                Log.i("파스핸들러&listOB&_calf", "" + entity._calf);

                entity._forearm = listOB.getInt("_forearm");
                Log.i("파스핸들러&listOB&_forearm", "" + entity._forearm);

                entity._waist = listOB.getInt("_waist");
                Log.i("파스핸들러&listOB&_waist", "" + entity._waist);

                json_main_list.add(entity);
            }

        } catch(Exception e){
            Log.e("ParseHandler/bodysize", e.toString());
        }
        Log.i("파스핸들러& bodysize", "" + json_main_list);

        return json_main_list;
    }

    public static UFitEntityObject memberProfile(StringBuilder buf){
        UFitEntityObject entity = null;
        JSONObject mainobj = null;
        JSONArray mainlist = null;

        try{
            mainobj = new JSONObject(buf.toString());
            mainlist = mainobj.getJSONArray("data");
            entity = new UFitEntityObject();

            for(int i = 0;  i < mainlist.length(); i++){
                JSONObject listOB = mainlist.getJSONObject(i);
                Log.i("파스&memberprofi&object", "" + listOB);

                entity._name = listOB.getString("_name");
                Log.i("파스&memberprofi&_name", "" + entity._name);

                entity._image = listOB.getString("_image");
                Log.i("파스&memberprofi&_image", "" + entity._image);

                entity._birth = listOB.getString("_birth");
                Log.i("파스&memberprofi&_birth", "" + entity._birth);

                entity._number = listOB.getString("_number");
                Log.i("파스&memberprofi&_number", "" + entity._number);

                entity._initial = listOB.getInt("_initial");
                Log.i("파스&memberprofi&_init", "" + entity._initial);

                entity._weight = listOB.getInt("_weight");
                Log.i("파스&memberprofi&_weight", "" + entity._weight);

                entity._goal = listOB.getInt("_goal");
                Log.i("파스&memberprofi&_goal", "" + entity._goal);

                entity._level = listOB.getInt("_level");
                Log.i("파스&memberprofi&_level", "" + entity._level);

                entity._achieve = listOB.getInt("_achieve");
                Log.i("파스&memberprofi&_achieve", "" + entity._achieve);

                entity._thumbnail = listOB.getString("_thumbnail");
                Log.i("파스&memberprofi&_thumb", "" + entity._thumbnail);
            }

        } catch(Exception e){
            Log.e("ParseHandler/MemberList", e.toString());
        }
        Log.i("파스&memberprofi&entity", "" + entity);

        return entity;
    }

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
