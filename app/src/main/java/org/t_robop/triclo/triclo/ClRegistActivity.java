package org.t_robop.triclo.triclo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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


        //動く気しない
        /*cloName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //受け取った時
                    inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                }else{
                    //離れた時
                    inputMethodManager.showSoftInput(v, InputMethodManager.HIDE_NOT_ALWAYS);
                    mainLayout.requestFocus();
                }
            }
        });*/
    }

    //scrollview外すと機能します
    //@Override
    public void onTouchEvent(View v) {
        // キーボードを隠す
        inputMethodManager.showSoftInput(v, InputMethodManager.HIDE_NOT_ALWAYS);
        //inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // 背景にフォーカスを移す
        mainLayout.requestFocus();
        //return true;
    }

    public void showDatePickerDialog(View v) {
        final Calendar c = Calendar.getInstance();
        int nyear = c.get(Calendar.YEAR);
        int nmonth = c.get(Calendar.MONTH);
        int nday = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear=monthOfYear+1;
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                Toast.makeText(ClRegistActivity.this, date, Toast.LENGTH_SHORT).show();
                dateText.setText(date);
            }
        }, nyear, nmonth, nday);
        datePicker.show();
    }

    //色選択のダイアログフラグメント
    public static class ColorSelectDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = inflater.inflate(R.layout.dialog_colorselect, null);

            builder.setView(content);
            builder.setMessage("色選択").setNegativeButton("決定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //決定ボタン押したときの処理

                }
            });
            return builder.create();
        }
    }

    //ダイアログ表示
    public void showColorSelectDialog(View v){
        DialogFragment newFragment = new ColorSelectDialogFragment();
        newFragment.show(getFragmentManager(), "color");
    }

}