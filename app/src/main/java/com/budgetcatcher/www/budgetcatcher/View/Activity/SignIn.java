package com.budgetcatcher.www.budgetcatcher.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Model.SignUpBody;
import com.budgetcatcher.www.budgetcatcher.Network.NetworkChangeReceiver;
import com.budgetcatcher.www.budgetcatcher.Network.QueryCallback;
import com.budgetcatcher.www.budgetcatcher.Network.URL;
import com.budgetcatcher.www.budgetcatcher.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignIn extends AppCompatActivity {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.facebook)
    ImageView facebook;

    private static final String TAG = "Sign In";
    private static final int RC_SIGN_IN = 9001;

    private BroadcastReceiver mNetworkReceiver;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseUser user;
    private CallbackManager mCallbackManager;


    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        /*FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);*/

    }
    // [END on_start_check_user]

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]
    }

    // [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        } else {

            // Pass the activity result back to the Facebook SDK
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle: " + acct.getId());

        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Log.d(TAG, "signInWithCredential: Authentication Failed.", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END auth_with_google]

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {

            this.user = user;
            login(user.getEmail(), user.getUid(), false);

        } else {

            //Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();

        }
    }

    @OnClick({R.id.signIn, R.id.forgot_password, R.id.facebook, R.id.google})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.signIn: {

                boolean hasError = false;

                if (password.getText().toString().equals("")) {
                    password.setError("Empty");
                    hasError = true;
                }
                if (email.getText().toString().equals("")) {
                    email.setError("Empty");
                    hasError = true;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {

                    email.setError("Wrong email format");
                    hasError = true;

                }
                if (!BudgetCatcher.getConnectedToInternet()) {

                    hasError = true;
                    Toast.makeText(SignIn.this, "No internet", Toast.LENGTH_SHORT).show();

                }
                if (!hasError) {

                    login(email.getText().toString(), password.getText().toString(), true);

                }

                break;
            }

            case R.id.google: {
                signIn();
                break;
            }

            case R.id.facebook: {
                LoginManager.getInstance().logInWithReadPermissions(
                        this,
                        Arrays.asList("user_photos", "email", "user_birthday", "public_profile")
                );
                break;
            }
        }
    }

    private void login(String userEmail, String userPassword, final Boolean generalLogin) {

        BudgetCatcher.apiManager.userSignIn(userEmail, userPassword, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {
                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                    JSONObject userObject = data.getJSONObject(0);
                    String userID = userObject.getString(URL.API_KEY_USER_ID);

                    if (storeUserInformationInSharedPreference(userID)) {

                        Toast.makeText(SignIn.this, "Welcome to Budget Catcher", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }

                } catch (JSONException e) {
                    Log.e("JsonError", e.toString());
                }

            }

            @Override
            public void onFail() {

                if (generalLogin) {

                    email.setText("");
                    password.setText("");

                    email.setError("Wrong email");
                    password.setError("Wrong password");

                } else {
                    signUp();
                    Log.e(TAG, "Log in onFail: Failed");
                }
            }

            @Override
            public void onError(Throwable th) {
                Log.e("SerVerErr", th.toString());
                if (th instanceof SocketTimeoutException) {
                    Toast.makeText(SignIn.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignIn.this, th.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUp() {

        SignUpBody signUpBody = new SignUpBody(user.getDisplayName(), user.getEmail(), user.getUid(), user.getPhoneNumber(), "social", "null", "null");

        BudgetCatcher.apiManager.userSignUp(signUpBody, new QueryCallback<String>() {
            @Override
            public void onSuccess(String response) {

                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray(URL.API_KEY_DATA);
                    JSONObject userObject = data.getJSONObject(0);
                    String userID = userObject.getString(URL.API_KEY_USER_ID);

                    if (storeUserInformationInSharedPreference(userID)) {

                        Toast.makeText(SignIn.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignIn.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail() {

                Toast.makeText(SignIn.this, "Failed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable th) {

                if (th instanceof SocketTimeoutException) {

                    Toast.makeText(SignIn.this, getString(R.string.time_out_error), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private boolean storeUserInformationInSharedPreference(String userId) {

        SharedPreferences sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Config.SP_USER_ID, userId);
        editor.putBoolean(Config.SP_LOGGED_IN, true);
        return editor.commit();

    }

    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        /*showProgressDialog();*/
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        /*hideProgressDialog();*/
                        // [END_EXCLUDE]
                    }
                });
    }

    private void registerNetworkBroadcastForNougat() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            }
        } catch (Exception e) {
            Log.v("Internet Reg : ", " " + e);
        }

    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }

    @Override
    protected void onPause() {

        super.onPause();
        BudgetCatcher.activityPaused();// On Pause notify the Application
    }

    @Override
    protected void onResume() {

        super.onResume();
        BudgetCatcher.activityResumed();// On Resume notify the Application
    }

}
