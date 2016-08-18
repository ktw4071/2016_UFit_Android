package kr.co.team.LKLH.ufit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
/*import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UFitLoginActivity extends AppCompatActivity {
//implements GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "UFitLoginActivity";
    private static final int RC_SIGN_IN = 9001;
//    private GoogleApiClient mGoogleApiClient;

    CallbackManager callbackManager;

    ImageView facebookLogin, googleLogin;

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
        /*googleLogin = (ImageView)findViewById(R.id.google_login);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });*/


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

                PropertyManager.getInstance().setFacebookId(userId);
                PropertyManager.getInstance().setFacebookToken(accessToken);

                Intent intent = new Intent(UFitLoginActivity.this, UFitMainActivity.class);
                intent.putExtra("token", accessToken);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

   /* @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }*/

    /*private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
