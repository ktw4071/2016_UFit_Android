package kr.co.team.LKLH.ufit;

import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class UFitLoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = "UFitLoginActivity";
    private static final int RC_SIGN_IN = 9001;
    CallbackManager callbackManager;
    ImageView facebookLogin, googleLogin;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(UFitApplication.getUFitContext());
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.ufit_login);

        // 페이스북 로그인 버튼
        facebookLogin = (ImageView) findViewById(R.id.facebook_login);
        facebookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(UFitLoginActivity.this,
                        Arrays.asList("public_profile", "email", "user_friends"));
            }
        });

        // 구글+ 로그인 버튼
        googleLogin = (ImageView) findViewById(R.id.google_login);
        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        try {
            PackageInfo info = getPackageManager().getPackageInfo("kr.co.team.LKLH.ufit",PackageManager.GET_SIGNATURES);
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

    private void signIn() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(intent, RC_SIGN_IN);
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.i("leelog trace", "4");
            String token = (String) msg.obj;
            Log.i("leelog trace", "5");
            Log.i("leelog token token", token);
            Log.i("leelog trace", "6");
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        new Thread() {
            @Override
            public void run() {
                String token = "";
                try {
                    token = GoogleAuthUtil.getToken(UFitApplication.getUFitContext(), "sdsd4564@gmail.com", "oauth2:https://www.googleapis.com/auth/userinfo.profile");
                } catch (Exception e) {
                    Log.e("leelog eeeeee", e.toString());
                }
                Message message = handler.obtainMessage();
                message.obj = token;
                handler.sendMessage(message);
            }
        }.start();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            // mStatusTextView.setText(acct.getDisplayName());
            // updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        // updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
