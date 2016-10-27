package com.cube_me.cubeme;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cube_me.cubeme.Calendar.CalendarFragment;

public class MainActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarFragment calendarFragment = new CalendarFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.activity_main, calendarFragment,"CalendarFragment");
        fragmentTransaction.commit();



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                fullView.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//        homeIntent.addCategory( Intent.CATEGORY_HOME );
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(homeIntent);
        this.finishAffinity();
    }
}
