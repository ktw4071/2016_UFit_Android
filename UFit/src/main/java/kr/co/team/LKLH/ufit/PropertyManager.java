package kr.co.team.LKLH.ufit;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Admin on 2016-08-18.
 */
public class PropertyManager {
    private static PropertyManager instance;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(UFitApplication.getUFitContext());
        mEditor = mPrefs.edit();
    }
    /*
     서버로 넘길 ID 또는 토큰 값
     */
    private static final String FIELD_FACEBOOK_ID = "facebookId";
    private static final String FIELD_FACEBOOK_TOKEN_KEY = "facebookToken";

    public void setFacebookId(String id) {
        mEditor.putString(FIELD_FACEBOOK_ID, id);
        mEditor.commit();
    }

    public String getFaceBookId() {
        return mPrefs.getString(FIELD_FACEBOOK_ID, "");
    }

    public void setFacebookToken(String token) {
        mEditor.putString(FIELD_FACEBOOK_TOKEN_KEY, token);
    }
    public String getFieldFacebookTokenKey() {
        return mPrefs.getString(FIELD_FACEBOOK_TOKEN_KEY, "");
    }
}
