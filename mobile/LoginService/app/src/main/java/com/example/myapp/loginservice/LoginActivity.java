package com.example.myapp.loginservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONObject;

import java.util.Arrays;

import static com.google.android.gms.auth.api.credentials.CredentialPickerConfig.Prompt.SIGN_IN;

/**
 * Created by Administrator on 2018-06-05.
 */

public class LoginActivity extends AppCompatActivity {
    private final String GOOGLE_TAG = "LoginActivity(Google)";
    private final String FACEBOOK_TAG = "LoginActivity(Facebook)";

    private LoginButton fb_login_btn;
    private SignInButton google_login_btn;

    private CallbackManager mCallbackManager;
    //구글

    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    private String platform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_login);

        init();


        fb_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

        google_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mGoogleApiClient != null){
                    googleLogout();
                }
                googleLogin();
            }
        });
    }

    private void init(){
        platform = getIntent().getStringExtra("PLATFORM");

        if(platform != null){
            platform = getIntent().getStringExtra("PLATFORM");
            doLogout();
        }
        fb_login_btn = (LoginButton)findViewById(R.id.facebook_login_btn);
        google_login_btn = (SignInButton)findViewById(R.id.google_login_btn);

        setGoogleLogin();
    }


    //-----------------------------------------Facebook Login---------------------------------------
    private void facebookLogin(){
        mCallbackManager = CallbackManager.Factory.create();

        fb_login_btn.setReadPermissions(Arrays.asList("public_profile","email"));

        fb_login_btn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    Log.d(FACEBOOK_TAG, "User Profile : " + object.toString());
                                    goMainActivity("facebook", object.getString("email"), object.getString("name"));
                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "페이스북 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "네트워크 연결이 불안합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void facebookLogout(){
        LoginManager.getInstance().logOut();
    }
    //----------------------------------------------------------------------------------------------

    //------------------------------------------Google Login----------------------------------------
    //구글에 로그인 된 사용자을 불러온다.
    private void setGoogleLogin(){
        FirebaseAuth.getInstance().signOut();

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener(){
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult){
                        Log.d(GOOGLE_TAG, "Google Login Failed!!!");
                        Toast.makeText(LoginActivity.this, "회원 정보를 가져올 수 없습니다.\n" + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API, options).build();
    }

    //구글 사용자 목록을 출력하고 로그인한다.
    private void googleLogin(){
        mFirebaseAuth = FirebaseAuth.getInstance();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN);
    }

    //구글 로그아웃
    private void googleLogout(){
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {

            @Override
            public void onConnected(@Nullable Bundle bundle) {
                FirebaseAuth.getInstance().signOut();
                if(mGoogleApiClient.isConnected()){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if(status.isSuccess()){
                                Log.v(GOOGLE_TAG, "Google SignOut Success");
                                setResult(1);
                            }
                            else {
                                Log.v(GOOGLE_TAG, "Google SignOut Failed");
                                Toast.makeText(LoginActivity.this, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                setResult(0);
                            }

                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Log.v(GOOGLE_TAG, "Google API Client Connection Suspended!!!");
                Toast.makeText(LoginActivity.this, "로그아웃에 실패했습니다.", Toast.LENGTH_SHORT).show();
                setResult(-1);
            }
        });
    }

    //구글에 정상적으로 로그인 후 Firebase 사용자 인증 정보로 교환
    private void firebaseAuthWithGoogle(GoogleSignInAccount account){
        Log.d(GOOGLE_TAG, "FirebaseAuthWithGoogle : " + account.getId());
        final String email = account.getEmail();
        final String name = account.getDisplayName();
        Log.d(GOOGLE_TAG, "Logged In Account : " + email + " , " + name);
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(GOOGLE_TAG, "SignInWithCredential:onComplete : " + task.isSuccessful());

                if(!task.isSuccessful()){
                    Log.w(GOOGLE_TAG, "SignInWithCredential", task.getException());
                    Toast.makeText(LoginActivity.this, "인증에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    goMainActivity("google", email, name);
                }
            }
        });
    }
    //----------------------------------------------------------------------------------------------

    //메인화면으로 이동하면서 로그인을 시도한 서비스플랫폼과, 사용자 이메일, 사용자 이름을 메인화면으로 넘긴다.
    private void goMainActivity(String platform, String email, String name){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("PLATFORM", platform);
        intent.putExtra("LOGIN_EMAIL", email);
        intent.putExtra("LOGIN_NAME", name);
        startActivity(intent);
        finish();
    }

    private void doLogout(){
        AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this);
        logoutAlert.setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false);
        logoutAlert.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (platform.equals("facebook")) {
                    facebookLogout();
                } else {
                    googleLogout();
                }
            }
        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = logoutAlert.create();
        alert.setTitle("로그아웃");
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //페이스북 로그인
        if(mCallbackManager != null)
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        //구글 로그인
        if(requestCode == SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result != null) {
                Log.d(GOOGLE_TAG, "Result Success Status : " + result.isSuccess());
                //구글 로그인 성공
                if (result.isSuccess()) {
                    GoogleSignInAccount account = result.getSignInAccount();

                    String personName = account.getDisplayName();
                    String personEmail = account.getEmail();

                    mGoogleApiClient.disconnect();

                    Log.d(GOOGLE_TAG, "Person Name     : " + personName);
                    Log.d(GOOGLE_TAG, "Person Email    : " + personEmail);

                    firebaseAuthWithGoogle(account);
                }
                //구글 로그인 실패
                else {
                    Log.e(GOOGLE_TAG, "Login Fail Cause : " + result.getStatus().getStatusMessage());
                }
            } else {
                Log.d(GOOGLE_TAG, "Result is Null");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
