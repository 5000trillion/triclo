package org.t_robop.triclo.triclo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by arika on 2017/07/06.
 */

public class CodePickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codepick);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //NavigationView Listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item1:
                        Log.d("TAG", "Item 1");

                        Intent intent = new Intent(CodePickActivity.this, Tab1_Activity.class);


                        startActivity(intent);
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });
    }

    public void intentaccessory(View v) {
        Intent intent = new Intent(getApplicationContext(), ImgCodeAccessoryActivity.class);
        startActivity(intent);
    }

    public void intenttops(View v) {
        Intent intent = new Intent(getApplicationContext(), ImgCodeTopsActivity.class);
        startActivity(intent);
    }

    public void intentbottoms(View v) {
        Intent intent = new Intent(getApplicationContext(), ImgCodeBottomsActivity.class);
        startActivity(intent);
    }
}
