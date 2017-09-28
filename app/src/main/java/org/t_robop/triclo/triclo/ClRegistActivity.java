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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * Created by tomi on 2017/06/20.
 */
public class ClRegistActivity extends AppCompatActivity {

    Realm realm;
    EditText clName;
    EditText clMemo;
    TextView dateText;
    TextView colText;
    String clColor;

    String genreArray[] = {"トップス", "インナー", "シャツ", "ボトムス", "アクセサリー", "その他"};
    String seasonArray[] = {"春", "夏", "秋", "冬", "春秋"};

    int boughtYear;
    int boughtMonth;
    int boughtDay;
    int nowYear;
    int nowMonth;
    int nowDay;
    int nowHour;
    int nowMinute;
    int nowSecond;

    Spinner genreSpinner;
    Spinner seasonSpinner;
    Bitmap bmp;
    ImageView clImg;

    private InputMethodManager inputMethodManager;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clregist);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        //Intent intent = getIntent();
        //byte[] bytes = intent.getByteArrayExtra("imege");


        //画像表示
        //byte[] bytes = hoge;
        //mp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        Intent intent = getIntent();
        bmp = (Bitmap) intent.getParcelableExtra("image");

        clImg = (ImageView) findViewById(R.id.imageView);
        clImg.setImageBitmap(bmp);


        //キーボード閉じの初期化
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mainLayout = (LinearLayout) findViewById(R.id.main_layout);

        //日付変数の初期化
        setNowDate();
        boughtYear = nowYear;
        boughtMonth = nowMonth - 1;
        boughtDay = nowDay;

        //TextView
        clName = (EditText) findViewById(R.id.nameText);
        clMemo = (EditText) findViewById(R.id.memoText);
        dateText = (TextView) findViewById(R.id.dateText);
        dateText.setText(nowYear + "/" + nowMonth + "/" + nowDay);
        colText = (TextView) findViewById(R.id.coltext);


        //Spinner
        genreSpinner = (Spinner) findViewById(R.id.genre_spi);
        seasonSpinner = (Spinner) findViewById(R.id.season_spi);

        initGenreSpinner();
        initSeasonSpinner();

        //Spinnerの初期化
        /*String genreArray[] = {"トップス", "インナー", "シャツ", "ボトムス", "アクセ", "その他"};
        String seasonArray[] = {"春", "夏", "秋", "冬", "春秋"};
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreArray);
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seasonArray);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
        seasonSpinner.setAdapter(seasonAdapter);*/


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

    //ツールバーの戻るボタン
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void setNowDate() {
        final Calendar c = Calendar.getInstance();
        nowYear = c.get(Calendar.YEAR);
        nowMonth = c.get(Calendar.MONTH) + 1;
        nowDay = c.get(Calendar.DAY_OF_MONTH);
        nowHour = c.get(Calendar.HOUR_OF_DAY);
        nowMinute = c.get(Calendar.MINUTE);
        nowSecond = c.get(Calendar.SECOND);
    }


    //大変だった
    //コードコピペした
    public void showDatePickerDialog(View v) {
        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                /*日付を選択した時の処理*/
                //選択した日付を表示
                String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                boughtYear = year;
                boughtMonth = monthOfYear;
                boughtDay = dayOfMonth;
                Toast.makeText(ClRegistActivity.this, date, Toast.LENGTH_SHORT).show();
                dateText.setText(date);
            }
        }, boughtYear, boughtMonth, boughtDay);//開いた時にフォーカスする年月日を指定
        datePicker.show();
    }


    //大変だった
    //コピペしてもよくわかんなかった
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
        clColor = String.valueOf(v.getTag());
        colText.setText(clColor);
        colText.setTextColor(Color.BLACK);
        newFragment.dismiss();
        Toast.makeText(this, clColor + "が選択されました", Toast.LENGTH_SHORT).show();
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

    public void initGenreSpinner() {
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genreArray);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
    }

    public void initSeasonSpinner() {
        ArrayAdapter<String> seasonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seasonArray);
        seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(seasonAdapter);
    }


    //大変だった
    //
    //登録ボタン
    public void registClData(View v) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        setNowDate();
        String id = String.valueOf(nowYear) + String.valueOf(nowMonth) + String.valueOf(nowDay)
                + String.valueOf(nowHour) + String.valueOf(nowMinute) + String.valueOf(nowSecond);
        long l = Long.parseLong(id);


        //トランザム
        realm.beginTransaction();
        ClothesDb clothes = realm.createObject(ClothesDb.class, l);
        clothes.setName(clName.getText().toString());
        clothes.setGenre(genreSpinner.getSelectedItem().toString());
        clothes.setSeason(seasonSpinner.getSelectedItem().toString());
        clothes.setColor(clColor);
        clothes.setYear(boughtYear);
        clothes.setMonth(boughtMonth);
        clothes.setDay(boughtDay);
        clothes.setMemo(clMemo.getText().toString());
        clothes.setImage(bytes);
        realm.commitTransaction();

        Intent intent = new Intent(this, ClRegistFinActivity.class);
        intent.putExtra("clId", l);
        startActivity(intent);
        finish();
    }

}