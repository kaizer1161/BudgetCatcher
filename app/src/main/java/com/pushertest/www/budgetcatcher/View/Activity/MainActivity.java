package com.pushertest.www.budgetcatcher.View.Activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pushertest.www.budgetcatcher.Config;
import com.pushertest.www.budgetcatcher.R;
import com.pushertest.www.budgetcatcher.View.Fragment.Advice;
import com.pushertest.www.budgetcatcher.View.Fragment.Catcher;
import com.pushertest.www.budgetcatcher.View.Fragment.Home;
import com.pushertest.www.budgetcatcher.View.Fragment.Manage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, new Home(), Config.TAG_HOME_FRAGMENT)
                .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
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
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Home(), Config.TAG_HOME_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_catcher) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Catcher(), Config.TAG_CATCHER_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_manage) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Manage(), Config.TAG_MANAGE_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_advice) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, new Advice(), Config.TAG_ADVICE_FRAGMENT)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_report) {


        } else if (id == R.id.nav_balance_projection) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
