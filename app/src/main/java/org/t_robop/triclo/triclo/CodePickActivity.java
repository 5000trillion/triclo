package org.t_robop.triclo.triclo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.TextView;

import static android.R.attr.id;

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_codepick,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        TextView varTextView = (TextView) findViewById(R.id.textView);
        switch (item.getItemId()){
            case R.id.item1:
                varTextView.setText(R.string.menu_item1);
                return true;
            case R.id.item2:
                varTextView.setText(R.string.menu_item2);
                return true;
            case R.id.item3:
                varTextView.setText(R.string.menu_item3);
                return true;
            }
        return super.onOptionsItemSelected(item);
        }



    public void intentaccessory(View v){
        Intent intent = new Intent(getApplicationContext(), ImgCodeAccessoryActivity.class);
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