package org.t_robop.triclo.triclo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import io.realm.Realm;


/**
 * Created by tomi on 2017/06/20.
 */
public class ClRegistActivity extends AppCompatActivity {

    Realm realm;
    TextView dateText;
    TextView colText;
    int nowyear;
    int nowmonth;
    int nowday;
    Spinner genreSpinner;
    Spinner seasonSpinner;
    Bitmap bmp;
    ImageView clImg;

    InputMethodManager inputMethodManager;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clregist);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        /* 画像表示
        byte[] bytes = hoge;
        bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        clImg = (ImageView) findViewById(R.id.imageView);
        clImg.setImageBitmap(bmp);
        */

        //キーボード閉じの初期化
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        //日付変数の初期化 DatePick用
        final Calendar c = Calendar.getInstance();
        nowyear = c.get(Calendar.YEAR);
        nowmonth = c.get(Calendar.MONTH);
        nowday = c.get(Calendar.DAY_OF_MONTH);

        //TextView
        EditText cloName = (EditText) findViewById(R.id.editText1);
        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(nowyear + "/" + nowmonth + "/" + nowday);
        colText = (TextView) findViewById(R.id.coltext);

        //Spinner
        genreSpinner = (Spinner) findViewById(R.id.genre_spi);
        seasonSpinner = (Spinner) findViewById(R.id.season_spi);


        //Spinnerの初期化
        String genreArray[] = {"トップス", "インナー", "シャツ", "ボトムス", "アクセ", "その他"};
        String seasonArray[] = {"春", "夏", "秋", "冬", "春秋"};
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreArray);
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seasonArray);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        seasonSpinner.setAdapter(seasonAdapter);


        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* 何もないところをタップでキーボード閉じます。
         */
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    mainLayout.requestFocus();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showDatePickerDialog(View v) {
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear = monthOfYear + 1;
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                nowyear = year;
                nowmonth = monthOfYear - 1;
                nowday = dayOfMonth;
                Toast.makeText(ClRegistActivity.this, date, Toast.LENGTH_SHORT).show();
                dateText.setText(date);
            }
        }, nowyear, nowmonth, nowday);
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
            builder.setMessage("色選択").setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //キャンセルボタン押したときの処理

                }
            });
            return builder.create();
        }
    }

    DialogFragment newFragment = new ColorSelectDialogFragment();

    //表示
    public void showColorSelectDialog(View v) {
        newFragment.show(getFragmentManager(), "color");
    }

    //選んだとき
    public void colorSelected(View v) {
        String tagstr = String.valueOf(v.getTag());
        colText.setText(tagstr);
        colText.setTextColor(Color.BLACK);
        newFragment.dismiss();
        Toast.makeText(this, tagstr + "が選択されました", Toast.LENGTH_SHORT).show();
    }

    //戻るボタン押すと確認ダイアログ
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                new AlertDialog.Builder(this)
                        .setTitle("登録を中断しますか？")
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

    public void registClData(View v) {
        /*realm.beginTransaction();
        ClothesDb model = realm.createObject(ClothesDb.class);
        model.setId(1);*/
        Intent intent = new Intent(this, ClRegistFinActivity.class);
        startActivity(intent);
    }
}