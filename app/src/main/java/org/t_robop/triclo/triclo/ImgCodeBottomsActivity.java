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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;




public class ImgCodeBottomsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgcodebottoms);

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

                        Intent intent = new Intent(ImgCodeBottomsActivity.this, Tab1_Activity.class);


                        startActivity(intent);
                }


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });
        //TabHostオブジェクト取得
        TabHost tabhost = (TabHost) findViewById(android.R.id.tabhost);
        tabhost.setup();

        TabHost.TabSpec tab1 = tabhost.newTabSpec("tab1");
        tab1.setIndicator("全て");
        tab1.setContent(R.id.tab1);
        tabhost.addTab(tab1);

        TabHost.TabSpec tab2 = tabhost.newTabSpec("tab2");
        tab2.setIndicator("ズボン");
        tab2.setContent(R.id.tab2);
        tabhost.addTab(tab2);

        TabHost.TabSpec tab3 = tabhost.newTabSpec("tab3");
        tab3.setIndicator("スカート");
        tab3.setContent(R.id.tab3);
        tabhost.addTab(tab3);

        tabhost.setCurrentTab(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
}