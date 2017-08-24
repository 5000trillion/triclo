package org.t_robop.triclo.triclo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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

    public void intentfin(View v){
        Intent intent = new Intent(getApplicationContext(),CodepickFinActivity.class);
        startActivity(intent);
    }

    //戻るボタン押すと確認ダイアログ
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("コーデを中断しますか？")
                        .setMessage("入力中の内容は保存されません。")
                        .setNegativeButton("いいえ", null)
                        .setPositiveButton("はい",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                }

                        )
                        .show();
        }
        return super.onKeyDown(keyCode, event);
    }
}
