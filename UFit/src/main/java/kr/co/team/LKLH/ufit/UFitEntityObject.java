package kr.co.team.LKLH.ufit;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-08-03.
 */
public class UFitEntityObject {

    public JSONArray _part_select;
    public int[] _part;
    public int _attendance;
    public int _zid, _chest, _thigh, _calf, _forearm, _waist;
    public String _date;
    public int _value;
    public int _wid;
    public int _mid;
    public int _sid;
    public int _height, _initial, _goal, _level, _achieve, _weight;
    public String _memo;
    public String _image;
    public String _name;
    public String _birth;
    public String _number;
    public String _dayOfTheWeek;
    public String _time;
    public String _thumbnail;
    public int _career, _memberTotal;
    public String _location1, _location2;
    public JSONArray _histoty, _album;
    public String _from, _to, _content;

    public UFitEntityObject() {

    }
    public UFitEntityObject(JSONObject jsonObject, int taehyung) {
        switch (taehyung) {

            case 0 : {
                try {
                    this._mid = jsonObject.getInt("_mid");
                    this._name= jsonObject.getString("_name");
                    this._thumbnail = jsonObject.getString("_thumbnail");

                } catch (Exception e) {
                    Log.e("UFitEntityObject_ERROR", e.toString());
                }
                break;
            }
            case 1 : {
                try {
                    this._mid = jsonObject.getInt("_mid");
                    this._name = jsonObject.getString("_name");
                    this._thumbnail = jsonObject.getString("_thumbnail");
                    this._image = jsonObject.getString("_image");
                    this._birth= jsonObject.getString("_birth");
                    this._number= jsonObject.getString("_number");
                    this._dayOfTheWeek = jsonObject.getString("_dayOfTheWeek");


                } catch (Exception e) {
                    Log.e("UFitEntityObject_ERROR", e.toString());
                }
                break;
            }
            case 2 : {
                try {
                    this._name        = jsonObject.getString("_name");
                    this._birth       = jsonObject.getString("_birth");
                    this._career      = jsonObject.getInt("_career");
                    this._memberTotal = jsonObject.getInt("_memberTotal");
                    this._location1   = jsonObject.getString("_location1");
                    this._location2   = jsonObject.getString("_location2");
                    this._image       = jsonObject.getString("_image");
                    this._thumbnail   = jsonObject.getString("_thumbnail");

                } catch (Exception e) {
                    Log.e("ENTITIERROR", e.toString());
                }
                break;
            }
            case 3 : {
                try {
                    this._from = jsonObject.getString("_from");
                    this._to = jsonObject.getString("_to");
                    this._content = jsonObject.getString("_content");
                } catch (Exception e) {

                }
                break;

            }
            case 4 : {
                try {
                    this._birth       = jsonObject.getString("_birth");
                    this._name        = jsonObject.getString("_name");
                    this._career      = jsonObject.getInt("_career");
                    this._location1   = jsonObject.getString("_location1");
                    this._location2   = jsonObject.getString("_location2");
                    this._histoty     = jsonObject.getJSONArray("_history");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 5 : {
                try {
                    this._height = jsonObject.getInt("_height");
                    this._initial = jsonObject.getInt("_initial");
                    this._goal = jsonObject.getInt("_goal");
                    this._memo = jsonObject.getString("_memo");
                    this._name = jsonObject.getString("_name");
                    this._birth= jsonObject.getString("_birth");
                    this._number= jsonObject.getString("_number");
                    this._dayOfTheWeek = jsonObject.getString("_dayOfTheWeek");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 6 : {
                try {
                    this._name = jsonObject.getString("_name");
                    this._thumbnail = jsonObject.getString("_thumbnail");
                    this._image = jsonObject.getString("_image");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 7 : {
                try {
                    this._mid = jsonObject.getInt("_mid");
                    this._date= jsonObject.getString("_date");
                    this._attendance = jsonObject.getInt("_attendance");
                    this._part_select = jsonObject.getJSONArray("_part");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}

