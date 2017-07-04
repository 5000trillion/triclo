package org.t_robop.triclo.triclo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * Created by tomi on 2017/06/20.
 */
public class ClRegistActivity extends AppCompatActivity {
    InputMethodManager inputMethodManager;
    private LinearLayout mainLayout;
    TextView dateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clregist);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        EditText cloName = (EditText) findViewById(R.id.editText1);
        dateText = (TextView) findViewById(R.id.dateText);

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
        final Calendar c = Calendar.getInstance();
        int nyear = c.get(Calendar.YEAR);
        int nmonth = c.get(Calendar.MONTH);
        int nday = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year+"/"+monthOfYear+"/"+dayOfMonth;
                Toast.makeText(ClRegistActivity.this, date, Toast.LENGTH_SHORT).show();
                dateText.setText(date);
            }
        },nyear, nmonth, nday);
        datePicker.show();
        
    }


}