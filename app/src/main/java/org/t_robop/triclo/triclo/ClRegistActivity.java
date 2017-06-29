package org.t_robop.triclo.triclo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by tomi on 2017/06/20.
 */
public class ClRegistActivity extends AppCompatActivity {
    InputMethodManager inputMethodManager;
    private LinearLayout mainLayout;
    private Calendar dCal = Calendar.getInstance();
    TextView dateText;
    final Calendar c = Calendar.getInstance();
    int y = c.get(Calendar.YEAR);
    int m = c.get(Calendar.MONTH);
    int d = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clregist);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        EditText cloName = (EditText) findViewById(R.id.editText1);
        dateText = (TextView)findViewById(R.id.dateText);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //spinner
        Spinner jan_spinner = (Spinner) findViewById(R.id.jan_spi);

    }

    //scrollview外すと機能します
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // 背景にフォーカスを移す
        mainLayout.requestFocus();

        return true;

    }



    
    public void showDatePickerDialog(View v) {

        DialogFragment newFragment = new DatePickerFragment(y,m,d);
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }
}