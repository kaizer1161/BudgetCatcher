package com.pushertest.www.budgetcatcher.View.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Fragment.Advice;
import com.pushertest.www.budgetcatcher.View.Fragment.Catcher;
import com.pushertest.www.budgetcatcher.View.Fragment.Home;
import com.pushertest.www.budgetcatcher.View.Fragment.Manage;

public class MainActivity2 extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new Home(), Config.TAG_HOME_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                    return true;

                case R.id.navigation_catcher:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new Catcher(), Config.TAG_CATCHER_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                    return true;

                case R.id.navigation_manage:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new Manage(), Config.TAG_MANAGE_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                    return true;

                case R.id.navigation_advice:

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new Advice(), Config.TAG_ADVICE_FRAGMENT)
                            .addToBackStack(null)
                            .commit();

                    return true;

                case R.id.navigation_reports:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new Home(), Config.TAG_HOME_FRAGMENT)
                .addToBackStack(null)
                .commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
