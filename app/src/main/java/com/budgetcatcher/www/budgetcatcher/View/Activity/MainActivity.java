package com.budgetcatcher.www.budgetcatcher.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.budgetcatcher.www.budgetcatcher.BudgetCatcher;
import com.budgetcatcher.www.budgetcatcher.Config;
import com.budgetcatcher.www.budgetcatcher.Network.NetworkChangeReceiver;
import com.budgetcatcher.www.budgetcatcher.R;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Advice;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Catcher;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Home;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Manage;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Report;
import com.budgetcatcher.www.budgetcatcher.View.Fragment.Settings;
import com.budgetcatcher.www.budgetcatcher.Yodlee.YodleeActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private BroadcastReceiver mNetworkReceiver;
    public BottomSheetBehavior projectedBalanceBottomSheetBehavior;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mNetworkReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastForNougat();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new Home(), Config.TAG_HOME_FRAGMENT)
                .addToBackStack(Config.TAG_HOME_FRAGMENT)
                .commit();

        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if (projectedBalanceBottomSheetBehavior != null && projectedBalanceBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                projectedBalanceBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            } else {

                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {

                    getSupportFragmentManager().popBackStack(Config.TAG_HOME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, new Home(), Config.TAG_HOME_FRAGMENT)
                            .commit();

                } else {

                    /*super.onBackPressed();*/
                    finish();
                }

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            navigationView.setCheckedItem(R.id.nav_home);

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                getSupportFragmentManager().popBackStack(Config.TAG_HOME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Home(), Config.TAG_HOME_FRAGMENT)
                    .commit();

        } else if (id == R.id.nav_catcher) {


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Catcher(), Config.TAG_CATCHER_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_catcher);


        } else if (id == R.id.nav_manage) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Manage(), Config.TAG_MANAGE_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_manage);

        } else if (id == R.id.nav_advice) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Advice(), Config.TAG_ADVICE_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_advice);

        } else if (id == R.id.nav_report) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Report(), Config.TAG_REPORT_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_settings) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Settings(), Config.TAG_SETTINGS_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_settings);

        } else if (id == R.id.nav_yodlee) {

            startActivity(new Intent(MainActivity.this, YodleeActivity.class));
            finish();

        } else if (id == R.id.nav_logout) {

            // [START declare_auth]
            FirebaseAuth mAuth;
            // [END declare_auth]

            GoogleSignInClient mGoogleSignInClient;

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

            // Firebase sign out
            mAuth.signOut();

            SharedPreferences sharedPreferences = getSharedPreferences(Config.SP_APP_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();

            if (editor.commit()) {

                startActivity(new Intent(MainActivity.this, SignInSignUp.class));
                finish();

            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
