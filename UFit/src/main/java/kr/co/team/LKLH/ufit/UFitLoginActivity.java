package kr.co.team.LKLH.ufit;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UFitLoginActivity extends AppCompatActivity {

    CallbackManager callbackManager;

    ImageView facebookLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(UFitApplication.getUFitContext());
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.ufit_login);

        facebookLogin = (ImageView)findViewById(R.id.facebook_login);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(UFitLoginActivity.this,
                        Arrays.asList("public_profile","email","user_friends"));
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "kr.co.team.LKLH.ufit",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(UFitLoginActivity.this.toString(), e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("노서치알고리즘", "NO_SUCH_ALGORITM");
        }

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String userId = loginResult.getAccessToken().getUserId();
                Log.e("fbUserId", String.valueOf(userId));

                String accessToken = loginResult.getAccessToken().getToken();
                Log.e("accessToken", String.valueOf(accessToken));

                PropertyManager.
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}
