package kr.co.team.LKLH.ufit;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-03.
 */
public class UFitHttpConnectionHandler {
    // 멤버 몸무게 라인그래프 기록
    public static ArrayList<UFitEntityObject> memberProfileWeightLineGraph(String _mid, String _from, String _to) {
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        ArrayList<UFitEntityObject> MainList = null;

        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Member_Profile_Weight_Line_Graph + "_mid=" + _mid + "&_from=" + _from + "&_to=" + _to, "GET");
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            MainList = UFitJSONParseHandler.memberProfileWeightLineGraph(buf);
            Log.i("메인리스트 in COnnHandler", MainList + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return MainList;
    }

    // 멤버 신체사이즈
    public static ArrayList<UFitEntityObject> memberProfileDetailBodySize(String _mid, String _from, String _to) {
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        ArrayList<UFitEntityObject> MainList = null;

        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Member_Profile_Detail_Body_Size + "_mid=" + _mid + "&_from=" + _from + "&_to=" + _to, "GET");
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            MainList = UFitJSONParseHandler.memberProfileDetailBodySize(buf);
            Log.i("메인리스트 in COnnHandler", MainList + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return MainList;
    }

    // 멤버 프로필
    public static UFitEntityObject memberProfile(int _mid){
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        UFitEntityObject uFitEntityObject = null;
        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Member_Profile + "_mid=" + _mid, "GET");
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            uFitEntityObject = UFitJSONParseHandler.memberProfile(buf);
            Log.i("멤버프로필 in COnnHandler", uFitEntityObject + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return uFitEntityObject;
    }

    // 멤버 - 그달의 스케쥴 표시.

    public static String deleteMemberSchedule(int _sid){
        String result = null;
        HttpURLConnection urlCon = null;
        OutputStream toServer = null;
        BufferedReader fromServer = null;
        BufferedReader bufferedReader = null;
        StringBuilder queryStringParams = new StringBuilder();
        try {
            queryStringParams.append("&_sid=" + _sid);

            Log.i("결과 - 3 ", "" + queryStringParams);

            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_Ufit_Trainer_Member_Schedule_Delete, "DELETE");


            toServer = urlCon.getOutputStream();


            toServer.write(queryStringParams.toString().getBytes("UTF-8"));

            toServer.flush();
            toServer.close();

            int responseCode = urlCon.getResponseCode();
            Log.i("통신결과  ", "" + responseCode);
            if(responseCode >= 200 && responseCode < 300){
                fromServer = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                Log.i("통신결과 프롬서버", "" + fromServer);
                StringBuilder jsonBuf = new StringBuilder();
                String line = "";
                while((line = fromServer.readLine()) != null){
                    jsonBuf.append(line);
                }
                JSONObject jsonObject = new JSONObject(jsonBuf.toString());
                Log.i("통신결과  오브젝트", "" + jsonObject);

                result = jsonObject.getString("total");
                Log.i("결과 - 1 ", "성공" + result);
                return result;
            }
        } catch(Exception e){

        } finally{
            if(toServer != null){
                try{
                    toServer.close();
                }catch (IOException ioe){

                }
            }
        }
        return null;
    }

    public static ArrayList<UFitCalendarCellEntityObject> memberMonthlySchedule(String _mid, String _from, String _to) {
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        ArrayList<UFitCalendarCellEntityObject> member_MonthlySchedule = null;

        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Member_Monthly_Schedule + "_mid=" + _mid + "&_from=" + _from + "&_to=" + _to, "GET");
            Log.e("유알엘", "" + UFitNetworkConstantDefinition.URL_UFit_Member_Monthly_Schedule + "_mid=" + _mid + "&_from=" + _from + "&_to=" + _to);
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            member_MonthlySchedule = UFitJSONParseHandler.getMemberMonthlySchedule(buf);
            Log.i("메인리스트 in COnnHandler", member_MonthlySchedule + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return member_MonthlySchedule;
    }

    // 트레이너 - 그달의 스케쥴 표시.
    public static int[] mainlist(String _tid, String _from, String _to) {
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        int[] monthlySchedule = null;

        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Trainer_Monthly_Schedule + "_tid=" + _tid + "&_from=" + _from + "&_to=" + _to, "GET");
            Log.e("유알엘", "" + UFitNetworkConstantDefinition.URL_UFit_Trainer_Monthly_Schedule + "_tid=" + _tid + "&_from=" + _from + "&_to=" + _to);
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            monthlySchedule = UFitJSONParseHandler.getTrainerMonthlySchedule(buf);
            Log.i("메인리스트 in COnnHandler", monthlySchedule + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return monthlySchedule;
    }

    //선택한 날짜의 회원리스트 가져오기
    public static ArrayList<UFitEntityObject> mainlist(String _tid, String _date) {
        HttpURLConnection urlCon = null;
        BufferedReader jsonStreamData = null;
        ArrayList<UFitEntityObject> MainList = null;

        try {
            urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_UFit_Trainer_Selected_Day_Schedule + "_tid=" + _tid + "&_date=" + _date, "GET");
            jsonStreamData = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
            String line = "";
            StringBuilder buf = new StringBuilder();


            while ((line = jsonStreamData.readLine()) != null) {
                buf.append(line);
            }

            MainList = UFitJSONParseHandler.getMemberList(buf);
            Log.i("메인리스트 in COnnHandler", MainList + "");
//            return MainList;

        } catch (IOException ioe) {
            Log.e("8888888888888888888", ioe.toString());
        } finally {
            UFitHttpURLConnectionManager.setDismissConnection(urlCon, jsonStreamData, null);
        }

        return MainList;
    }

    public static String AddMember(String _tid, String _mid, String _date, String _time){
        String result = null;
        HttpURLConnection urlCon = null;
        OutputStream toServer = null;
        BufferedReader fromServer = null;
        BufferedReader bufferedReader = null;
        StringBuilder queryStringParams = new StringBuilder();
            try {
                queryStringParams.append("&_mid=" + _mid).append("&_date=" + _date).append("&_time=" + _time);

                Log.i("결과 - 3 ", "" + queryStringParams);

                urlCon = UFitHttpURLConnectionManager.getHttpURLConnection(UFitNetworkConstantDefinition.URL_Ufit_Trainer_Add_Schedule + "_tid=" + _tid, "POST");


                toServer = urlCon.getOutputStream();


                toServer.write(queryStringParams.toString().getBytes("UTF-8"));

                toServer.flush();
                toServer.close();

                int responseCode = urlCon.getResponseCode();
                Log.i("통신결과  ", "" + responseCode);
                if(responseCode >= 200 && responseCode < 300){
                    fromServer = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
                    Log.i("통신결과 프롬서버", "" + fromServer);
                    StringBuilder jsonBuf = new StringBuilder();
                    String line = "";
                    while((line = fromServer.readLine()) != null){
                        jsonBuf.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(jsonBuf.toString());
                    Log.i("통신결과  오브젝트", "" + jsonObject);

                    result = jsonObject.getString("total");
                    Log.i("결과 - 1 ", "성공" + result);
                    return result;
                }
            } catch(Exception e){

            } finally{
                if(toServer != null){
                    try{
                        toServer.close();
                    }catch (IOException ioe){

                    }
                }
            }
            return null;

    }

}