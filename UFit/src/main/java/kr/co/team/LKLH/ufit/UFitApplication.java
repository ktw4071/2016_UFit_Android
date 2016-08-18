package kr.co.team.LKLH.ufit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.tsengvn.typekit.Typekit;

/**
 * Created by Admin on 2016-07-26.
 */
public class UFitApplication extends Application {
    private static Context mContext;
    private static volatile Activity currentActivity = null;

    /*public static Activity getCurrentActivity() {
        return currentActivity;
    }
    public static void setCurrentActivity(Activity currentActivity) {
        UFitApplication.currentActivity = currentActivity;
    }*/
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NanumBarunGothic.ttf"))
                .addBold(Typekit.createFromAsset(this, "NanumBarunGothicBold.ttf"));
    }
    public static Context getUFitContext(){
        return mContext;
    }

}
