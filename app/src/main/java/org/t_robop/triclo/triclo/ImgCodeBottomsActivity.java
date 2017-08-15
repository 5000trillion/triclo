package org.t_robop.triclo.triclo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TabHost;




public class ImgCodeBottomsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgcodebottoms);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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