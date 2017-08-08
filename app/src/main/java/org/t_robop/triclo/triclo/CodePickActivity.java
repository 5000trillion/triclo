package org.t_robop.triclo.triclo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

/**
 * Created by arika on 2017/07/06.
 */

public class CodePickActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codepick);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


    }


    public void intentaccessory(View v){
        Intent intent = new Intent(getApplicationContext(), ImgCodeAccessoryctivity.class);
        startActivity(intent);
    }
    public void intenttops(View v){
        Intent intent = new Intent(getApplicationContext(), ImgCodeTopsActivity.class);
        startActivity(intent);
    }
    public void intentbottoms(View v){
        Intent intent = new Intent(getApplicationContext(), ImgCodeBottomsActivity.class);
        startActivity(intent);
    }



}